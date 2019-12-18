import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ChatController {


    @FXML
    private VBox usersList;

    @FXML
    void initialize() {
        assert usersList != null : "fx:id=\"usersList\" was not injected: check your FXML file 'Chat.fxml'.";
        loadUsersButtons(new ArrayList<String>());
        Main.chatController = this;
    }

    public void loadUsersButtons(ArrayList<String> users) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            for (String s : users) {
                Button b = new Button(s);
                b.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String name = s;
                        System.out.println(name);

                    }
                });
                b.setPrefSize(200, 30);
                usersList.getChildren().add(b);
            }

        });

    }
}
