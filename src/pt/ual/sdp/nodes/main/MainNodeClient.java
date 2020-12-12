package pt.ual.sdp.nodes.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainNodeClient extends Thread {
    private MainNode mainNode;
    private int port;

    public MainNodeClient(MainNode mainNode, int port) {
        super();
        this.mainNode = mainNode;
        this.port = port;
    }

    @Override
    public synchronized void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new MainNodeClientOperation(mainNode, socket).start();
        }
    }
}
