import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raul Farkas
 */
public class Session implements Runnable{
    private Socket socket;
    public Session(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(this.socket.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            // Socket output stream
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);
            
            Thread t1 = new Thread(new ClientWriter(out));
            t1.start();
            while (true) {
                String message = in.readLine();
                // Debug System.out.println(message);
                /* Available structures
                    1. 3 elements separated by ":"
                */

                // Structure 1
                if (message.split(":").length >= 3){
                    /* Available substructures
                        1. User:{username}:{message} -> message from users
                        2. Server:error:{error message} -> error message
                    */

                    String[] messageTokens = message.split(":");
                    if (messageTokens[0].equals("User")) {
                        // Substructure 1
                        // Print message
                        System.out.println(messageTokens[1].trim() + ": " + messageTokens[2].trim());
                    } else if (messageTokens[0].equals("Server")) {
                        // Substructure 2
                        if (messageTokens[1].equals("error")){
                            System.out.println("Error: " + messageTokens[2].trim());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error encountered while creating the io streams.");
        }
        
        
    }
}
