package me.rubl.chat.server.gui;

import me.rubl.chat.server.core.ChatServer;
import me.rubl.chat.server.core.ChatServerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler, ChatServerListener {
    private static final int POS_X = 800;
    private static final int POS_Y = 200;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final ChatServer chatServer = new ChatServer(this);
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JPanel panelTop = new JPanel(new GridLayout(1, 2));
    private final JTextArea log = new JTextArea();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ServerGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ServerGUI() throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        JScrollPane scrollLog = new JScrollPane(log);
        log.setEditable(false);
        log.setLineWrap(true);
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);

        panelTop.add(btnStart);
        panelTop.add(btnStop);
        add(panelTop, BorderLayout.NORTH);
        add(scrollLog, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStop) {
            chatServer.stop();
        } else if (src == btnStart) {
            chatServer.start(8189);
        } else {
            throw new RuntimeException("Unknown action source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = "Exception in " + t.getName() + " " +
                e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at " + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    @Override
    public void onChatServerMessage(String msg) {
        putToUILog(msg);
    }

    private void putToUILog(String msg) {
        SwingUtilities.invokeLater(() -> {
            log.append(msg + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }
}
