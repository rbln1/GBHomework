package me.rubl.chat.server.core;

import me.rubl.chat.common.Library;
import me.rubl.network.ServerSocketThread;
import me.rubl.network.ServerSocketThreadListener;
import me.rubl.network.SocketThread;
import me.rubl.network.SocketThreadListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.*;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss: ");

    private static final Logger logger = Logger.getLogger("");
    private static final String LOG_PREFIX = "GBLog | ";

    private ServerSocketThread server = null;
    private final ChatServerListener listener;
    private final Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener listener) throws IOException {
        this.listener = listener;

        logger.setLevel(Level.ALL);

        Handler fileHandler = new FileHandler("server_logs.log", true);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                System.out.println(record.getMessage());
                return "[" + DATE_FORMAT.format(record.getMillis()) +
                        "| " + record.getLevel() +
                        "]: " + record.getMessage().substring(LOG_PREFIX.length());
            }
        });
        fileHandler.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                System.out.println(record.getMessage().startsWith(LOG_PREFIX));
                return record.getMessage().startsWith(LOG_PREFIX);
            }
        });
        logger.addHandler(fileHandler);
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            putToLog("Server already started", true);
        } else {
            server = new ServerSocketThread(this, "Chat server", 8189, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putToLog("Server is not running", true);
        } else {
            server.interrupt();
        }
    }

    private void putToLog(String msg, boolean showInUI) {

        logger.log(Level.INFO, LOG_PREFIX + msg + "\n");

        if (showInUI) {
            msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                    Thread.currentThread().getName() + ": " + msg;

            listener.onChatServerMessage(msg);
        }
    }

    /**
     * Server methods
     *
     * */

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putToLog("Server thread started", true);
        SqlClient.connect();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putToLog("Server thread stopped", true);
        SqlClient.disconnect();
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putToLog("Server socket created", false);

    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        //putToLog("Server timeout", false);
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putToLog("Client connected", true);
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
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
        putToLog("Socket created", false);
    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        clients.remove(thread);
        ClientThread clientThread = (ClientThread) thread;
        if (clientThread.isAuthorized()) {
            String message = clientThread.getNickname() + " disconnected";
            putToLog(message, true);
            sendToAllAuthorizedClients(Library.Message.formatForBroadcastMessage("Server", message));
        }
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAutorizedMessage(client, msg);
        } else {
            handleNonAuthorizedMessage(client, msg);
        }
    }

    private void handleNonAuthorizedMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Library.Message.DELIMITER);

        if (arr.length != Library.Message.AUTH_REQUEST_LENGTH ||
                !arr[Library.Message.PREFIX_INDEX].equals(Library.Message.AUTH_REQUEST_PREFIX)) {
            client.msgFormatError(msg);
            return;
        }

        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putToLog("Auth failed with login: " + login, false);
            client.authFail("Unknown login or password");
            return;
        }
        putToLog("Auth completed successfully with login: " + login, false);
        client.authAccept(nickname);
        sendToAllAuthorizedClients(Library.Message.formatForBroadcastMessage("Server", nickname + " connected"));
    }

    private void handleAutorizedMessage(ClientThread client, String msg) {
        putToLog("Client '" + client.getNickname() + "' sent message: " + msg, false);
        sendToAllAuthorizedClients(Library.Message.formatForBroadcastMessage(client.getNickname(), msg));
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }
}
