import java.net.Socket;
/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
*/
public class Main /*extends Application*/{
    public static boolean authenticated = false;

    public static void main(String[] args) {
        //launch(args);

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
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./registration_form.fxml"));
        primaryStage.setTitle("Registration Form FXML Application");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }
*/
}