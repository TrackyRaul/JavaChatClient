
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raul Farkas
 */
public class ClientWriter implements Runnable {

    private Scanner stdIn;
    String userInput;
    PrintWriter out;
    public static String username = new String();

    public ClientWriter(PrintWriter out) {
        this.out = out;
        stdIn = new Scanner(System.in);
    }
    public static void usernameUpdated() {
        // Notify that object changed
        synchronized (ClientWriter.username.getClass()) {
            ClientWriter.username.getClass().notifyAll();
        }

    }
    @Override
    public void run() {
        Interpreter interpreter = new Interpreter();


        synchronized (ClientWriter.username.getClass()) {
            // Check if authenticated
            while (Main.authenticated == false) {
                try {
                    // Wait for username to be updated
                    ClientWriter.username.getClass().wait();
                    System.out.println(ClientWriter.username);
                    // Send auth message and try to log-in
                    out.println("Head: " + ClientWriter.username.trim());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Update interface message to authenticated!
        Main.registrationFormController.updateLoginStatus("!!!Authenticated!!!");
        while (true){
            String myMessage = stdIn.nextLine();
            interpreter.setString(myMessage);
            out.println(interpreter.interpret());
            if (myMessage.equals("/quit")){
                System.exit(0);
            }

        }
    }
}
