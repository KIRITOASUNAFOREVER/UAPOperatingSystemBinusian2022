package nachos.WatchService;

public class Service implements Runnable{
	private String serviceName;
	private String serviceType;
	private String serviceBrand;
	
	public Service(String serviceName, String serviceType, String serviceBrand) {
		super();
		this.serviceName = serviceName;
		this.serviceType = serviceType;
		this.serviceBrand = serviceBrand;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceBrand() {
		return serviceBrand;
	}

	public void setServiceBrand(String serviceBrand) {
		this.serviceBrand = serviceBrand;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}