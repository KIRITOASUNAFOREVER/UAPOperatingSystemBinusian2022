package nachos.BluejackPCComponentStores;

import java.util.Vector;
import nachos.machine.Machine;
import nachos.threads.KThread;


public class Main {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Component> componentList = new Vector<>();
	
	public Main() {
		mainMenu();
	}
	private void mainMenu(){
		int pilihan;
		do{
			loadData();
			consoleScan.cetakEnter("Bluejack PC Component Store");
			consoleScan.cetakEnter("======================");
			consoleScan.cetakEnter("1. Add Component");
			consoleScan.cetakEnter("2. View Component(s)");
			consoleScan.cetakEnter("3. Deliver");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				AddComponent();
				break;
			case 2:
				ViewComponent();
				break;
			case 3:
				DeliverComponent();
				break;
			case 4:
				consoleScan.cetakEnter("Time: "+Machine.timer().getTime());
				consoleScan.cetakEnter("Thank you. Good Bye!");
				break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		componentList = new Vector<Component>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Component component= new Component(eachDatas[0], eachDatas[1], eachDatas[2]);
			componentList.add(component);
		}
	}
	
	public static int onlyLetterSpaceQuotes(String str){
	    for(int x=0; x<str.length(); x++){
	        char ch = str.charAt(x);
	        if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ' 
	            || ch == '\'' || ch == '\"'))
	            return 0;               
	    }
	    return 1;            
	}
	
	private void AddComponent() {
		String componentName,componentCategory,address;
		int cek;
		
		do {
			consoleScan.cetak("Component name[3..50 characters](Letters and spaces only): ");
			componentName = consoleScan.bacaString();
			cek = onlyLetterSpaceQuotes(componentName);
		} while (componentName.length() < 3 || componentName.length() > 50 || cek==0);
		
		do {
			consoleScan.cetak("Component category[ Storage | Display | Basic Component](case sensitive): ");
			componentCategory = consoleScan.bacaString();
		} while (!componentCategory.equals("Storage") && !componentCategory.equals("Display") && !componentCategory.equals("Basic Component"));
		
		do {
			consoleScan.cetak("Address[ ends with ' Street']: ");
			address = consoleScan.bacaString();
		} while (!address.endsWith(" Street"));
		
		String isiFile = componentName + "#" + componentCategory + "#" + address;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Component has been successfully inserted...");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void ViewComponent() {
		if(componentList.isEmpty()){
			consoleScan.cetak("No component(s)....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}

		for (Component component : componentList) {
			consoleScan.cetakEnter("Component Name: "+component.getComponentName());
			consoleScan.cetakEnter("Component Category: "+component.getComponentCategory());
			consoleScan.cetakEnter("Address: "+component.getAddress());
			new KThread(component).fork();
			consoleScan.cetakEnter("");
		}
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Component> compList) {
		String txt="";
		for (Component component : compList) {
			txt+=(component.getComponentName() + "#" + component.getComponentCategory() + "#" + component.getAddress() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void DeliverComponent() {
		if(componentList.isEmpty()){
			consoleScan.cetak("No component(s)....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		for (Component component : componentList) {
			consoleScan.cetakEnter("Component Name: "+component.getComponentName());
			consoleScan.cetakEnter("Component Category: "+component.getComponentCategory());
			consoleScan.cetakEnter("Address: "+component.getAddress());
			new KThread(component).fork();
			consoleScan.cetakEnter("");
		}
		componentList.removeAllElements();
		overWrite(componentList);
	}
}
