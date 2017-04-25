package filehandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileHandler Class Save and load the player pot 
 * @author Amin Harirchian
 *
 */

public class FileHandler {
	private String filename = "files/pot.txt";
	double pot;
	

	/**
	 * Construction for ArrayProducer that we define how long waiting and how
	 * many times list of icons is going to show.
	 * 
	 * @param pot the player pot
	 */
	public FileHandler(double pot) {
		this.pot=pot;
	}


	/**
	 * Method is going to save the player pot
	 */
	public void savePot() throws IOException {
		DataOutputStream dos = null;

		try {
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			dos.writeDouble(pot);
		//	System.out.println(pot);
			dos.flush();
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * Method is going to load the player pot that saved the last time.
	 */
	public void loadPot() throws IOException {
		try( DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename))) ) {
    		double pot = dis.readDouble();
    		System.out.println(pot);
	}}
	
	/**
	 * Main metod for testing the Handler class
	 */
	public static void main(String args[]) throws IOException {
        FileHandler dt = new FileHandler(200);
        dt.savePot();
        System.out.println("saved");
        dt.loadPot();
        System.out.println("load");
    }
}
