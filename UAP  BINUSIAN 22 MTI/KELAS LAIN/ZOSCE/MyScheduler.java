package nachos.ZOSCE;

import nachos.threads.Scheduler;
import nachos.threads.ThreadQueue;

public class MyScheduler extends Scheduler{

	public MyScheduler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ThreadQueue newThreadQueue(boolean transferPriority) {
		return new MyQueue();
	}

}
