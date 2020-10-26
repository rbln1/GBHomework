package me.rubl.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SocketThread extends Thread {
    private final SocketThreadListener listener;
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public SocketThread(String name, SocketThreadListener listener, Socket socket) {
        super(name);
        this.listener = listener;
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onSocketStart(this, socket);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()) {
                String msg = in.readUTF();
                listener.onReceiveString(this, socket, msg);
            }
        } catch (EOFException | SocketException e) {
            // do nothing
        } catch (IOException e) {
            listener.onSocketException(this, e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                listener.onSocketException(this, e);
            }
            listener.onSocketStop(this);
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            close();
            return false;
        }
    }

    public synchronized void close() {
        interrupt();
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }
}
