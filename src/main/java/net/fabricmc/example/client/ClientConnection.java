package net.fabricmc.example.client;

import com.ibm.icu.impl.StaticUnicodeSets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import javax.swing.text.JTextComponent;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.util.Scanner;
import java.util.UUID;

public class ClientConnection {

    static Thread thread;
    static Socket s;
    static PrintWriter out;
    static Scanner in;

    static boolean connected = false;

    static Thread syncronizer;

    public static void connectClient(String ip, int port){

        MinecraftClient mc = MinecraftClient.getInstance();


        syncronizer = new Thread() {
            @Environment(EnvType.CLIENT)
            @Override
            public void run(){
                while(true) {
                    try {Thread.currentThread().sleep(500);} catch (InterruptedException ex) {ex.printStackTrace();}
                    if(MinecraftClient.getInstance().player!=null)
                        syncLocation();

                    syncServer();
                    syncHP();
                    syncHunger();
                }
            }
        };
        syncronizer.start();

         thread = new Thread() {
            @Environment(EnvType.CLIENT)
            public void run(){
                    while(true) {

                        //MinecraftClient.getInstance().getWindow().setTitle("Connected: "+String.valueOf(connected));

                        if(!connected){
                            try {Thread.currentThread().sleep(5*1000);} catch (InterruptedException ex) {ex.printStackTrace();}
                            System.out.println("Not Connected... ==> Trying to Connect...");

                            try{
                                System.out.println("Connecting to "+ip+":"+port);
                                s = new Socket(ip, port);
                                out = new PrintWriter(s.getOutputStream());
                                in = new Scanner(s.getInputStream());
                                connected = true;
                                syncName();



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
                                    if (args[0].equalsIgnoreCase("b_farm_logs")) {
                                        Thread.currentThread().sleep(3000);

                                        //mc.player.sendChatMessage("#mine jungle_log oak_log spruce_log birch_log");
                                        keyOrderType("t#mine jungle_log oak_log spruce_log birch_log");
                                        //mc.player.sendSystemMessage(Text.of("#mine jungle_log oak_log spruce_log birch_log"), UUID.fromString(MinecraftClient.getInstance().getSession().getUuid()));
                                    }

                                if(args.length>=2)
                                    if (args[0].equalsIgnoreCase("chat")) {
                                        mc.player.sendChatMessage(args[1].replace("_p/p_", ":"));
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

    public static void keyOrderType(String toType){
        for(String c : toType.split("")){
            InputUtil.Key toPress;
            if(c.equalsIgnoreCase("#"))
                toPress = InputUtil.fromTranslationKey("key.keyboard.hashtag");
            if(c.equalsIgnoreCase(" "))
                toPress = InputUtil.fromTranslationKey("key.keyboard.space");
            else
                toPress = InputUtil.fromTranslationKey("key.keyboard."+c);
            KeyBinding.setKeyPressed(toPress, true);
            try {Thread.currentThread().sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
            KeyBinding.setKeyPressed(toPress, false);
        }
    }

    public static void sendMessage(String text){
        if(out!=null){
            out.println(text);
            out.flush();
        }
    }

    public static void syncXCoord(){
        sendMessage("CoordX:"+MinecraftClient.getInstance().player.getX());
    }
    public static void syncYCoord(){
        sendMessage("CoordY:"+MinecraftClient.getInstance().player.getY());
    }
    public static void syncZCoord(){
        sendMessage("CoordZ:"+MinecraftClient.getInstance().player.getZ());
    }
    public static void syncWorld(){
        //MinecraftClient.getInstance().getNetworkHandler().getWorld().
    }


    public static void syncHP() {
        if(MinecraftClient.getInstance().player!=null)
            sendMessage("PlayerHP:"+MinecraftClient.getInstance().player.getHealth());
    }
    public static void syncHunger() {
        System.out.println("HS");
        if(MinecraftClient.getInstance().player!=null)
            sendMessage("PlayerHunger:"+MinecraftClient.getInstance().player.getHungerManager().getFoodLevel());
    }

    public static void syncLocation(){
        syncXCoord();
        syncYCoord();
        syncZCoord();
        syncWorld();
    }

    public static void syncName() {
        sendMessage("Name:"+ MinecraftClient.getInstance().getSession().getUsername());
    }

    public static void syncServer(){
        if(MinecraftClient.getInstance().getNetworkHandler()!=null)
            sendMessage("ServerIP:"+MinecraftClient.getInstance().getNetworkHandler().getConnection().getAddress());
        else
            sendMessage("ServerIP:null");
    }

}
