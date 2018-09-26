//INOG

import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//author @abba5aghaei

public class Main {
	
	public static String[] setting = { "255", "255", "255", "0", "0", "0", "Tohama", String.valueOf(Font.PLAIN), "11", "70", "70", "100", "128", "128", "128", "0", "0", "0" };
	public static Scanner input;
	public static BufferedWriter output;
	public static Font appFont = new Font("Tahoma", Font.PLAIN, 11);
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					
					loadSetting();
					
					MegaFrame frameMega = new MegaFrame(setting);
					
					frameMega.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}}});
		}
	
	public static void loadSetting() {
		
		File info = new File("Data/setting.set");
		
		if(!info.exists()) {
			
			try {
				output = new BufferedWriter(new FileWriter(info));
				
				for(int i=0 ; i<18 ; i++)
					output.write(setting[i]+"\n");
				
				output.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		try {
			input = new Scanner(new FileInputStream(info));
			
			for(int i=0 ; i<18 ; i++)
				setting[i] = input.nextLine();
			
			input.close();
		}
		catch (Exception e) {}
	}
}
