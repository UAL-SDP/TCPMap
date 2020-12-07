package pt.ual.sdp;

public class MainNode {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong number of arguments.");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);

        // Thread to deal with node registry.
        MainNodeRegister mainNodeRegister = new MainNodeRegister(port);
        mainNodeRegister.start();
    }
}
