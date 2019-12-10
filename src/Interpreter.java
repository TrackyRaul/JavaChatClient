public class Interpreter {
    private String string;
    public Interpreter(String string) {
        this.string = string.trim();
    }

    public Interpreter() {
        this.string = "";
    }

    public String interpret () {
        String ret = "";
        /*
        Commands:
        1. /dest {user}
        2. /dest broadcast
        3. /quit
         */

        if (isCommand()) {
            boolean useDefault = true;
            String[] commandTokens = this.string.split(" ");
            if (commandTokens.length == 2) {
                if (commandTokens[0].trim().equals("/dest") && !commandTokens[1].trim().equals("broadcast")) {
                    ret = "Command: /dest user," + commandTokens[1].trim();
                    useDefault = false;
                }
            }

            if (useDefault) {
                ret = "Command: " + this.string;
            }

        } else {
            ret = "Message: " + this.string;
        }
        return ret;
    }

    private boolean isCommand() {
        if (this.isStringValid()) {
            if (this.string.charAt(0) == '/'){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isStringValid() {
        boolean valid = true;
        if (this.string.length() < 1) {
            valid = false;
        }

        return valid;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
