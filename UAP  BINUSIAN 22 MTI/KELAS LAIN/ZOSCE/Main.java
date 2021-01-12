package nachos.ZOSCE;

import java.util.Vector;
import nachos.machine.Machine;
import nachos.threads.KThread;


public class Main {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Tweet> tweetList = new Vector<>();
	
	public Main() {
		mainMenu();
	}
	private void mainMenu(){
		int pilihan;
		do{
			loadData();
			consoleScan.cetakEnter("Welcome to ZOSCE. What would you like to do?");
			consoleScan.cetakEnter("============================================");
			consoleScan.cetakEnter("1. Check Timeline");
			consoleScan.cetakEnter("2. Create Tweet");
			consoleScan.cetakEnter("3. Delete Tweet");
			consoleScan.cetakEnter("4. Logout");
			consoleScan.cetak("Choose : ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				checkTimeline();
				break;
			case 2:
				createTweet();
				break;
			case 3:
				deleteTweet();
				break;
			case 4:
				long timerSeconds = Machine.timer().getTime()/ 10000000;
				long timerMinutes = timerSeconds /60;
				consoleScan.cetakEnter("You have been logged in for : "+timerMinutes +" minute(s)");
				consoleScan.cetakEnter("Goodbye!");
				break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		tweetList = new Vector<Tweet>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Tweet tweet = new Tweet(eachDatas[0], eachDatas[1]);
			tweetList.add(tweet);
		}
	}
	
	private void checkTimeline() {
		if(tweetList.isEmpty()){
			consoleScan.cetak("Sadly no one has tweeted yet, including you.. Follow other users and create tweets to fill the timeline!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		int jumlah = tweetList.size();
		int tanda = 0;
		int ulang = 1;
		int test = 0;
		int hitung = 3;
		int sisa = tweetList.size();
		String retry;
		if(jumlah > 3){
			tanda = 1;
		}
		if(tanda==0){
			for (Tweet tweet : tweetList) {
				consoleScan.cetakEnter("==================================");
				consoleScan.cetakEnter("User : "+tweet.getTweetCreator());
				consoleScan.cetakEnter("----------------------------------");
				consoleScan.cetakEnter(tweet.getTweetContent());
				consoleScan.cetakEnter("==================================");
				new KThread(tweet).fork();
				consoleScan.cetakEnter("");
			}
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}else{
			while(ulang==1){
				if(test==0){
					for (int i = 0; i < 3; i++) {
						consoleScan.cetakEnter("==================================");
						consoleScan.cetakEnter("User : "+tweetList.get(i).getTweetCreator());
						consoleScan.cetakEnter("----------------------------------");
						consoleScan.cetakEnter(tweetList.get(i).getTweetContent());
						consoleScan.cetakEnter("==================================");
						new KThread(tweetList.get(i)).fork();
						consoleScan.cetakEnter("");
						test++;
					}
				}else{
					for (int i = tweetList.size() - sisa; hitung !=0 ; i++) {
						consoleScan.cetakEnter("==================================");
						consoleScan.cetakEnter("User : "+tweetList.get(i).getTweetCreator());
						consoleScan.cetakEnter("----------------------------------");
						consoleScan.cetakEnter(tweetList.get(i).getTweetContent());
						consoleScan.cetakEnter("==================================");
						new KThread(tweetList.get(i)).fork();
						consoleScan.cetakEnter("");
						hitung--;
					}
				}

				do {
					consoleScan.cetak("Do you want to show more tweets? [Y/N] ");
					retry = consoleScan.bacaString();
				} while (!retry.equals("Y") && !retry.equals("N"));
				
				if(retry.equals("Y")){
					sisa = sisa - 3;
					if(sisa > 3 && sisa!=0){
						hitung = 3;
						ulang = 1;
					}else if(sisa==0){
						ulang = 2;
					}else{
						ulang = 0;
					}
				}else{
					consoleScan.cetakEnter("");
					consoleScan.cetakEnter("Press enter to go back home...");
					consoleScan.bacaString();
					return;
				}
			}
			if(ulang==2){
				consoleScan.cetakEnter("");
				consoleScan.cetakEnter("Press enter to go back home...");
				consoleScan.bacaString();
				return;
			}
			if(ulang==0){
				for (int i = tweetList.size() - sisa; i < tweetList.size(); i++) {
					consoleScan.cetakEnter("==================================");
					consoleScan.cetakEnter("User : "+tweetList.get(i).getTweetCreator());
					consoleScan.cetakEnter("----------------------------------");
					consoleScan.cetakEnter(tweetList.get(i).getTweetContent());
					consoleScan.cetakEnter("==================================");
					new KThread(tweetList.get(i)).fork();
					consoleScan.cetakEnter("");
				}
				consoleScan.cetakEnter("");
				consoleScan.cetakEnter("Press enter to go back home...");
				consoleScan.bacaString();
				return;
			}
		}
	}
	
	private void createTweet() {
		String tweetContent = "",tweetCreator = "";
		String pilihan;
		
		do {
			consoleScan.cetakEnter("Input your tweet [1-50 characters]: ");
			tweetContent = consoleScan.bacaString();
			if(tweetContent.isEmpty()){
				consoleScan.cetakEnter("Tweet content cannot be empty!");
			}
		} while (tweetContent.isEmpty() || tweetContent.length() < 1 || tweetContent.length() > 50);
		
		do{
			consoleScan.cetakEnter("Input your username [20 characters max | Leave empty to stay anonymous]: ");
			tweetCreator = consoleScan.bacaString();
		}while(tweetCreator.length() > 20);
		
		do {
			consoleScan.cetak("Do you want to post this tweet? (Anonymous tweets cannot be deleted) [Y/N] ");
			pilihan = consoleScan.bacaString();
		} while (!pilihan.equals("Y") && !pilihan.equals("N"));
		if(pilihan.equals("Y")){
			if(tweetCreator.isEmpty()){
				tweetCreator = "ANONYMOUS";
			}
			String isiFile = tweetContent + "#" + tweetCreator;
			fileSaya.write(isiFile);
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("Tweet Posted!");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}else{
			return;
		}
	}
	
	public void overWrite(Vector<Tweet> tweetList) {
		String txt="";
		for (Tweet tweet : tweetList) {
			txt+=(tweet.getTweetContent() + "#" + tweet.getTweetCreator() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private Vector<Tweet> usernameData(String username){
		Vector<Tweet> usernameTweetList = new Vector<>();
		for (Tweet tweet : tweetList) {
			if(tweet.getTweetCreator().equals(username)){
				usernameTweetList.add(tweet);
			}
		}
		if(usernameTweetList.isEmpty()){
			return null;
		}
		return usernameTweetList;
	}
	
	private void deleteTweet() {
		String username;
		int number = 0;
		int indexDelete;
		int indexTweetAsli = 0;
		if(tweetList.isEmpty()){
			consoleScan.cetak("Sadly no one has tweeted yet, including you.. Follow other users and create tweets to fill the timeline!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		do {
			consoleScan.cetakEnter("Input your username : ");
			username = consoleScan.bacaString();
			if(username.isEmpty()){
				consoleScan.cetakEnter("Anonymous tweets cannot be deleted!");
			}
		} while (username.isEmpty());
		
		Vector<Tweet> usernameTweetList = usernameData(username);
		if(usernameTweetList==null){
			consoleScan.cetakEnter("No tweet has been made with this username..");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}else{
			for (Tweet tweet : usernameTweetList) {
				consoleScan.cetakEnter((++number) +". " + tweet.getTweetContent());
			}
			
			do{
				consoleScan.cetak("Choose the tweet you want to delete [1-"+usernameTweetList.size() +" | 0 to exit] : ");
				indexDelete = consoleScan.bacaInteger();
				if(indexDelete < 1 || indexDelete > usernameTweetList.size()){
					consoleScan.cetakEnter("Invalid tweet index, please try again!");
				}
			}while(indexDelete < 1 || indexDelete > usernameTweetList.size());
			
			String kalimatDelete = usernameTweetList.get(indexDelete-1).getTweetContent();
			for(int i = 0 ; i < tweetList.size(); i++){
				if(kalimatDelete.equals(tweetList.get(i).getTweetContent())){
					indexTweetAsli = i;
					break;
				}
			}
			
			tweetList.remove(indexTweetAsli);
			overWrite(tweetList);
			consoleScan.cetakEnter("Tweet deleted!");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}
	}
}
