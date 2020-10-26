package me.rubl.chat.client;

import me.rubl.network.SocketThread;
import me.rubl.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;


public class ClientGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea taLog = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    private boolean isAuthorizationMode = true;

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);

        taLog.setEditable(false);
        taLog.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(taLog);
        JScrollPane scrollUser = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"};
        userList.setListData(users);
        scrollUser.setPreferredSize(new Dimension(100, 0));

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);
        btnSend.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            disconnect();
        } else {
            showException(Thread.currentThread(), new RuntimeException("Unknown action source: " + src));
        }
    }

    private void connect() {
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread("Client", this, socket);
            changeLogonState();
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }
    }
    private void disconnect() {
        if (socketThread != null && socketThread.isAlive()) {
            System.out.println("disconnect OK");
            socketThread.close();
            changeLogonState();
        }
    }

    private void sendMessage() {

        String msg = tfMessage.getText().trim();

        if (!msg.isEmpty()) {
            // send message
            boolean isSent = socketThread.sendMessage(msg);
            if (isSent) {
                // add message in textArea
                putInTextArea(String.format("You: %s", msg));
            }
        }
        // clear tfMessage
        tfMessage.setText("");
        // return focus to tfMessage
        tfMessage.grabFocus();
    }

   private void putInTextArea(String msg) {
        if (msg.trim().isEmpty()) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                taLog.append(msg + "\n");
                taLog.setCaretPosition(taLog.getDocument().getLength());
            }
        });
    }

    private void changeLogonState() {
        isAuthorizationMode = !isAuthorizationMode;

        panelTop.setVisible(isAuthorizationMode);
        panelBottom.setVisible(!isAuthorizationMode);
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    /**
     * Socket thread listener methods
     * */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putInTextArea("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putInTextArea("Stop");
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putInTextArea("Ready");
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        putInTextArea("Somebody: " + msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        showException(thread, exception);
    }

}
