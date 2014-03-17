package tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 4444);
        //Socket socket = new Socket("127.0.0.1", 4444);
        //to get the ip address
        System.out.println((java.net.InetAddress.getLocalHost()).toString());

        //true: it will flush the output buffer
        PrintWriter outSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       // Thread.sleep(1000);

        //System.out.println("Sending question to server");
        
        boolean match = false;
        
        do
        {
            System.out.println("Try to find THE number.");
            Scanner sc = new Scanner(System.in);
            int chosenNumber = sc.nextInt();

            outSocket.println(chosenNumber);
            String serverReturn = inSocket.readLine();
            //System.out.println("Waiting answer from server");

            if("=".equals(serverReturn))
            {
                match = true;
                System.out.println("You are so IN " + serverReturn);
            }
            else if("+".equals(serverReturn))
            {
                System.out.println("THE number is superior " + serverReturn);
            }
            else
            {
                System.out.println("THE number is inferior " + serverReturn);
            }
        } while(!match);
        
        System.out.println("End.");
    }
}

