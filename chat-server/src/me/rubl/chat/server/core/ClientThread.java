package me.rubl.chat.server.core;

import me.rubl.chat.common.Library;
import me.rubl.network.SocketThread;
import me.rubl.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(name, listener, socket);
    }


    public String getNickname() {
        return nickname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    void authAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Library.Message.formatForAuthAccept(nickname));
    }

    void authFail(String msg) {
        sendMessage(Library.Message.formatForAuthDenied(msg));
        close();
    }

    void msgFormatError(String msg) {
        sendMessage(Library.Message.formatForMsgFormatError(msg));
        close();
    }

}
