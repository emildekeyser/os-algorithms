import ucll.processscheduler.ProcessControlBlock;
import ucll.processscheduler.ProcessSchedulingAlgorithm;
import ucll.processscheduler.ProcessState;
import ucll.processscheduler.ScheduleInformation;

import java.util.LinkedList;

public final class ShortestProcessNext extends ProcessSchedulingAlgorithm
{
    public ShortestProcessNext()
    {
        super(false);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess)
    {
        if (!_submittedQueue.contains(readyProcess))
        {
            _submittedQueue.addLast(readyProcess);
        }
        if (info.getActiveProcess() == null)
        {
            info.setActive(shortestProcess());
        }
    }

    public void processYield(ScheduleInformation info)
    {
        if (info.getActiveProcess().getState() == ProcessState.Finished
            || info.getActiveProcess().getState() == ProcessState.Waiting)
        {
            _submittedQueue.remove(info.getActiveProcess());
            if (_submittedQueue.isEmpty())
            {
                info.setActive(null);
            }
            else
            {
                info.setActive(shortestProcess());
            }
        }
    }

    private ProcessControlBlock shortestProcess()
    {
        ProcessControlBlock shortest = _submittedQueue.getFirst();
        for (ProcessControlBlock p : _submittedQueue)
        {
            if (p.calculateNextBurstEstimate() < shortest.calculateNextBurstEstimate())
            {
                shortest = p;
            }
        }
        return shortest;
    }

    private LinkedList<ProcessControlBlock> _submittedQueue;
}