package nachos.proj1;

public class Figimon implements Runnable{
	String name;
	String type;
//	private Console console = new Console();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Figimon(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
			System.out.println("Figimon Name : "+getName());
			System.out.println("Figimon Type : "+getType());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
