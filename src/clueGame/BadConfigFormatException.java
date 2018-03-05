package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Improper format");
	}
	
	public BadConfigFormatException(String s){
		super(s);
	}

}
