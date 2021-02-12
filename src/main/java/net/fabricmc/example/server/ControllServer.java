package net.fabricmc.example.server;

import java.net.ServerSocket;

public class ControllServer {

    static ServerSocket server;

    public static void load()  {

    }

    public static void startServer() {
        try {
            server = new ServerSocket(44335);
        }catch(java.io.IOException e){e.printStackTrace();}





    }

}
