public class SolutionWithNoSynchronization implements MemoryWrapper{

    private MemorySegment _memory = null;

    public SolutionWithNoSynchronization(){
	_memory = new MemorySegment();
    }

    public void read(Process p){
    	p.setState("wantread");
    	p.setState("reading");
    	_memory.read();	
    	p.setState("idle");
    }

    public void write(Process p){
    	p.setState("wantwrite");
    	p.setState("writing");
    	_memory.write();
    	p.setState("idle");
    }
}
