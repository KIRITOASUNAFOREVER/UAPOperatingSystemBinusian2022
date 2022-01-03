package nachos.WatchService;

import nachos.threads.Scheduler;
import nachos.threads.ThreadQueue;

public class MyScheduler extends Scheduler{

	@Override
	public ThreadQueue newThreadQueue(boolean transferPriority) {
		return new MyQueue();
	}
	
}
