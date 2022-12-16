package Server;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ServerProgram {
    
    private ServerSocket ssck;
    private static HashMap<String, ClientHandler> cMap = new HashMap<String, ClientHandler>();
    final static DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<String>();
    final static JComboBox<String> cb_clients = new JComboBox<String>(cbModel);

    public void addComponentToPane(Container pane) 
    {
        JPanel titlePane = new JPanel();
        JPanel functionPane = new JPanel(new BorderLayout());
        JPanel botPane = new JPanel();

        JLabel lb_title = new JLabel("Click here to open server");
        JButton btn_start = new JButton("Start");
        JTextField txfChatBox = new JTextField(20);
        

        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //Thread run
            }
        });

        txfChatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    //Get selected client username
                    String selected = cb_clients.getSelectedItem().toString();
                    ClientHandler clh = cMap.get(selected);
                    if (clh == null) {
                        //No client
                        return;
                    }
                    DataOutputStream dos = clh.getDOS();
                    dos.writeUTF("CHAT");
                    dos.writeUTF(txfChatBox.getText());
                    dos.flush();
                    txfChatBox.setText("");                
                        
                    
                }
                catch(IOException exc) {
                    exc.getStackTrace();
                }
            }
        });

        titlePane.add(lb_title);
        functionPane.add(btn_start, BorderLayout.NORTH);
        functionPane.add(cb_clients, BorderLayout.CENTER);
        botPane.add(txfChatBox);
        
        pane.add(titlePane, BorderLayout.NORTH);
        pane.add(functionPane, BorderLayout.CENTER);
        pane.add(botPane, BorderLayout.SOUTH);
    }



    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("ServerControlPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        ServerProgram demo = new ServerProgram();
        demo.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);

        try {
            demo.ssck = new ServerSocket(3000);
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
        //running new thread to connecting socket
        //to isplaying javax.swing interface
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Socket sck = demo.ssck.accept();
                        ClientHandler clh = new ClientHandler(sck, cbModel);
                        
                        String clientUsername = clh.getUsername();
                        //Suppose client usernames are unique
                        cMap.put(clientUsername, clh);
                        cbModel.addElement(clientUsername);
                        clh.start();
                        
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public ServerProgram() {

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
