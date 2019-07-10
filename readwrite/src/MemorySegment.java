import java.util.Random;

public class MemorySegment
{

    private static MemorySegment _segment = null;
    private Random rand = new Random();

    protected MemorySegment()
    {
    }

    public void read()
    {
        synchronized (this)
        {
            System.out.println("Reading...");
            try
            {
                Thread.sleep(rand.nextInt(1000));
            }
            catch (InterruptedException ie) {}
            System.out.println("Done reading...");
        }
    }

    public void write()
    {
        synchronized (this)
        {
            System.out.println("Writing...");
            try
            {
                Thread.sleep(rand.nextInt(1000));
            }
            catch (InterruptedException ie) {}
            System.out.println("Done writing...");
        }
    }

}
