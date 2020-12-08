package pt.ual.sdp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParticipantNode {
    private int id;
    private Map<String, String> database;

    public ParticipantNode(int id) {
        this.id = id;
        this.database = new HashMap<>();
    }

    synchronized public Map<String, String> getDatabase() {
        return database;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        String mainNodeAddress = args[0];
        int mainNodePort = Integer.parseInt(args[1]);
        int port = Integer.parseInt(args[2]);

        // Register with the MainNode
        Socket registrySocket = null;
        try {
            registrySocket = new Socket(mainNodeAddress, mainNodePort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(registrySocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println(port);
        printWriter.flush();

        Scanner scanner = null;
        try {
            scanner = new Scanner(registrySocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("[Participant id: " + id + "]");

        ParticipantNode participantNode = new ParticipantNode(id);

        // Accepts and respond to requests from the MainNode
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new ParticipantNodeThread(participantNode, socket).start();
        }
    }
}
