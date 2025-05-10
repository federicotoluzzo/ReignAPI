package reign.network;

import reign.Reign;
import reign.types.ResourceType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class PeerThread implements Runnable {
    public Reign reign;
    private Socket client;

    public PeerThread(Socket client){
        this.client = client;
    }

    public static ArrayList<String> getOffers(String ip) {
        Socket socket = null;
        ArrayList<String> offers = new ArrayList<String>();
        try{
            socket = new Socket(ip, 6832);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET_OFFERS");
            out.flush();

            String line = in.readLine();

            while (!line.isEmpty()) {
                offers.add(line);
            }

            out.close();
            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return offers;
    }

    public static ArrayList<String> acceptOffer(String ip, int id, ResourceType resource, int quantity) {
        Socket socket = null;
        ArrayList<String> offers = new ArrayList<String>();
        try{
            socket = new Socket(ip, 6832);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("ACCEPT_OFFER");
            out.println("" + id);
            out.println("" + resource);
            out.println("" + quantity);
            out.flush();

            String line = in.readLine();

            while (!line.isEmpty()) {
                offers.add(line);
            }

            out.close();
            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return offers;
    }

    @Override
    public void run() {
        try {
            PrintWriter output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String line = input.readLine();

            if (line.equalsIgnoreCase("GET_OFFERS")){
                for (String offer : reign.offers){
                    output.println(offer);
                }
                output.println();
                output.flush();
            } else if (line.equalsIgnoreCase("ACCEPT_OFFER")){
                line = input.readLine();
                if(reign.offers.get(line) != null && Objects.equals(reign.offers.get(line), input.readLine() + " " + input.readLine())){
                    output.println(reign.offers.get(line));
                } else {
                    output.println("NACK");
                }
                output.flush();
            }
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
