package server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.model.Board;

import java.io.ObjectInputStream; 

public class TicTacToeServer {
	
	private ServerSocket serverSocket;
	private ExecutorService myGame;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket aSocket;
	private ArrayList<Socket> boards;
	
	
	public TicTacToeServer() {
		try {
			serverSocket = new ServerSocket(9090); 
			myGame = Executors.newFixedThreadPool(2);
		} catch (IOException e) {
			System.out.println("Create new socket error");
		}
		System.out.println("Server is now running");
	}
	
	public void communicate() {
		System.out.println("Waiting for players to connect...");
		try {
			boards = new ArrayList<>(2);
			while(true) {
				aSocket = serverSocket.accept();
				boards.add(aSocket);
				if(boards.size() == 1) 
					System.out.println("Player has connected...(1/2)");
				else if(boards.size() == 2)
					System.out.println("Player has connected...(2/2)");
				
				if(boards.size()==2){
                    Board board = new Board(boards.get(0), boards.get(1));
                    myGame.execute(board);
				}
			}
		} catch (IOException e) {
				System.out.println(e.toString());
		}
		myGame.shutdown();
		while (!myGame.isTerminated()) { // this loop waits until all threads have stopped
			
		}
	}
	
	
	public static void main(String args[]) {
		TicTacToeServer myServer = new TicTacToeServer();
		myServer.communicate();
	}
	 
}
