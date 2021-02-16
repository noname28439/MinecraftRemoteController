package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import display.ConnectionListFrame;
import display.Map;
import display.World;

public class ServerConnection implements Runnable {

    Socket connection;
    PrintWriter out;
    Scanner in;
    Thread listener;

    public static String commandSeperator = ":";


    public String name = "Not Named";

    public int[] location = new int[3];

    public int hp = 0;
    public int food = 0;
    
    public String ip = "";
    
    
    
    //For draw
    public boolean selected = false;
    public int[] goal;
    
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
                //System.out.println("--> "+rcv);
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
                
                if(args[0].equalsIgnoreCase("PlayerHP"))
                	hp = Integer.valueOf(args[1].split("\\.")[0]);
                
                if(args[0].equalsIgnoreCase("PlayerHunger"))
                	food = Integer.valueOf(args[1].split("\\.")[0]);
                
                if(args[0].equalsIgnoreCase("ServerIP")) {
                	String build = "";
                	for(int i = 1; i<args.length;i++)
                		build+=":"+args[i];
                	build = build.replaceFirst(":", "");
                	ip = build;
                }
                
                if(args[0].equalsIgnoreCase("BlockAt")) {
                	//System.out.println("--> "+rcv);
                	if(ip.equalsIgnoreCase(World.watchServer)) {
                		int x = Integer.valueOf(args[1]);
                    	int z = Integer.valueOf(args[2]);
                    	int id = Integer.valueOf(args[3]);
                    	
//                    	try {
//                    	for(int i = 0; i<Map.mapData.size();i++){
//                    		int[] ci = Map.mapData.get(i);
//                            if(ci[0]==x&&ci[1]==z)
//                                Map.mapData.remove(ci);
//                        }
//                    	}catch (java.util.ConcurrentModificationException|java.lang.NullPointerException e) {
//    						e.printStackTrace();
//    					}
                    	
                    	Map.mapData.add(new int[] {x, z, id});
                	}
                	
                	
                }
                
                

            }catch (Exception e) {
            	if(e instanceof java.util.NoSuchElementException) {
            		System.err.println(name+" lost connection...");
                    ControllServer.connections.remove(this);
                    listener.stop();
            	}else
            		e.printStackTrace();
                
            }

        }

    }
    
    public void clientKill() {
    	sendMessage("kill");
    }
    
	public void clientConnect(String ip, int port) {
		sendMessage("connect:"+ip+":"+port);
	}

	public void clientDisconnect() {
		sendMessage("disconnect");
	}
	
	public void clientBaritoneGoal(int x, int y, int z) {
		sendMessage("b_goal:"+x+":"+y+":"+z);
	}
	public void clientBaritonePath() {
		sendMessage("b_path");
	}
	public void clientBaritoneStop() {
		sendMessage("b_stop");
	}
    
    
    
    public void killConnection() {
        System.out.println("Killing "+name+"...");
        ControllServer.connections.remove(this);
        listener.stop();
    }

    public void sendMessage(String text) {
        out.println(text);
        out.flush();
    }

}
