import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
    public static boolean authenticated = false;
    public static RegistrationFormController registrationFormController;
    public static ChatController chatController;
    public static ScreenController screenController;
    public static ArrayList<String> onlineUsers = new ArrayList<String>();

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
        Scene scene = new Scene(root, 700, 500);
        screenController = new ScreenController(scene);
        screenController.addScreen("RegistrationForm", FXMLLoader.load(getClass().getResource("RegistrationForm.fxml")));

        // Add screens
        screenController.addScreen("Chat", FXMLLoader.load(getClass().getResource("Chat.fxml")));
        Main.registrationFormController = (RegistrationFormController) fxmlLoader.getController();

        primaryStage.setTitle("Registration Form FXML Application");

        // What happens when windows closes
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

}