package net.fabricmc.example.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {

    static Thread thread;
    static Socket s;
    static PrintWriter out;
    static Scanner in;

    static boolean connected = false;

    public static void connectClient(String ip, int port){

        MinecraftClient mc = MinecraftClient.getInstance();


         thread = new Thread() {
            @Environment(EnvType.CLIENT)
            public void run(){



                    while(true) {

                        if(!connected){
                            try {Thread.currentThread().sleep(5*1000);} catch (InterruptedException ex) {ex.printStackTrace();}
                            System.out.println("Not Connected... ==> Trying to Connect...");

                            try{
                                System.out.println("Connecting to "+ip+":"+port);
                                s = new Socket(ip, port);
                                out = new PrintWriter(s.getOutputStream());
                                in = new Scanner(s.getInputStream());
                                connected = true;
                            } catch (Exception e) {
                                if(e instanceof java.net.ConnectException) {
                                    System.out.println("Server didn't respond...");
                                }else
                                    e.printStackTrace();
                            }
                        }else {
                            try{
                                String rcv = in.nextLine();
                                String[] args = rcv.split(":");
                                try{
                                    mc.player.sendMessage(new LiteralText("[RemoteController] --> " + rcv), false);
                                }catch(java.lang.NullPointerException e){
                                    System.out.println("[RemoteController]--> " + rcv);
                                }

                                if(args.length==4)
                                    if (args[0].equalsIgnoreCase("b_goal")) {
                                        mc.player.sendChatMessage("#goal " + args[1] + " " + args[2] + " " + args[3]);
                                    }
                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("b_path")) {
                                        mc.player.sendChatMessage("#path");
                                    }
                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("b_stop")) {
                                        mc.player.sendChatMessage("#stop");
                                    }

                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("kill")) {
                                        mc.stop();
                                    }

                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("ping")) {
                                        out.println("Pong!");
                                        out.flush();
                                        mc.player.sendMessage(new LiteralText("_Pong!"), false);
                                    }

                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("disconnect")) {
                                        mc.getNetworkHandler().getConnection().disconnect(Text.of("Kein Bock!"));
                                    }


                                if(args.length==3)
                                    if (args[0].equalsIgnoreCase("connect")) {
                                        String address = args[1];
                                        int port = Integer.valueOf(args[2]);
                                        ExampleMod.port = port;
                                        ExampleMod.ip = address;
                                        //mc.openScreen(new ConnectScreen(new TitleScreen(), mc, new ServerInfo(name, address, local)));

                                        //mc.openScreen(new ConnectScreen(new TitleScreen(), mc, address, port));
                                        //mc.setCurrentServerEntry(new ServerInfo("", "localhost:25565", true));
                                        //new ConnectScreen(new TitleScreen(), mc, address, port);
                                        //mc.getNetworkHandler().
                                    }



                                if(args.length==1)
                                    if (args[0].equalsIgnoreCase("test")) {
                                        //mc.player.sendMessage(new LiteralText("---------------------[RemoteController]---------------------" + rcv), false);
                                        mc.openScreen(new DisconnectedScreen(new TitleScreen(), Text.of("TextTest?"), Text.of("Ich hab kein Gruuund!")));
                                    }


                            }catch(Exception e){
                                if(e instanceof java.util.NoSuchElementException){
                                    System.out.println("Server connection lost...");
                                    connected = false;
                                }else
                                    e.printStackTrace();
                            }

                        }


                    }


            }
        };
        thread.start();
    }



}
