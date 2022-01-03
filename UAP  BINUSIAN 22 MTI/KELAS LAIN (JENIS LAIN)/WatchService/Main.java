package nachos.WatchService;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class Main {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Service> serviceList = new Vector<>();
	
	public Main() {
		mainMenu();
	}
	
	private void mainMenu(){
		int pilihan;
		do {
			loadData();
			consoleScan.cetakEnter("===Watch Service===");
			consoleScan.cetakEnter("===================");
			consoleScan.cetakEnter("1. Add Service");
			consoleScan.cetakEnter("2. View Service(s)");
			consoleScan.cetakEnter("3. Delete Service");
			consoleScan.cetakEnter("4. Clear All & Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan) {
				case 1:
					addService();
					break;
				case 2:
					viewServices();
					break;
				case 3:
					deleteService();
					break;
				case 4:
					clearAllandExit();
					break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		serviceList = new Vector<Service>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Service service = new Service(eachDatas[0], eachDatas[1], eachDatas[2]);
			serviceList.add(service);
		}
	}
	
	private boolean checkServiceName(String ServiceName) {
		if(ServiceName.length() > 3 && ServiceName.length() < 30) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean validateType(String ServiceType){
        if (ServiceType.equals("Change Battery") || ServiceType.equals("Clean & Polish") || ServiceType.equals("Repair")){
            return true;
        }
        return false;
    }
	
	private boolean validateBrand(String ServiceBrand){
        if (ServiceBrand.equalsIgnoreCase("Rolex") || ServiceBrand.equalsIgnoreCase("Guess") || ServiceBrand.equalsIgnoreCase("Seiko")){
            return true;
        }
        return false;
    }
	
	private void addService() {
		String serviceName = "", serviceType = "" , serviceBrand = "";
		
		do {
			consoleScan.cetak("Insert customer name [3 - 30 characters]: ");
			serviceName = consoleScan.bacaString();
		}while(!checkServiceName(serviceName));
		
		do {
			consoleScan.cetak("Insert service type [Change Battery | Clean & Polish | Repair] (case sensitive): ");
			serviceType = consoleScan.bacaString();
		}while(!validateType(serviceType));
		
		do {
			consoleScan.cetak("Insert watch's brand [Rolex | Guess | Seiko] (case insensitive): ");
			serviceBrand = consoleScan.bacaString();
		}while(!validateBrand(serviceBrand));
		
		String isiFile = serviceName + "#" + serviceType + "#" + serviceBrand;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Success add service!");
		consoleScan.cetakEnter("Press ENTER to continue..");
		consoleScan.bacaString();
	}
	
	private void cetakDataView(int hitung) {
		consoleScan.cetakEnter("====================================================================");
		consoleScan.cetakEnter("|No. |Customer Name                 |Service Type        |Brand    |");
		consoleScan.cetakEnter("====================================================================");
		for (Service service : serviceList) {
			int sisaNama = 30 - service.getServiceName().length();
			consoleScan.cetak("|" + (hitung++) + ".  |" + service.getServiceName());
			for(int j = 0 ; j < sisaNama ; j++) {
				consoleScan.cetak(" ");
			}
			int sisaTipe = 20 - service.getServiceType().length();
			consoleScan.cetak("|" + service.getServiceType());
			for(int k = 0 ; k < sisaTipe ; k++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetak("|" + service.getServiceBrand() + "    |");
			
			consoleScan.cetakEnter("");
			new KThread(service).fork();
		}
		consoleScan.cetakEnter("====================================================================");
	}
	
	private void viewServices() {
		if(serviceList.isEmpty()){
			consoleScan.cetak("No service left");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press ENTER to continue..");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		int hitung = 1;
		cetakDataView(hitung);
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press ENTER to continue..");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Service> serviceList) {
		String txt="";
		for (Service service : serviceList) {
			txt+=(service.getServiceName() + "#" + service.getServiceType() + "#" + service.getServiceBrand() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void cetakDataDelete(int hitung) {
		consoleScan.cetakEnter("====================================================================");
		consoleScan.cetakEnter("|No. |Customer Name                 |Service Type        |Brand    |");
		consoleScan.cetakEnter("====================================================================");
		for (Service service : serviceList) {
			int sisaNama = 30 - service.getServiceName().length();
			consoleScan.cetak("|" + (hitung++) + ".  |" + service.getServiceName());
			for(int j = 0 ; j < sisaNama ; j++) {
				consoleScan.cetak(" ");
			}
			int sisaTipe = 20 - service.getServiceType().length();
			consoleScan.cetak("|" + service.getServiceType());
			for(int k = 0 ; k < sisaTipe ; k++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetak("|" + service.getServiceBrand() + "    |");
			
			consoleScan.cetakEnter("");
		}
		consoleScan.cetakEnter("====================================================================");
	}
	
	private boolean validateDeleteIndex(int indexs) {
		if(indexs > 0 && indexs <= serviceList.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	private void deleteService() {
		if(serviceList.isEmpty()){
			consoleScan.cetak("No service left");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press ENTER to continue..");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		int hitung = 1;
		cetakDataDelete(hitung);
		consoleScan.cetakEnter("");
		
		int indeks = 0;
		do {
			consoleScan.cetak("Input service number to remove [1.." + serviceList.size() +"]: ");
			indeks = consoleScan.bacaInteger();
		}while(!validateDeleteIndex(indeks));
		
		serviceList.remove(indeks-1);
		overWrite(serviceList);
		consoleScan.cetakEnter("Success remove service!");
		consoleScan.cetakEnter("Press ENTER to continue..");
		consoleScan.bacaString();
	}
	
	private void clearAllandExit() {
		serviceList.removeAllElements();
		overWrite(serviceList);
		consoleScan.cetakEnter("The program ended in "+Machine.timer().getTime() / 10000000 +" second(s).");
	}
}
