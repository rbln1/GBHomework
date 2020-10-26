package me.rubl.chat.server.core;

import me.rubl.network.ServerSocketThread;
import me.rubl.network.ServerSocketThreadListener;
import me.rubl.network.SocketThread;
import me.rubl.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    Vector<SocketThread> clients = new Vector<>();

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss: ");

    private ServerSocketThread server = null;
    public void start(int port) {
        if (server != null && server.isAlive()) {
            System.out.println("Server already started");
        } else {
            server = new ServerSocketThread(this, "Chat server", 8189, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            System.out.println("Server is not running");
        } else {
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        System.out.println(msg);
    }

    /**
     * Server methods
     *
     * */

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server thread started");
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server thread stopped");

    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("Server timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        clients.add(new SocketThread(name, this, socket));
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * Socket methods
     *
     * */

    @Override
    public synchronized void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket created");

    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        putLog("Socket stopped");
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) == thread) clients.remove(i);
        }
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");

    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        for (int i = 0; i < clients.size(); i++) {
            SocketThread client = clients.get(i);
            if (client != thread) client.sendMessage(msg);
        }
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) == thread) clients.remove(i);
        }
    }
}
