import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatController {


    @FXML
    private VBox usersList;

    @FXML
    private TextField messageInput;

    @FXML
    private Button sendButton;

    @FXML
    private VBox messagesView;

    @FXML
    private ScrollPane scrollPane;

    private ArrayList<TextArea> messages = new ArrayList<>();

    @FXML
    void initialize() {
        assert usersList != null : "fx:id=\"usersList\" was not injected: check your FXML file 'Chat.fxml'.";
        loadUsersButtons(new ArrayList<String>());
        Main.chatController = this;
        sendButton.requestFocus();
        scrollPane.vvalueProperty().bind(messagesView.heightProperty());

    }

    public void sendMessage() {
        if (!messageInput.getText().equals("")) {
            ClientWriter.message = messageInput.getText();
            ClientWriter.sendMessage();
            messages.add(new TextArea(ClientWriter.username + ": " + ClientWriter.message));
            updateMessages();
            messageInput.clear();
        }
    }

    public void addMessage(String message) {
        Platform.runLater(() -> {
            messages.add(new TextArea(message));
            updateMessages();
        });
    }

    public void updateMessages() {
        messagesView.getChildren().clear();
        for (TextArea a : this.messages) {
            a.setPrefSize(500,18);
            a.editableProperty().setValue(false);
            messagesView.getChildren().add(a);

        }
    }

    public void loadUsersButtons(ArrayList<String> users) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            // All button
            Button allButton = new Button("All");
            allButton.setPrefSize(200, 30);
            allButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent) {
                    ClientWriter.message = "/dest broadcast";
                    ClientWriter.sendMessage();

                }
            });
            usersList.getChildren().add(allButton);

            for (String s : users) {
                Button b = new Button(s);
                b.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String name = s;
                        ClientWriter.message = "/dest " + name;
                        ClientWriter.sendMessage();

                    }
                });
                b.setPrefSize(200, 30);
                usersList.getChildren().add(b);
            }



        });

    }
}
