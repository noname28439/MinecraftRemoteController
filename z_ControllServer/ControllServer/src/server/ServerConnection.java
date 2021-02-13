package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerConnection implements Runnable {

    Socket connection;
    PrintWriter out;
    Scanner in;
    Thread listener;

    public static String commandSeperator = ":";


    String name = "Not Named";


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
