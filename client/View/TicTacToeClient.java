package client.View;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TicTacToeClient {

	private JFrame frmTicTacToe;
	private String playerName;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton userName;
	private JTextArea outputWindow;
	private JTextArea charPlayer;
	private Socket clientSocket;
    private JTextArea displayArea;
    private JTextArea displayName;
    private JTextArea displayPlayer;
    private PrintWriter socketOut;
	private BufferedReader socketIn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TicTacToeClient window = new TicTacToeClient();
			window.frmTicTacToe.setVisible(true);
			window.listen();
		} catch (Exception e) {
				e.printStackTrace();
		}

	}

	/**
	 * Create the application.
	 */
	public TicTacToeClient() {
		
		try {
			clientSocket = new Socket("localhost", 9090);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTicTacToe = new JFrame();
		frmTicTacToe.setTitle("Tic-Tac-Toe Game");
		frmTicTacToe.setBounds(100, 100, 590, 335);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(null);
		
		JButton button_1 = new JButton("");
		button1 = button_1;
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,0-0";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_1.setBounds(48, 65, 54, 40);
		frmTicTacToe.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("");
		button2 = button_2;
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,0-1";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_2.setBounds(112, 65, 54, 40);
		frmTicTacToe.getContentPane().add(button_2);
		
		JButton button_3 = new JButton("");
		button3 = button_3;
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,0-2";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_3.setBounds(176, 65, 54, 40);
		frmTicTacToe.getContentPane().add(button_3);
		
		JButton button_4 = new JButton("");
		button4 = button_4;
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,1-0";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_4.setBounds(48, 112, 54, 40);
		frmTicTacToe.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("");
		button5 = button_5;
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,1-1";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_5.setBounds(112, 112, 54, 40);
		frmTicTacToe.getContentPane().add(button_5);
		
		JButton button_6 = new JButton("");
		button6 = button_6;
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,1-2";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_6.setBounds(176, 112, 54, 40);
		frmTicTacToe.getContentPane().add(button_6);
		
		JButton button_7 = new JButton("");
		button7 = button_7;
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,2-0";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_7.setBounds(48, 163, 54, 40);
		frmTicTacToe.getContentPane().add(button_7);
		
		JButton button_8 = new JButton("");
		button8 = button_8;
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,2-1";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_8.setBounds(112, 163, 54, 40);
		frmTicTacToe.getContentPane().add(button_8);
		
		JButton button_9 = new JButton("");
		button9 = button_9;
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "MOVE,2-2";
				socketOut.println(msg);
				socketOut.flush();
			}
		});
		button_9.setBounds(176, 163, 54, 40);
		frmTicTacToe.getContentPane().add(button_9);
		
		
		JTextArea display_Area = new JTextArea();
		display_Area.setBounds(308, 65, 211, 138);
		frmTicTacToe.getContentPane().add(display_Area);
		displayArea = display_Area;
		
		JLabel lblNewLabel = new JLabel("Message Window");
		lblNewLabel.setBounds(308, 40, 172, 14);
		frmTicTacToe.getContentPane().add(lblNewLabel);
		
		JLabel lblCurrentPlayer = new JLabel("Player name");
		lblCurrentPlayer.setBounds(279, 247, 112, 14);
		frmTicTacToe.getContentPane().add(lblCurrentPlayer);
		
		JTextArea nameArea = new JTextArea();
		displayName = nameArea;
		nameArea.setBounds(360, 242, 101, 22);
		frmTicTacToe.getContentPane().add(nameArea);	
		
		
		JButton submit = new JButton("Save");
		userName = submit;
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!displayName.getText().equals("")){
		            String name = displayName.getText();
		            String msg = "NAME," + name;
					socketOut.println(msg);
					socketOut.flush();
		        }

			}
		});
		submit.setBounds(471, 243, 89, 23);
		frmTicTacToe.getContentPane().add(submit);
		
		JTextArea currentPlayerArea = new JTextArea();
		displayPlayer = currentPlayerArea;
		currentPlayerArea.setBounds(126, 231, 26, 22);
		frmTicTacToe.getContentPane().add(currentPlayerArea);
		
		JLabel lblNewLabel_1 = new JLabel("Current Player");
		lblNewLabel_1.setBounds(30, 236, 89, 14);
		frmTicTacToe.getContentPane().add(lblNewLabel_1);
	}
	
	public void listen(){
         try {
//        	 in = new ObjectInputStream(clientSocket.getInputStream());
        	 socketOut = new PrintWriter((clientSocket.getOutputStream()), true);
 			 socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 			 socketOut.flush();
 			 
             while(true){
            	 String response = socketIn.readLine();
            	 String [] message = response.split(",");
            	 String msgType = message[0];
            	 String msgContent = message[1];
            	 
            	 if(msgType.equals("MARK")){
                	 displayPlayer.setText(msgContent);
                 }
                 if(msgType.equals("MSG")){     
				     displayArea.setText(msgContent);
				 }
				 if(msgType.equals("END")){
				     displayArea.setText(msgContent);
				     break;
				 }
				 if(msgType.equals("VALID")){
					 String[] playerMove = msgContent.split(" "); 
				     String location = playerMove[0];
				     String mark = playerMove[1];
				     switch(location){
				         case "0-0":
				             button1.setEnabled(false);
				             button1.setText(mark);
				             break;
				         case "0-1":
				             button2.setEnabled(false);
				             button2.setText(mark);
				             break;
				         case "0-2":
				             button3.setEnabled(false);
				             button3.setText(mark);
				             break;
				         case "1-0":
				             button4.setEnabled(false);
				             button4.setText(mark);
				             break;
				         case "1-1":
				             button5.setEnabled(false);
				             button5.setText(mark);
				             break;
				         case "1-2":
				             button6.setEnabled(false);
				             button6.setText(mark);
				             break;
				         case "2-0":
				             button7.setEnabled(false);
				             button7.setText(mark);
				             break;
				         case "2-1":
				             button8.setEnabled(false);
				             button8.setText(mark);
				             break;
				         case "2-2":
				             button9.setEnabled(false);
				             button9.setText(mark);                          
				             break;
				     }
				 }
             }
             socketIn.close();
             socketOut.close();
             clientSocket.close();
         } catch (IOException e) {
             System.out.println(e.toString());
         }
	 }
}
