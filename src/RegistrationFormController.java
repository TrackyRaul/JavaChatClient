import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationFormController {

    public RegistrationFormController() {
    }

    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    @FXML
    private void initialize()
    {

    }

    @FXML
    private void login()
    {
        if (!usernameField.getText().equals("")) {
            ClientWriter.username = usernameField.getText();
            ClientWriter.usernameUpdated();
            //System.out.println(ClientWriter.username);
        }
    }

    public void updateLoginStatus(String text) {
        // Method to be called externally, it updates label text
        Platform.runLater(() -> {
            this.loginStatus.setText(text);
        });
    }
}
