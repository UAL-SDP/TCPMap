package pt.ual.sdp;

import pt.ual.sdp.util.SocketUtil;
import pt.ual.sdp.views.MainNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainNodeRegister extends Thread {
    private int port;
    private int nodeCount;
    private MainNode mainNode;

    public MainNodeRegister(MainNode mainNode, int port) {
        super();
        this.mainNode = mainNode;
        this.port = port;
        this.nodeCount = 1;
    }

    @Override
    public synchronized void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Cannot create ServerSocket on port "+port);
            e.printStackTrace();
        }
        while(true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Could not create socket on accept.");
                e.printStackTrace();
            }
            String nodeAddress = socket.getInetAddress().getHostAddress();
            Scanner scanner = SocketUtil.getScanner(socket);
            int nodePort = Integer.parseInt(scanner.nextLine());
            int nodeId = nodeCount++;
            ParticipantNodeRecord nodeRecord = new ParticipantNodeRecord(nodeAddress, nodePort);
            mainNode.getNodes().put(String.valueOf(nodeId), nodeRecord);
            PrintWriter printWriter = SocketUtil.getPrintWritter(socket);
            printWriter.println(nodeId);
            printWriter.flush();
        }
    }
}
