package pt.ual.sdp.nodes.participant;

import pt.ual.sdp.util.SocketUtil;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ParticipantNodeThread extends Thread {
    private ParticipantNode participantNode;
    private Socket socket;

    public ParticipantNodeThread(ParticipantNode participantNode, Socket socket) {
        super();
        this.participantNode = participantNode;
        this.socket = socket;
    }

    @Override
    public synchronized void start() {
        Scanner scanner = SocketUtil.getScanner(socket);
        PrintWriter printWriter = SocketUtil.getPrintWritter(socket);
        String operation = scanner.nextLine();
        String key, value;
        switch (operation) {
            case "R":
                key = scanner.nextLine();
                value = scanner.nextLine();
                // TODO: check if the key should belong to this ParticipantNode.
                participantNode.getDatabase().put(key, value);
                printWriter.println("OK");
                printWriter.flush();
                break;
            default:
                break;
        }
        SocketUtil.closeSocket(socket);
    }
}
