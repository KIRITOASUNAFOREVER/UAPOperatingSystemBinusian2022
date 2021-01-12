package nachos.ZOSCE;

public class Tweet implements Runnable{
	private String tweetContent;
	private String tweetCreator;
	
	public Tweet(String tweetContent, String tweetCreator) {
		super();
		this.tweetContent = tweetContent;
		this.tweetCreator = tweetCreator;
	}

	public String getTweetContent() {
		return tweetContent;
	}

	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}

	public String getTweetCreator() {
		return tweetCreator;
	}

	public void setTweetCreator(String tweetCreator) {
		this.tweetCreator = tweetCreator;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
