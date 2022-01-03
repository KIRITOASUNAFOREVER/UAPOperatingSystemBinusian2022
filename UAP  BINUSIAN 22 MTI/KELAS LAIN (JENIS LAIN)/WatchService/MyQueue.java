package nachos.WatchService;

import java.util.Vector;

import nachos.threads.KThread;
import nachos.threads.ThreadQueue;

public class MyQueue extends ThreadQueue{
	private Vector<KThread> queue = new Vector<KThread>();
	
	@Override
	public void waitForAccess(KThread thread) {
		queue.add(thread);
	}

	@Override
	public KThread nextThread() {
		if(queue.isEmpty()){
			return null;
		}
		return queue.remove(0);
	}

	@Override
	public void acquire(KThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}
