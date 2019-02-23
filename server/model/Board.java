package server.model;
import java.net.Socket;

import server.Controller.Player;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

/**
 * 
 * @author Ian
 *
 */
public class Board implements Constants, Runnable {
	private char theBoard[][];
	private int markCount;
	private Socket xPlayer;
	private Socket oPlayer;
	public Player currentPlayer;
	public boolean gameStarted;

	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	public Board(Socket socket, Socket socket2) {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
		
		this.xPlayer = socket;
		this.oPlayer = socket2;
		gameStarted = false;
	}
/**
 * This is used to get the mark that is currently occupying this specific cell
 * @param row
 * @param col
 * @return
 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
/**
 * Method to check if the Tic-Tac-Toe board has run out of spaces
 * @return
 */
	public boolean isFull() {
		return markCount == 9;
	}
/**
 * Uses the method checkWinner in order to determine if the 'X' player won the game
 * @return
 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}
/**
 * Uses the method checkWinner in order to determine if the 'O' player won the game
 * @return
 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}
/**
 * This is a method that has three arguments: row, column, and mark. The method itself is used to determine the position that the player has decided to put 
 * their 'X' or 'O'
 * @param row
 * @param col
 * @param mark
 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}
/**
 * this clears the board of all current marks that are present and replaces it with a blank space 
 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
/**
 * This is used to determine the winner. It does this by checking if "mark" has occurred 3-in-a-row anywhere on the board. This could be horizontal,
 * vertical, or diagonal.
 * @param mark
 * @return
 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
/**
 * displays the headers of the column, col 0, col 1, col 2
 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}
/**
 * This generates the horizontal borders between each cell
 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}
/**
 * Creates the vertical boundaries between each cell. Also creates white space so each cell has adequate 
 * spacing between them.
 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
	
	public synchronized boolean validateMove(int row,int col){
        if(theBoard[row][col]!=SPACE_CHAR){
            System.out.println("PLEASE CHOOSE ANOTHER SPOT \n");
            return false;
        }
        else return true;
        
    }
	@Override
	public void run() {
		Player x = new Player(xPlayer,LETTER_X,this);
        Player o = new Player(oPlayer,LETTER_O,this);
        x.setOpponent(o);
        o.setOpponent(x);
        currentPlayer = x;
        new Thread(x).start();
        new Thread(o).start();
	
    }
}
