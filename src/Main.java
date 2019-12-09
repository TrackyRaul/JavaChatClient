import java.net.Socket;

public class Main {
    public static boolean authenticated = false;
    public static void main(String[] args) {
        try {
            // Socket creation
            Socket socket = new Socket("localhost", 3333);
            System.out.println("Client started.");

            Thread t1 = new Thread(new Session(socket));
            t1.start();

        } catch (Exception e) {
            System.err.println("Error" + e);
            System.exit(1);
        }
    }
}
