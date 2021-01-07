package nachos.proj1;

import java.util.Vector;

import nachos.threads.KThread;
import nachos.threads.ThreadQueue;

public class MyThreadQueue extends ThreadQueue{

	private Vector<KThread> threadList = new Vector<KThread>();
	
	public MyThreadQueue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void waitForAccess(KThread thread) {
		threadList.add(thread);
		// TODO Auto-generated method stub
		
	}

	@Override
	public KThread nextThread() {
		// TODO Auto-generated method stub
		if(threadList.isEmpty())
			return null;
		return threadList.remove(0);
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
