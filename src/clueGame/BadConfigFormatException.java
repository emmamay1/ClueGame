/**
 * BadConfigFormatException is thrown when the confiuration files have formats that don't work with our file reading code
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Improper format");
	}
	
	public BadConfigFormatException(String errorMessage){
		super(errorMessage);
	}

}
