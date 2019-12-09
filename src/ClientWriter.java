/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raul Farkas
 */
public class ClientWriter implements Runnable {

    private Scanner stdIn;
    String userInput;
    PrintWriter out;
    public ClientWriter(PrintWriter out) {
        this.out = out;
        stdIn = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true){
            String myMessage = stdIn.nextLine();
            out.println(myMessage);
            if (myMessage.equals("Command: /quit")){
                System.exit(0);
            }

        }
    }
}
