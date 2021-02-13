package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ControllServer {

    static ServerSocket server;

    public static ArrayList<ServerConnection> connections = new ArrayList<>();

    static JFrame senderFrame;
    static JPanel senderFramePanel;
    static JTextField senderFramePanelCommandField;

    public static void load()  {

    }

    public static void openServerSenderPanel(){
        senderFrame = new JFrame("MessageSender");
        senderFramePanel = new JPanel();
        senderFramePanelCommandField = new JTextField();
        senderFramePanelCommandField.addActionListener(new SendListener());
        senderFramePanel.add(senderFramePanelCommandField);
        senderFrame.add(senderFramePanel);
        senderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        senderFrame.setVisible(true);

    }

    public static void startServer() {
        System.out.println("Starting Server...");
        try {
            server = new ServerSocket(44335);
        }catch(java.io.IOException e){e.printStackTrace();}
        openServerSenderPanel();

        while(true){
            try {
                Socket currentConnection = server.accept();
                System.out.println("Connected by "+currentConnection.getInetAddress().toString());
                connections.add(new ServerConnection(currentConnection));
            } catch (IOException e) {e.printStackTrace();}
        }

    }

    public static void sendMessageToAll(String text){
        for(ServerConnection sc : connections)
            sc.sendMessage(text);
    }


    static class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessageToAll(senderFramePanelCommandField.getText());
        }
    }

}


