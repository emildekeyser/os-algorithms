import java.util.LinkedList;
import java.util.Queue;

public class SolutionWithSynchronizationAndQueue implements MemoryWrapper{

    private MemorySegment _memory = null;
	private boolean writing = false;
	private boolean reading = false;
	private Queue<Process> Q = new LinkedList<>();

	public SolutionWithSynchronizationAndQueue(){
	_memory = new MemorySegment();
    }

    public void read(Process p){
    	p.setState("wantread");
    	synchronized(this)
		{
			while (!this.Q.peek().equals(p))
			{
				try
				{
					if (!this.Q.contains(p)){this.Q.add(p);}
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			this.Q.poll();
			this.reading = true;
			p.setState("reading");
		}
			_memory.read();
    	synchronized (this)
		{
			this.reading = false;
			notifyAll();
			p.setState("idle");
		}

    }

    public synchronized void write(Process p){
    	p.setState("wantwrite");
//    	while(this.writing || this.reading)
        while(!this.Q.peek().equals(p))
		{
			try
			{
				if (!this.Q.contains(p)){this.Q.add(p);}
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		this.writing = true;
    	p.setState("writing");
    	_memory.write();
    	this.writing = false;
    	this.Q.poll();
    	notifyAll();
    	p.setState("idle");
    }
}
