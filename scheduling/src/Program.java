import ucll.processscheduler.*;

public class Program
{
    public static void main(String[] args)
    {
        ProcessSchedulingAlgorithm algorithm = new Priority();

        ProcessScheduler scheduler = new ProcessScheduler();
        scheduler.schedule(algorithm);
    }
}
