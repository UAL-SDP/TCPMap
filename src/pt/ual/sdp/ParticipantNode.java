package pt.ual.sdp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ParticipantNode {
    private int id;

    public ParticipantNode(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        if(args.length != 3){
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        String mainNodeAddress = args[0];
        int mainNodePort = Integer.parseInt(args[1]);
        int port = Integer.parseInt(args[2]);

        // Register with the MainNode
        Socket socket = null;
        try {
            socket = new Socket(mainNodeAddress, mainNodePort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println(port);
        printWriter.flush();

        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(id);
        ParticipantNode participantNode = new ParticipantNode(id);
    }
}
