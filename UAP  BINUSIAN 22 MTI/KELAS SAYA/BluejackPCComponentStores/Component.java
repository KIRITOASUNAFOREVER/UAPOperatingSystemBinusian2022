package nachos.BluejackPCComponentStores;

public class Component implements Runnable{
	private String componentName;
	private String componentCategory;
	private String address;


	public Component(String componentName, String componentCategory, String address) {
		super();
		this.componentName = componentName;
		this.componentCategory = componentCategory;
		this.address = address;
	}


	public String getComponentName() {
		return componentName;
	}


	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}


	public String getComponentCategory() {
		return componentCategory;
	}


	public void setComponentCategory(String componentCategory) {
		this.componentCategory = componentCategory;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
