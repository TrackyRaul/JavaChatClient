import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static boolean authenticated = false;
    public static RegistrationFormController registrationFormController;
    public static void main(String[] args) {


        try {
            // Socket creation
            Socket socket = new Socket("localhost", 3333);
            System.out.println("Client started.");

            Thread t1 = new Thread(new Session(socket));
            t1.start();
            launch(args);

        } catch (Exception e) {
            System.err.println("Error" + e);
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrationForm.fxml"));
        Parent root = fxmlLoader.load();
        Main.registrationFormController = (RegistrationFormController) fxmlLoader.getController();
        primaryStage.setTitle("Registration Form FXML Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();


    }

}