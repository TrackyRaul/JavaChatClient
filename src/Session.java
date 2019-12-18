import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Raul Farkas
 */
public class Session implements Runnable{
    private Socket socket;
    public static String loginStatus = "";

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
                    2. 2 elements separated by ":"
                */

                // Structure 1
                if (message.split(":").length >= 3){
                    /* Available substructures
                        1. User:{username}:{message} -> message from users
                        2. Server:error:{error message} -> error message
                        3. Server:updateUser:{users} -> connected users list
                    */

                    String[] messageTokens = message.split(":");
                    if (messageTokens[0].equals("User")) {
                        // Substructure 1
                        // Print message
                        String printableMessage = message.substring(5 + messageTokens[1].length() + 1);

                        System.out.println(messageTokens[1].trim() + ": " + printableMessage);


                    } else if (messageTokens[0].equals("Server")) {
                        // Substructure 2
                        if (messageTokens[1].equals("error")){
                            // Update login status on interface
                            Main.registrationFormController.updateLoginStatus("Error: " + messageTokens[2].trim());
                        } else if (messageTokens[1].equals("updateUsers")) {
                            ArrayList<String> users = new ArrayList<>(Arrays.asList(messageTokens[2].strip().split(",")));
                            Main.chatController.loadUsersButtons(users);
                            System.out.println("Users: " + messageTokens[2].trim());
                        }
                    }
                } else if (message.split(":").length == 2) {
                    String[] messageTokens = message.split(":");
                    if (messageTokens[0].equals("Head") && messageTokens[1].trim().equals("OK")) {
                        Main.authenticated = true;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error encountered while creating the io streams.");
        }
        
        
    }
}
