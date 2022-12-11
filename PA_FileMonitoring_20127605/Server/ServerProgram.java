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
	private Socket sck;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    final static DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<String>();


    public void addComponentToPane(Container pane) 
    {
        JPanel titlePane = new JPanel();
        JPanel functionPane = new JPanel(new BorderLayout());
        JPanel botPane = new JPanel();

        JLabel lb_title = new JLabel("Click here to open server");
        JButton btn_start = new JButton("Start");
        JTextField txfChatBox = new JTextField(20);
        
        txfChatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    dos.writeUTF(txfChatBox.getText());
                    dos.flush();
                    txfChatBox.setText("");
                }
                catch(IOException exc) {
                    exc.getStackTrace();
                }
            }
        });

        JComboBox<String> cb_clients = new JComboBox<String>(cbModel);
        
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

        Thread t = new Thread() {
            public void run() {
                try {
                    ServerProgram demo = new ServerProgram();
                    demo.addComponentToPane(frame.getContentPane());

                    frame.pack();
                    frame.setVisible(true);

                    demo.ssck = new ServerSocket(3000);
                    
                    
                    while (true) {
                        demo.sck = demo.ssck.accept();
                        dis = new DataInputStream(demo.sck.getInputStream());
                        dos = new DataOutputStream(demo.sck.getOutputStream());
                        cbModel.addElement(dis.readUTF());

                        String receivedMessage = dis.readUTF();

                        if (receivedMessage.equals("quit")) {
                            dos.writeUTF("close");
                            break;
                        }
                        else {
                            
                        }
                    }
                    demo.sck.close();
                }
                catch(IOException exc) {
                    exc.printStackTrace();
                }
            }
        };
        t.start();
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