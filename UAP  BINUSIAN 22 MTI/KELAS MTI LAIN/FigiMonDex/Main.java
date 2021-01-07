package nachos.proj1;

import java.util.Scanner;
import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class Main {

	private Console console = new Console();
	private Vector<Figimon> figimonList= new Vector<Figimon>();
	private MyFileSystem fileSystem = new MyFileSystem();
	public Main() {
		// TODO Auto-generated constructor stub
		int x;
		do {
			loadData();
			Menu();
			x=console.scanInt();
			switch (x) {
			case 1:
				Insert();
				break;

			case 2:
				ViewFigimon();
				break;
				
			case 3:
				DeleteFigimon();
				break;
			}
		} while (x!=4);
		console.println("Time: "+Machine.timer().getTime());
		console.println("Thank you for using the Figimon Dex!");
	}
	
	public void loadData() {
		figimonList = new Vector<Figimon>();
		try {

			String data=fileSystem.scan();
			String[] lineDatas= data.split("\n");
			for (String lineData : lineDatas) {
				if(lineData.isEmpty())
					continue;
				String[] eachDatas=lineData.split("#");
				Figimon figimon= new Figimon(eachDatas[0],eachDatas[1]);
				figimonList.add(figimon);	

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	public void DeleteFigimon() {

		if(figimonList.isEmpty()) {
			console.println("There is no data avaible!");
		}
		else {
			for (int i=0;i<figimonList.size();i++) {
				console.println("Dex No."+(i+1));
				console.println("===============");
				new KThread(figimonList.get(i)).fork();
				console.println("");
			}
			int figimonNumber=0;
			do {
				console.print("Input index that want to be deleted [1 - "+figimonList.size()+"]:");
				figimonNumber=console.scanInt();
			} while (figimonNumber<1|| figimonNumber>figimonList.size() );
			figimonList.remove(figimonNumber-1);
			overWrite(figimonList);
		}
		console.println("Please Enter to continue...");
		console.scan();
	}
	
	public void overWrite(Vector<Figimon> figList) {
		String txt="";
		for (Figimon figimon : figList) {
			txt+=(figimon.getName()+"#"+figimon.getType()+"\n");
			
		}

		fileSystem.overwrite(txt);
		
	}
	
	public void ViewFigimon() {
		if(figimonList.isEmpty()) {
			console.println("There is no data avaible!");
		}
		else {
			for (int i=0;i<figimonList.size();i++) {
				console.println("Dex No."+(i+1));
				console.println("===============");
				new KThread(figimonList.get(i)).fork();
				console.println("");
			}
		}

		console.println("Please Enter to continue...");
		console.scan();
	}

	public void Insert() {
		String name;
		do {
			console.print("Input Figimon Name [must end with mon] : ");
			name=console.scan();
		} while (!name.endsWith("mon"));
		
		String type;
		do {
			console.print("Input Figimon type [Vaccine | Data | Virus]:");
			type=console.scan();
		} while (!type.equals("Vaccine")&&!type.equals("Data")&&!type.equals("Virus"));
		fileSystem.write(name+"#"+type);
		console.println("");
	}
	
	public void Menu() {
		console.println("Figimon Dex");
		console.println("=============================");
		console.println("1. Insert Data");
		console.println("2. View Data");
		console.println("3. Delete Data");
		console.println("4. Exit");;
		console.print(">> ");
	}
}
