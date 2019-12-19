import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private Text chattingWith;

    private ArrayList<TextArea> messages = new ArrayList<>();

    private ArrayList<Conversation> conversations = new ArrayList<>();

    @FXML
    void initialize() throws FileNotFoundException {
        assert usersList != null : "fx:id=\"usersList\" was not injected: check your FXML file 'Chat.fxml'.";
        loadUsersButtons(new ArrayList<String>());
        Main.chatController = this;
        sendButton.requestFocus();
        scrollPane.vvalueProperty().bind(messagesView.heightProperty());
        FileInputStream input = new FileInputStream("src\\send.png");
        Image img = new Image(input);
        sendButton.setGraphic(new ImageView(img));

    }

    public void sendMessage() {
        if (!messageInput.getText().equals("")) {
            ClientWriter.message = messageInput.getText();
            ClientWriter.sendMessage();
            // Update messages in conversation, by adding sent message
            for (Conversation conversation : conversations) {
                // If conversation is equal to current dest add to messages
                if (conversation.getUser().equals(ClientWriter.dest)) {
                    TextArea ta = new TextArea(ClientWriter.username + ": " + ClientWriter.message);
                    // Set sent text with right to left orientation
                    ta.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    conversation.getMessages().add(ta);
                    break;
                }
            }
            // Update messages shown on screen
            updateMessages();
            messageInput.clear();
        }
    }

    public void addMessage(String message, String username) {
        Platform.runLater(() -> {
            // Add received message to specific conversation
            for (Conversation conversation : conversations) {
                if (conversation.getUser().equals(username)) {
                    conversation.getMessages().add(new TextArea(message));
                    if (ClientWriter.dest.equals(conversation.getUser())) {
                        /*  If you are currently talking to the person
                            you are receiving the message from update
                            shown messages on screen

                        */
                        updateMessages();

                    }
                    break;
                }
            }
        });
    }

    public void updateMessages() {
        messagesView.getChildren().clear();
        // Consider each conversation
        for (Conversation conversation : this.conversations) {
            // If the conversation equals to the one you are in
            if (conversation.getUser().equals(ClientWriter.dest)) {
                // Display conversation messages
                for (TextArea a : conversation.getMessages()) {
                    a.setPrefSize(500,18);
                    a.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    a.getStyleClass().add("message-text");
                    a.editableProperty().setValue(false);
                    messagesView.getChildren().add(a);

                }
                break;
            }
        }

    }

    public void loadUsersButtons(ArrayList<String> users) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            // All button
            Button allButton = new Button("All");
            conversations.add(new Conversation("All"));
            allButton.setPrefSize(200, 30);
            allButton.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            allButton.getStyleClass().add("user-button");
            allButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent) {
                    ClientWriter.message = "/dest broadcast";
                    ClientWriter.sendMessage();
                    ClientWriter.dest = "All";
                    // Switch displayed conversations
                    updateMessages();
                    chattingWith.setText("Users: All");

                }
            });
            usersList.getChildren().add(allButton);

            for (String s : users) {
                if (!s.equals(ClientWriter.username)) {
                    Button b = new Button(s);
                    conversations.add(new Conversation(s));
                    b.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    b.getStyleClass().add("user-button");
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            chattingWith.setText("User: " + s);
                            String name = s;
                            ClientWriter.dest = s;
                            // Switch displayed conversation
                            updateMessages();
                            ClientWriter.message = "/dest " + name;
                            ClientWriter.sendMessage();
                        }
                    });

                    b.setPrefSize(200, 30);
                    usersList.getChildren().add(b);
                }
            }
        });

    }
}
