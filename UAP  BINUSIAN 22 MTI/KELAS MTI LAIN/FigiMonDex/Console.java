package nachos.proj1;

import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

public class Console {
	
	private SerialConsole serCon = Machine.console(); 
	private Semaphore semp = new Semaphore(0); 
	private char tempChar; 
	
	public Console() {
		Runnable receive = new Runnable() {
			
			@Override
			public void run() {
				tempChar = (char) serCon.readByte(); 
				semp.V();
			}
		};
		
		Runnable send = new Runnable() {
			
			@Override
			public void run() {
				semp.V();
			}
		};
		
		serCon.setInterruptHandlers(receive, send);
	}
	
	public String scan() {
		String res = "";
		
		do {
			semp.P();
			if(tempChar != '\n') {
				res += tempChar;
			}
		}while(tempChar != '\n'); 
		
		return res; 
	}
	
	public int scanInt() {
		int res = -1; 
		
		try {
			res = Integer.parseInt(scan());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			println("Input must be an integer");
		} 
		
		return res; 
	}
	
	public void print(String str) {
		for(int i = 0; i < str.length(); i++) {
			serCon.writeByte(str.charAt(i));
			semp.P();
		}
	}
	
	public void println(String str) {
		print(str + "\n"); 
	}
}
