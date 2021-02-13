package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection implements Runnable {

    Socket connection;
    PrintWriter out;
    Scanner in;
    Thread listener;

    public static String commandSeperator = ":";


    String name = "Not Named";

    static int[] location = new int[3];

    public ServerConnection(Socket connection) {
        this.connection = connection;
        try {
            out = new PrintWriter(connection.getOutputStream());
            in = new Scanner(connection.getInputStream());
        } catch (IOException e) {e.printStackTrace();}

        listener = new Thread(this);
        listener.start();



    }

    @Override
    public void run() {

        while(true) {
            try {
                String rcv = in.nextLine();
                System.out.println("--> "+rcv);
                String[] args = rcv.split(commandSeperator);

                if(args[0].equalsIgnoreCase("echo"))
                    ControllServer.sendMessageToAll(args[1]);
                
                
                if(args[0].equalsIgnoreCase("Name"))
                	name = args[1];
                
                
                if(args[0].equalsIgnoreCase("CoordX"))
                	location[0] = Integer.valueOf(args[1].split("\\.")[0]);
                
                if(args[0].equalsIgnoreCase("CoordY"))
                	location[1] = Integer.valueOf(args[1].split("\\.")[0]);
                
                if(args[0].equalsIgnoreCase("CoordZ"))
                	location[2] = Integer.valueOf(args[1].split("\\.")[0]);
                

            }catch (java.util.NoSuchElementException e) {
                System.err.println(name+" lost connection...");
                listener.stop();
            }

        }

    }

    public void kill() {
        System.out.println("Killing "+name+"...");
        ControllServer.connections.remove(this);
        listener.stop();
    }

    public void sendMessage(String text) {
        out.println(text);
        out.flush();
    }

}
