public class SolutionWithSynchronization implements MemoryWrapper{

    private MemorySegment _memory = null;
	private boolean writing = false;
	private boolean reading = false;

	public SolutionWithSynchronization(){
	_memory = new MemorySegment();
    }

    public  void read(Process p){
    	p.setState("wantread");
    	synchronized(this)
		{
			while (writing)
			{
				try
				{
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
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
    	while(this.writing || this.reading)
		{
			try
			{
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
    	notifyAll();
    	p.setState("idle");
    }
}
