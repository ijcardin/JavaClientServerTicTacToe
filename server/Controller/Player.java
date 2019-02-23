package server.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import server.model.Board;
/**
 * 
 * @author Ian
 *
 */
public class Player implements Runnable {

	private String myName;
	private char mark;
	private Board board;
	private Player opponent;
	private Player currentPlayer;
	private boolean state = true;
	private Socket aSocket;
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	


	/**
	 * default constructor that takes in a string and a character
	 * @param name
	 * @param ch
	 */
	public Player(String name, char ch) {	
		this.myName = name;
		this.mark = ch;
	}
	
    public Player(Socket socket, char mark, Board board) {
    	this.aSocket = socket;
    	this.mark = mark;
    	this.board = board;
    	state = false;      
	    try {
			socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
			socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
    }

/**
 * This method makes a call to the makeMove method and then displays the board after a move has been made
 */
//	public void play() {
//			makeMove();
//			board.display();
//	}
	/**
	 * This method takes in user input in order to determine which row and column they want to insert their mark. Also checks if 
	 * the current spot on the board is occupied by another mark, as well as checks if user input is of correct format.
	 */
//	public void makeMove() {
//		Scanner scan = new Scanner(System.in);
//		String input_row;
//		String input_column;
//		int row = 0;
//		int column = 0;
//	    state = true;
//	    
//		
//		while(true) {
//		if(opponent.mark == 'O') {
//			System.out.println(currentPlayer.myName + ", What row should your next 'X' be placed in? ");
//			input_row = scan.nextLine();
//			try{
//				row = Integer.parseInt(input_row);
//			} catch(NumberFormatException e) {
//				System.out.println("Sorry, invalid input. Please try again\n");
//				continue;
//			}
//			if(row < 0 || row > 2) {
//				System.out.println("Sorry, the row you entered is not within range of the board. Please enter 0 - 2\n");
//				continue;
//			}
//			System.out.println(currentPlayer.myName + ", What column should your next 'X' be placed in? ");
//			input_column = scan.nextLine();
//			try{
//				column = Integer.parseInt(input_column);
//			} catch(NumberFormatException e) {
//				column = -1;
//				System.out.println("Sorry, invalid input. Please try again\n");
//				continue;
//			}	
//			if(column < 0 || column > 2) {
//				System.out.println("Sorry, the column you entered is not within range of the board. Please enter 0 - 2\n");
//				continue;
//			}
//		}
//		else if(opponent.mark == 'X') {
//			String inputRow;
//			String inputColumn;
//			
//			System.out.println(currentPlayer.myName + ", What row should your next 'O' be placed in? ");
//			inputRow = scan.nextLine();
//			try{
//				row = Integer.parseInt(inputRow);
//			} catch(NumberFormatException e) {
//				System.out.println("Sorry, invalid input. Please try again\n");
//				continue;
//			}
//			if(row < 0 || row > 2) {
//				System.out.println("Sorry, the row you entered is not within range of the board. Please enter 0 - 2\n");
//				continue;
//			}
//			
//			System.out.println(currentPlayer.myName + ", What column should your next 'O' be placed in? ");
//			inputColumn =scan.nextLine();
//			try{
//				column = Integer.parseInt(inputColumn);
//			} catch(NumberFormatException e) {
//				System.out.println("Sorry, invalid input. Please try again\n");
//				continue;
//			}
//			if(column < 0 || column > 2) {
//				System.out.println("Sorry, the column you entered is not within range of the board. Please enter 0 - 2\n");
//				continue;
//			}
//		}
//		if(board.getMark(row, column) == ' ') {
//			board.addMark(row, column, mark);
//			break;
//		}
//		else {
//			System.out.println("Sorry, this spot is taken. Please try again\n");
//		}
//	}
//}
	public void setCurrentPlayer(Player currentP) {
		this.currentPlayer = currentP;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setOpponent(Player enemy) {
		this.opponent = enemy;
	}
	
	public void setBoard(Board gameBoard) {
		this.board = gameBoard;
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public char getMyMark() {
		return mark;
	}

	public void setMyMark(char mark) {
		this.mark = mark;
	}
	
	public Player getOpponent() {
		return opponent;
	}

	@Override
    public void run() {       
        try {		    
		    String msg = "";
		    
		    msg = "MSG,Welcome! What is your name?";
		    socketOutput.println(msg);
		    socketOutput.flush();
		    msg = "";
            
            while(true) { // this while loop is to ensure both clients have entered their names
            	if(board.gameStarted == true) {
            		break;
            	}
            	else if(getOpponent().state == true && state == true && board.gameStarted == false) { // check to see if both players are both ready
            		msg = "MSG,GAME STARTED! " + board.currentPlayer.getMyName() + "'s turn.";
            		socketOutput.println(msg);
            		socketOutput.flush();  		
            		getOpponent().socketOutput.println(msg);
            		socketOutput.flush();
            		msg = "";
            		
            		board.gameStarted = true;
            		break;
            	}
            
					String [] response = socketInput.readLine().split(",");
					String msgType = response[0];
					String msgContent = response[1];
					
					if(msgType.equals("NAME")) { // Check to see if players entered name
						String playerName = msgContent; // Used key NAME in order to store player names
						myName = playerName;
						msg = "MSG,Waiting for Opponent...";
						state = true;
						socketOutput.println(msg);
						socketOutput.flush();
						msg = "";
					}			
            }
            
            while(true) { // this while loop implements the game itself
            	try {
            		String [] response = socketInput.readLine().split(",");
					if(board.currentPlayer == this && response[0].equals("MOVE")) { // all moves use the key MOVE
						String move = response[1];
						String [] coords = move.split("-");
						int row = Integer.parseInt(coords[0]);
						int column = Integer.parseInt(coords[1]);
						
					    if(board.validateMove(row, column) == true) {	
							board.addMark(row, column, mark);
							
							msg = "MARK," + Character.toString(getOpponent().mark);
							socketOutput.println(msg);
							getOpponent().socketOutput.println(msg);
							socketOutput.flush();
							getOpponent().socketOutput.flush();
							
							msg = "MSG," + getOpponent().myName + "'s turn";
							socketOutput.println(msg);
							getOpponent().socketOutput.println(msg);
							socketOutput.flush();
							getOpponent().socketOutput.flush();
							
							msg = "VALID," + move + " " + Character.toString(mark);
							socketOutput.println(msg);
							getOpponent().socketOutput.println(msg);
							socketOutput.flush();
							getOpponent().socketOutput.flush();
							
							msg = "";
							
					    
					    if(board.xWins() == true || board.oWins() == true) {
					    	
					    	msg = "END," + myName + " has won the match!";
					    	socketOutput.println(msg);
					    	socketOutput.flush();
					    	getOpponent().socketOutput.println(msg);
					    	getOpponent().socketOutput.flush();
					    	break;
					    }					    
					    if(board.isFull()) {
					    	msg = "END," + "The result is a draw!";
					    	socketOutput.println(msg);
					    	socketOutput.flush();
					    	getOpponent().socketOutput.println(msg);
					    	getOpponent().socketOutput.flush();
					    	break;
					    }
				}
					    board.currentPlayer = getOpponent();
					}
				} catch (IOException e) {
					socketInput.close();
                    socketOutput.close();
                    aSocket.close();
                    System.out.println("GAME FINISHED");
                    break;
				} catch(NullPointerException e) {
					socketInput.close();
                    socketOutput.close();
                    aSocket.close();
                    System.out.println("GAME FINISHED");
                    break;
				}
            }
            
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
	
	
}
