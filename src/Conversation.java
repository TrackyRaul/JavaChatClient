import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Conversation {
    private String user;
    public ArrayList<TextArea> messages = new ArrayList<>();

    public Conversation(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<TextArea> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<TextArea> messages) {
        this.messages = messages;
    }
}
