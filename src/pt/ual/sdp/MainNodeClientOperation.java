package pt.ual.sdp;

import pt.ual.sdp.util.SocketUtil;
import pt.ual.sdp.views.MainNode;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainNodeClientOperation extends Thread {
    private MainNode mainNode;
    private Socket clientSocket;

    public MainNodeClientOperation(MainNode mainNode, Socket socket) {
        this.mainNode = mainNode;
        this.clientSocket = socket;
    }

    @Override
    public synchronized void start() {
        Scanner scanner = SocketUtil.getScanner(clientSocket);
        Socket participantNodeSocket = null;
        String operation = scanner.nextLine();
        String key, value;
        switch (operation) {
            case "R":
                key = scanner.nextLine();
                value = scanner.nextLine();
                ParticipantNodeRecord node = mainNode.getNode(key);
                participantNodeSocket = SocketUtil.getSocket(node.getAddress(), node.getPort());

                PrintWriter participantNodePrintWritter = SocketUtil.getPrintWritter(participantNodeSocket);
                participantNodePrintWritter.println(operation);
                participantNodePrintWritter.flush();
                participantNodePrintWritter.println(key);
                participantNodePrintWritter.flush();
                participantNodePrintWritter.println(value);
                participantNodePrintWritter.flush();

                Scanner participantNodeScanner = SocketUtil.getScanner(participantNodeSocket);
                String response = participantNodeScanner.nextLine();

                PrintWriter clientPrintWritter = SocketUtil.getPrintWritter(clientSocket);
                clientPrintWritter.println(response);
                clientPrintWritter.flush();
                break;
            case "C":
                break;
            case "D":
                break;
            case "L":
                break;
        }

        SocketUtil.closeSocket(clientSocket);
        SocketUtil.closeSocket(participantNodeSocket);
    }


}
