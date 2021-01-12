package nachos.ZOSCE;
import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

public class MyConsole {
	private SerialConsole sc=Machine.console();
	char c;
	private Semaphore semaphore = new Semaphore(0);
	public MyConsole(){
		Runnable receiveInterruptHandler = new Runnable() {
			
			@Override
			public void run() {
				c=(char) sc.readByte();
				semaphore.V();
			}
		};
		Runnable sendInterruptHandler = new Runnable() {
			
			@Override
			public void run() {
				semaphore.V();
			}
		};
		sc.setInterruptHandlers(receiveInterruptHandler, sendInterruptHandler);
		
	}
	public String bacaString() {
		String str= "";
		do {
			semaphore.P();
			if(c=='\n') {
				break;
			}
			else
			{
				str+=c;
			}
		}while(true);
		return str;
	}
	public int bacaInteger() {
		int value=-1;
		try {
			value=Integer.parseInt(bacaString());	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}
	public void cetak(String str)
	{
		for(int i=0;i<str.length();i++)
		{
			sc.writeByte(str.charAt(i));
			semaphore.P();
		}

	}
	public void cetakEnter(String str)
	{
		cetak(str);
		System.out.print('\n');
	}
}
