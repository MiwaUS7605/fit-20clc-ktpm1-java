package Server;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket sck;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String username;
    private DefaultComboBoxModel<String> cbModel;
    
    public ClientHandler(Socket sck, DefaultComboBoxModel<String> cbModel) throws IOException {
		this.setSocket(sck);
        this.dis = new DataInputStream(this.sck.getInputStream());
        this.dos = new DataOutputStream(this.sck.getOutputStream());
		this.setUsername(this.dis.readUTF());
        this.cbModel = cbModel;
	}

    public void setSocket(Socket sck) {
		this.sck = sck;
		try {
			this.dis = new DataInputStream(sck.getInputStream());
			this.dos = new DataOutputStream(sck.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void closeSocket() {
		if (this.sck != null) {
			try {
				this.sck.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public DataOutputStream getDOS() {
        return this.dos;
    }

    public DataInputStream getDIS() {
        return this.dis;
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean setUsername(String username) {
        if (!username.equals("")) {
            this.username = username;
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {   
                String message = null;
                
                //read user request
                message = dis.readUTF();

                if (message.equals("DISCONNECT")) {
                    this.cbModel.removeElement(this.username);
                    this.closeSocket();
                    break;
                }
                else if (message.equals("CHAT")) {
                    String receivedChat = dis.readUTF();
                }

            }
            catch(IOException exc) {
                exc.printStackTrace();
            }

        }
    }

}
