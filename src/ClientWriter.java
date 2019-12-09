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
        Interpreter interpreter = new Interpreter();
        // Check if authenticated
        while (!Main.authenticated) {
            System.out.print("Insert username: ");
            String tempUsername = stdIn.nextLine();
            // Send auth message
            out.println("Head: " + tempUsername.trim());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("\n!!!Authenticated!!!\n");
        while (true){
            String myMessage = stdIn.nextLine();
            interpreter.setString(myMessage);
            out.println(interpreter.interpret());
            if (myMessage.equals("/quit")){
                System.exit(0);
            }

        }
    }
}
