package nachos.WatchService;

import nachos.machine.FileSystem;
import nachos.machine.Machine;
import nachos.machine.OpenFile;

public class MyFileSystem {
	private FileSystem file = Machine.stubFileSystem();
	
	public MyFileSystem() {
		// TODO Auto-generated constructor stub
	}

	public void write(String txt) {
		String dataTxt="";
		try {
			dataTxt=scan();
		} catch (Exception e) {
			// TODO: handle exception
			dataTxt="";
		}
		
		if(dataTxt!=null)
			dataTxt=dataTxt+"\n"+txt+"\n";
		else
			dataTxt=txt+"\n";
		OpenFile fileData = file.open("Service.txt", true);
		byte[] bytes= dataTxt.getBytes();
		fileData.write(bytes, 0, bytes.length);	
		fileData.close();
	}
	
	public String scan() {
		OpenFile fileData = file.open("Service.txt", false);
		if(fileData == null){
			return null;
		}
		byte[] buffer = new byte[999]; 
		fileData.read(buffer, 0, buffer.length);
		String hasil = new String(buffer);
		fileData.close();
		
		return hasil.trim();
	}
	public void overwrite(String dataTxt) {
		OpenFile fileData = file.open("Service.txt", true);
		byte[] bytes= dataTxt.getBytes();
		fileData.write(bytes, 0, bytes.length);	
		fileData.close();
	}
}
