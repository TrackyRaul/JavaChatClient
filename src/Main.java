import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            // Socket creation
            Socket socket = new Socket("localhost", 3333);
            System.out.println("Client started.");

            Thread t1 = new Thread(new Session(socket));
            t1.start();
            // ciclo di lettura da tastiera, invio al server e stampa risposta
        } catch (Exception e) {
            System.err.println("Errore client" + e);
            System.exit(1);
        }
    }
}
