package Client;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ClientProgram {

    // default host
    private String host;
    private int port;
    private String client_name;
    private Socket sck;
    private DataInputStream dis;
    private DataOutputStream dos;
    private JPanel cards;
    final static String CONNECTPANEL = "ConnectPanel";
    final static String CHATPANEL = "ChatPanel";

    private JTextArea txaArea = new JTextArea(10, 30);

    public void specifyConnectInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void specifyName(String name) {
        this.client_name = name;
    }

    public void Connect() throws IOException {

        if (this.sck != null) {
            this.sck.close();
        }
        this.sck = new Socket(this.host, this.port);
        JOptionPane.showMessageDialog(null, "Successfully connect to server!", "NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
        this.dis = new DataInputStream(this.sck.getInputStream());
        this.dos = new DataOutputStream(this.sck.getOutputStream());
        
        //Write client_name to server(ClientHandler)
        this.dos.writeUTF(this.client_name);

        while (true) {
            String receivedString = this.dis.readUTF();
            if (receivedString.equals("DISCONNECT")) {
                break;
            } 
            else if (receivedString.equals("CHAT")){
                receivedString = this.dis.readUTF();
                txaArea.setText(txaArea.getText() +
                        "Server: " + receivedString + "\n");
            }
        }
    }

    public void addComponentToPane(Container pane) {

        JPanel chatCard = new JPanel(new BorderLayout());
        JPanel connectCard = new JPanel(new BorderLayout());

        //ConnectPanel
        JPanel infoPane = new JPanel(new GridLayout(3,2));
        JLabel lb_name = new JLabel("Name");
        JLabel lb_ip = new JLabel("IP");
        JLabel lb_port = new JLabel("Port");
        JTextField txf_name = new JTextField();
        JTextField txf_ip = new JTextField();
        JTextField txf_port = new JTextField();

        JButton btn_submit = new JButton("Connect");
        
        btn_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                specifyName(txf_name.getText());
                specifyConnectInfo(txf_ip.getText(), Integer.parseInt(txf_port.getText()));
                
                Thread t = new Thread() {
                    public void run() {
                        try {
                            Connect();
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Connect Failure!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                t.start();
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, CHATPANEL);
                
            }
        });
        
        infoPane.add(lb_name);
        infoPane.add(txf_name);
        infoPane.add(lb_ip);
        infoPane.add(txf_ip);
        infoPane.add(lb_port);
        infoPane.add(txf_port);

        connectCard.add(infoPane, BorderLayout.NORTH);
        connectCard.add(btn_submit, BorderLayout.CENTER);

        //ChatPanel
        JPanel titlePane = new JPanel();
        JPanel logPane = new JPanel();
        JPanel botPane = new JPanel(new BorderLayout());

        JLabel lb_title = new JLabel("Chat");
        lb_title.setAlignmentX(JLabel.CENTER);

        JScrollPane scpScroller = new JScrollPane(txaArea);
        txaArea.setEditable(false);

        JTextField txfChatBox = new JTextField(30);
        txfChatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    //Send command
                    dos.writeUTF("CHAT");
                    //Get message from chatbox and send it to the server
                    String msg = txfChatBox.getText();
                    dos.writeUTF(msg);
                    //Update text area
                    txaArea.setText(txaArea.getText() + client_name + ": " + msg + "\n");
                    txfChatBox.setText("");
                }
                catch(IOException exc) {
                    exc.printStackTrace();
                }

            }
        });
        JButton btn_disconnect = new JButton("DISCONNECT");
        btn_disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    //Send DISCONNECT command and client name
                    dos.writeUTF("DISCONNECT");
                    dos.writeUTF(client_name);
                    //Close connection
                    sck.close();
                    //Change layout
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, CONNECTPANEL);
                }
                catch(IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        titlePane.add(lb_title);
        logPane.add(scpScroller);
        botPane.add(txfChatBox, BorderLayout.NORTH);
        botPane.add(btn_disconnect, BorderLayout.CENTER);
        
        chatCard.add(titlePane, BorderLayout.NORTH);
        chatCard.add(logPane, BorderLayout.CENTER);
        chatCard.add(botPane, BorderLayout.SOUTH);

        //Config CardLayout
        cards = new JPanel(new CardLayout());
        cards.add(connectCard, CONNECTPANEL);
        cards.add(chatCard, CHATPANEL);

        pane.add(cards);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ClientChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        ClientProgram demo = new ClientProgram();
        demo.addComponentToPane(frame.getContentPane());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                //Do something
            } 
        });

        frame.pack();
        frame.setVisible(true);
    }

    public ClientProgram() {

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}