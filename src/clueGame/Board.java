/**
 * Board class stores every board cell in an array and allows for the functionality of the game board
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

public class Board{

	private int numRows;
	private int numColumns;
	private int currentPlayersTurn;
	private static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private Solution trueSolution;
	private ArrayList<Player> players;
	private ArrayList<ComputerPlayer> computerPlayers;
	private ArrayList<Card> cards;
	private ArrayList<String> weapons;
	private ArrayList<String> rooms;
	private ArrayList<Card> theEnvelope;
	static GameDisplay frame;
	private boolean playerHasMoved = true;
	private static int dieRoll = 0;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * Calls all the functions to set all the instance variables
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void initialize() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		legend = new HashMap<Character, String>();
		players = new ArrayList<Player>();
		computerPlayers = new ArrayList<ComputerPlayer>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		theEnvelope = new ArrayList<Card>();
		currentPlayersTurn = 0;
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
			makeDeck();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException f){
			f.printStackTrace();
		}
		calcAdjacencies();
	}

	/**
	 * Reads the legend file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		try {
			legend.clear();
		}
		catch (NullPointerException p) {
			legend = new HashMap<Character, String>();
		}
		rooms = new ArrayList<String>();
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);

		while (in.hasNext()) {
			String temp = in.nextLine();
			Character tempchar = temp.charAt(0); //Gets char for room initial
			String value = temp.substring(3, temp.indexOf(',', 3)); //Gets string for room name
			String type = temp.substring(temp.indexOf(',', 3) + 2); //Gets room type (card or other)
			if(type.equalsIgnoreCase("Card")){
				rooms.add(value);
			}
			if (!((type.equalsIgnoreCase("Card")) || (type.equalsIgnoreCase("Other")))) {
				throw new BadConfigFormatException("Bad legend typing");
			}
			legend.put(tempchar, value); //Maps the room name to the room initial
		}
	}


	/**
	 * Reads the excel file with our board
	 * @throws FileNotFoundException 
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		try {
			board[0][0] = null;
		}
		catch (NullPointerException p) {
			board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		}

		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int previousColumnNum = 0;

		while (in.hasNext()) {
			String tempLine = in.nextLine();
			String[] tempLineArr = tempLine.split(","); //Creates array of strings, breaking each string at a ','
			int column = 0;

			for (String s: tempLineArr) {
				if (!legend.containsKey(s.charAt(0))) {
					throw new BadConfigFormatException("Room intial not in legend.");
				}

				BoardCell tempBoardCell = new BoardCell();
				tempBoardCell.setInitial(s.charAt(0));
				tempBoardCell.setColumn(column);
				tempBoardCell.setRow(row);

				if(s.length() == 2){
					switch(s.charAt(1)){
					case 'U': 
						tempBoardCell.setDirection(DoorDirection.UP);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'D': 
						tempBoardCell.setDirection(DoorDirection.DOWN);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'L': 
						tempBoardCell.setDirection(DoorDirection.LEFT);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'R': 
						tempBoardCell.setDirection(DoorDirection.RIGHT);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'N':
						tempBoardCell.setDirection(DoorDirection.NONE);
						tempBoardCell.setCellType(CellType.DOORWAY);
						tempBoardCell.setRoomName(legend.get(s.charAt(0)));
						break;
					}
				}
				else {
					if (s.charAt(0) == 'W') {
						tempBoardCell.setCellType(CellType.WALKWAY);
					}
					else{
						tempBoardCell.setCellType(CellType.ROOM);
					}
				}

				board[row][column] = tempBoardCell;
				column++;
				numColumns = column;
			}

			if (row == 0) {
				previousColumnNum = numColumns;
			}
			else {
				if (previousColumnNum != numColumns) {
					throw new BadConfigFormatException("Bad number of columns.");
				}
			}
			row++;
			numRows = row;
		}	
	}

	/**
	 * Loads configuration file for the players and stores them in the arrayList<Players>
	 */
	public void loadPlayerConfig() throws FileNotFoundException{
		try {
			players.clear();
		}
		catch (NullPointerException p) {
			players = new ArrayList<Player>();
		}

		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);

		while (in.hasNext()) {
			String temp = in.nextLine();
			//Parses the string to get each field
			int firstComma = temp.indexOf(',');
			int secondComma = temp.indexOf(',', firstComma+1);
			int thirdComma = temp.indexOf(',', secondComma+1);
			int fourthComma = temp.indexOf(',', thirdComma+1);
			String name = temp.substring(0, firstComma);
			String color = temp.substring((firstComma+2), secondComma);
			Color playerColor = convertColor(color);
			String type = temp.substring((secondComma+2), thirdComma);
			String row = temp.substring((thirdComma+2), fourthComma);
			String col = temp.substring(fourthComma+2);
			if (type.equals("H")) {
				HumanPlayer nextHumanPlayer = new HumanPlayer(name, Integer.parseInt(row), Integer.parseInt(col), playerColor);
				players.add(0, nextHumanPlayer);
			}
			else {
				ComputerPlayer nextComputerPlayer = new ComputerPlayer(name, Integer.parseInt(row), Integer.parseInt(col), playerColor);
				players.add(nextComputerPlayer);
				computerPlayers.add(nextComputerPlayer);
			}

		}
		for (ComputerPlayer cpu: computerPlayers) {
			cpu.setLegend(legend);
		}
	}

	/**
	 * Converts string to color
	 * @param strColor
	 * @return Color
	 */
	public Color convertColor(String strColor){
		Color color;
		try{
			Field field = Class.forName("java.awt.Color").getField(strColor);
			color = (Color)field.get(null);
		} catch (Exception e){
			color = null;
		}
		return color;
	}

	/**
	 * Loads configuration file for the weapons and stores them in the arrayList<String>
	 */
	public void loadWeaponConfig() throws FileNotFoundException{
		try {
			weapons.clear();
		}
		catch (NullPointerException p) {
			weapons = new ArrayList<String>();
		}

		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);

		while (in.hasNext()) {
			String temp = in.nextLine();
			weapons.add(temp);
		}
	}

	/**
	 * Creates the deck of cards with 6 weapons, 6 people and 9 rooms
	 */
	public void makeDeck(){
		cards = new ArrayList<Card>();
		for(int i = 0; i < players.size(); i++){
			Card temp = new Card(players.get(i).getPlayerName(), CardType.PLAYER);
			cards.add(temp);
		}

		for(int i = 0; i < weapons.size(); i++){
			Card temp = new Card(weapons.get(i), CardType.WEAPON);
			cards.add(temp);
		}

		for(int i = 0; i < rooms.size(); i++){
			Card temp = new Card(rooms.get(i), CardType.ROOM);
			cards.add(temp);
		}
		
		for (ComputerPlayer cpu: computerPlayers) {
			cpu.setAllCards(cards);
		}
	}

	/**
	 * Generates a random solution with one player, one weapon and one room, then removes those cards from the deck
	 */
	public void makeSolution(int player, int weapon, int room){
		trueSolution = new Solution(cards.get(player).getName(), cards.get(weapon).getName(), cards.get(room).getName());
		theEnvelope.add(cards.get(player));
		theEnvelope.add(cards.get(weapon));
		theEnvelope.add(cards.get(room));
	}

	/**
	 * Shuffles the cards then deals the full deck to the players
	 */
	public void deal(){
		int randRoom = (int)(Math.random()*(rooms.size())) + (weapons.size() + players.size());
		int randWeapon = (int)(Math.random()*(weapons.size())) + players.size();
		int randPlayer = (int)(Math.random()*players.size());
		makeSolution(randPlayer, randWeapon, randRoom);
		Collections.shuffle(cards);
		
		int i = 0;
		for(Card c : cards){
			if (!theEnvelope.contains(c)) {
				if (i == players.size()){
					i = 0;
				}
				players.get(i).getMyCards().add(c);
				players.get(i).getSeenCards().add(c);
				i++;
			}
		}
	}

	/**
	 * Calculates adjacencies of each walkway
	 */
	public void calcAdjacencies() {

		for (int row = 0; row <= numRows - 1; row++) {
			for (int column = 0; column <= numColumns - 1; column++) {
				BoardCell c = board[row][column];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if (c.isWalkway()) {
					//checks if cell above current exists, and if it does and isWalkway or door with direction DOWN, adds to temp
					if ((row - 1) >= 0 && (board[row-1][column].isWalkway() || 
							((board[row-1][column].isDoorway()) && (board[row-1][column].getDoorDirection() == DoorDirection.DOWN)))) {
						temp.add(board[row-1][column]);
					}
					//checks if cell below current exists, and if it does and isWalkway or door with direction UP, adds to temp
					if ((row + 1) < numRows && (board[row+1][column].isWalkway() || 
							((board[row+1][column].isDoorway()) && (board[row+1][column].getDoorDirection() == DoorDirection.UP)))) {
						temp.add(board[row+1][column]);
					}
					//checks if cell to the left of current exists, and if it does and isWalkway or door with direction RIGHT, adds to temp
					if ((column - 1) >= 0 && (board[row][column-1].isWalkway() || 
							((board[row][column-1].isDoorway()) && (board[row][column-1].getDoorDirection() == DoorDirection.RIGHT)))) {
						temp.add(board[row][column - 1]);
					}
					//checks if cell to the right of current exists, and if it does and isWalkway or door with direction LEFT, adds to temp
					if ((column + 1) < numColumns && (board[row][column+1].isWalkway() || 
							((board[row][column+1].isDoorway()) && (board[row][column+1].getDoorDirection() == DoorDirection.LEFT)))) {
						temp.add(board[row][column + 1]);
					}
					adjMatrix.put(c, temp);
				}
				else if (c.isDoorway()) { //put into a switch statement
					if (c.getDoorDirection() == DoorDirection.UP) {
						temp.add(board[row-1][column]);
					}
					if (c.getDoorDirection() == DoorDirection.DOWN) {
						temp.add(board[row+1][column]);
					}
					if (c.getDoorDirection() == DoorDirection.LEFT) {
						temp.add(board[row][column - 1]);
					}
					if (c.getDoorDirection() == DoorDirection.RIGHT) {
						temp.add(board[row][column + 1]);
					}
					adjMatrix.put(c, temp);
				}
				else {
					adjMatrix.put(c, temp);
				}
			}
		}
	}

	/**
	 * Driver for findAllTargets, which will find all targets for given cell
	 * @param cell starting cell
	 * @param pathLength number of steps to take
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(cell);
		findAllTargets(cell, pathLength);
	}

	public void calcTargets(int row, int column, int pathLength) {
		calcTargets(board[row][column], pathLength);
	}

	/**
	 * Recursive function that will find all the targets for a given cell
	 * @param startCell
	 * @param pathLength
	 */
	public void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell: adjMatrix.get(startCell)) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathLength == 1 || adjCell.isDoorway()) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	/**
	 * given a suggestion, returns a card that disproves the suggestion
	 * @return
	 */
	public Card handleSuggestion(Solution s, Player accuser, ArrayList<Player> people) {
		boolean disproved = false;
		int loc = 1+people.indexOf(accuser);
		if(loc == people.size()){
			loc = 0;
		}
		int count = 0;
		while(!disproved && count < people.size() - 1){
			Card c = people.get(loc).disproveSuggestion(s);
			if(c != null){
				disproved = true;
				return c;
			}
			count++;
			loc++;
			if(loc == people.size()){
				loc = 0;
			}
		}

		return null;
	}

	/**
	 * Updates control panel with die roll and player suggestion (if applicable)
	 * Checks for a winner, moves computer players, calls methods to check accusations or suggestions made
	 */
	public void makeNextMove() {
		if (playerHasMoved) {
			dieRoll = (int)((Math.random() * 6) + 1);
		}
		frame.getControlPanel().updateDisplay(dieRoll, players.get(currentPlayersTurn).getPlayerName());
		calcTargets(players.get(currentPlayersTurn).getRow(), players.get(currentPlayersTurn).getColumn(), dieRoll);
		if (currentPlayersTurn == 0 && playerHasMoved) {
			playerHasMoved = false;
		}
		if (!players.get(currentPlayersTurn).isHuman() && ((ComputerPlayer) players.get(currentPlayersTurn)).getGuessIsCorrect()) {
			Solution computerSolution = ((ComputerPlayer) players.get(currentPlayersTurn)).makeAccusation();
			if (checkAccusation(computerSolution)) {
				setWinner(players.get(currentPlayersTurn));
			}
			else {
				displayWrongAccusation(computerSolution, players.get(currentPlayersTurn));
				((ComputerPlayer) players.get(currentPlayersTurn)).setGuessIsCorrect(false);
			}
		}
		
		players.get(currentPlayersTurn).makeMove(targets);
		if (!players.get(currentPlayersTurn).isHuman()) {
			if (board[players.get(currentPlayersTurn).getRow()][players.get(currentPlayersTurn).getColumn()].isDoorway()) {
				Solution computerGuess = ((ComputerPlayer) players.get(currentPlayersTurn)).createSuggestion(true);
				frame.getControlPanel().setGuess(computerGuess);
				computerGuess.print();
				for (Player p: players) {
					if (p.getPlayerName().equals(computerGuess.person)) {
						p.setRow(players.get(currentPlayersTurn).getRow());
						p.setColumn(players.get(currentPlayersTurn).getColumn());
					}
				}
				
				Card guessDisproval = handleSuggestion(computerGuess, players.get(currentPlayersTurn), players);
				if (guessDisproval == null) {
					((ComputerPlayer) players.get(currentPlayersTurn)).setGuessIsCorrect(true);
					frame.getControlPanel().setResponse("No new clue.");
				}
				else {
					for (Player p: players) {
						if (!p.getSeenCards().contains(guessDisproval)) {
							p.getSeenCards().add(guessDisproval);
						}
					}
					frame.getControlPanel().setResponse(guessDisproval.getName());
				}
			}
		}
		if (playerHasMoved) {
			currentPlayersTurn++;
			targets.clear();
		}
		if (currentPlayersTurn == players.size()) {
			currentPlayersTurn = 0;
		}
		frame.repaint();
	}
	
	/**
	 * Displays message with the game winner
	 * @param p the winning player
	 */
	public void setWinner(Player p) {
		JOptionPane.showMessageDialog(frame, "Congrats! You won! The solution is " + trueSolution.person + " in " + trueSolution.room + " with " + trueSolution.weapon);
	}
	
	/**
	 * Displays message when a computer players makes an incorrect accusation
	 * @param solution
	 * @param accuser
	 */
	public void displayWrongAccusation(Solution solution, Player accuser) {
		JOptionPane.showMessageDialog(frame, accuser.getPlayerName() + " accused incorrectly. They guessed it was " + solution.person + " in " + solution.room + " with " + solution.weapon);
	}
	
	/**
	 * If the human player moves into a room, will display an option for the player to make a suggestion and receive the card feedback
	 */
	public void doHumanTurn(){
		targets.clear();
		BoardCell current = getPlayersCell(players.get(currentPlayersTurn));
		String roomName = legend.get(current.getInitial());
		if(current.isDoorway() || current.isRoom()){
			JPanel guessPanel = new JPanel();
			guessPanel.setLayout(new GridLayout(4, 2));
			JTextField room = new JTextField("Your Room");
			room.setEditable(false);
			guessPanel.add(room);
			JTextField currentRoom = new JTextField(roomName);
			currentRoom.setEditable(false);
			guessPanel.add(currentRoom);
			JTextField person = new JTextField("Person");
			person.setEditable(false);
			guessPanel.add(person);
			String[] people = {"Mark Baldwin", "Cyndi Raider", "Jeffrey Paone", "Christoper Painter-Wakefield", "Tracy Camp", "Poor Student"};
			JComboBox<String> peopleCombo = new JComboBox<String>(people);
			guessPanel.add(peopleCombo);
			JTextField weapon = new JTextField("Weapon");
			weapon.setEditable(false);
			guessPanel.add(weapon);
			String[] weapons = {"C++", "Java", "Python", "MIPS", "HTML", "PHP"};
			JComboBox<String> weaponsCombo = new JComboBox<String>(weapons);
			guessPanel.add(weaponsCombo);
			guessPanel.setVisible(true);
			String[] buttons = {"Submit", "Cancel"};
			JOptionPane.showOptionDialog(frame, guessPanel, "Make a Guess", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			String personGuess = (String) peopleCombo.getSelectedItem();
			String weaponsGuess = (String) weaponsCombo.getSelectedItem();
			Solution playerGuess = new Solution();
			playerGuess.setRoom(roomName);
			playerGuess.setPerson(personGuess);
			playerGuess.setWeapon(weaponsGuess);
			Card disprove = this.handleSuggestion(playerGuess, getHumanPlayer(), players);
			if(disprove == null){
				frame.getControlPanel().setResponse("No new disprove");
			}
			else{
				frame.getControlPanel().setResponse(disprove.getName());
			}
			frame.getControlPanel().setGuess(playerGuess);
		}
	}
	
	/**
	 * returns the players current board cell
	 * @param player
	 * @return
	 */
	public BoardCell getPlayersCell(Player player){
		return board[player.getRow()][player.getColumn()];
	}
	
	/**
	 * Displays a box for the human player to create an accusation.
	 * Recieves the data from the player's accusation and handles it and displays appropriate response whether they are right or wrong
	 */
	public void handleAccusation(){
		if(currentPlayersTurn == 0 && !playerHasMoved){
			JPanel guessPanel = new JPanel();
			guessPanel.setLayout(new GridLayout(4, 2));
			JTextField room = new JTextField("Room");
			room.setEditable(false);
			guessPanel.add(room);
			String[] rooms = {"Darwin", "Mac", "FreeBSD", "Linux", "Windows", "NetBSD", "OpenBSD", "QNX", "Solaris"};
			JComboBox<String> roomCombo = new JComboBox<String>(rooms);
			guessPanel.add(roomCombo);
			JTextField person = new JTextField("Person");
			person.setEditable(false);
			guessPanel.add(person);
			String[] people = {"Mark Baldwin", "Cyndi Raider", "Jeffrey Paone", "Christoper Painter-Wakefield", "Tracy Camp", "Poor Student"};
			JComboBox<String> peopleCombo = new JComboBox<String>(people);
			guessPanel.add(peopleCombo);
			JTextField weapon = new JTextField("Weapon");
			weapon.setEditable(false);
			guessPanel.add(weapon);
			String[] weapons = {"C++", "Java", "Python", "MIPS", "HTML", "PHP"};
			JComboBox<String> weaponsCombo = new JComboBox<String>(weapons);
			guessPanel.add(weaponsCombo);
			guessPanel.setVisible(true);
			String[] buttons = {"Submit", "Cancel"};
			int result = JOptionPane.showOptionDialog(frame, guessPanel, "Make an Accusation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if(result == 0){
				String personGuess = (String) peopleCombo.getSelectedItem();
				String weaponsGuess = (String) weaponsCombo.getSelectedItem();
				String roomGuess = (String) roomCombo.getSelectedItem();
				Solution playerGuess = new Solution();
				playerGuess.setRoom(roomGuess);
				playerGuess.setPerson(personGuess);
				playerGuess.setWeapon(weaponsGuess);
				Card disprove = this.handleSuggestion(playerGuess, getHumanPlayer(), players);
				if(disprove == null){
					setWinner(getHumanPlayer());
				}
				else{
					JOptionPane.showMessageDialog(frame, "Your accusation is not correct.");
				}
			}

		}
		else{
			JOptionPane.showMessageDialog(frame, "You cannot make an accusation at this time.");
		}
	}

	/**
	 * returns true if it is the humans players turn
	 * @return
	 */
	public boolean isHumanPlayersTurn() {
		return players.get(currentPlayersTurn).isHuman();
	}

	/**
	 * checks if an accusation is correct
	 * @param accusation
	 * @return
	 */
	public boolean checkAccusation(Solution accusation) {
		return trueSolution.equals(accusation);
	}
	
	public static void main(String[] arg0) {
		frame = new GameDisplay();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "You are Poor Student (red), press Next Player to begin", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	

	/*
	 * The following are all getters or setters
	 */
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	public void setConfigFiles(String boardLayout, String legend) {
		boardConfigFile = boardLayout;
		roomConfigFile = legend;
	}
	public void setConfigFiles(String boardLayout, String legend, String player, String weapons){
		boardConfigFile = boardLayout;
		roomConfigFile = legend;
		playerConfigFile = player;
		weaponConfigFile = weapons;
	}
	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(board[row][column]);
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public ArrayList<Card> getCards() {
		return cards;

	}
	public ArrayList<String> getWeapons(){
		return weapons;
	}
	public Player getHumanPlayer() {
		for (Player p: players) {
			if (p.getPlayerName().equals("Poor Student")) {
				return p;
			}
		}
		return null;
	}
	public Solution getTrueSolution(){
		return trueSolution;
	}
	public void setTrueSolution(Solution solution) {
		trueSolution = solution;
	}
	public boolean getPlayerMoveStatus() {
		return playerHasMoved;
	}
	public void setPlayerHasMoved(boolean b) {
		playerHasMoved = b;
	}
	public void repaint() {
		frame.repaint();
	}
	public void incrementPlayerTurn() {
		currentPlayersTurn++;
	}
}
