package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().begin(4444);
    }

    
    ServerSocket serverSocket;

    public void begin(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for clients to connect on port " + port + "...");
            new Server.ProtocolThread(serverSocket.accept()).start();
            //Thread.start() calls Thread.run()
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;

        public ProtocolThread(Socket socket) {
            System.out.println("Accepting connection from " + socket.getInetAddress() + "...");
            this.socket = socket;
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            try
            {
                //System.out.println("Expecting answer from client...");
                //sleep(5000);
                
                // RANDOM BETWEEN 0 AND 5
                Random rd = new Random();
                randNumber = rd.nextInt(6);
                boolean match = false;
                
                do
                {
                    int chosenNumber = Integer.parseInt(in_socket.readLine());
                    if(chosenNumber == randNumber)
                    {
                        match = true;
                        System.out.println("=");
                        out_socket.println("=");
                    }
                    else if(randNumber > chosenNumber)
                    {
                        System.out.println("+");
                        out_socket.println("+");
                    }
                    else
                    {
                        System.out.println("-");
                        out_socket.println("-");
                    }
                } while(!match);
                
                System.out.println("End of this stupid server");
                
                
                
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            finally
            {
                try
                {
                    System.out.println("Closing connection.");
                    socket.close();
                }
                
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        private boolean first = true;
        private int randNumber;
    }
}
