import ucll.processscheduler.ProcessControlBlock;
import ucll.processscheduler.ProcessSchedulingAlgorithm;
import ucll.processscheduler.ProcessState;
import ucll.processscheduler.ScheduleInformation;

import java.util.LinkedList;

public final class Priority extends ProcessSchedulingAlgorithm
{
    public Priority()
    {
        super(true);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess)
    {
        if (!_submittedQueue.contains(readyProcess))
        { _submittedQueue.addLast(readyProcess); }
//        if (info.getActiveProcess() == null)
        info.setActive(highestPriorityProcess());
    }

    public void processYield(ScheduleInformation info)
    {
        _submittedQueue.remove(info.getActiveProcess());
        if (!(info.getActiveProcess().getState() == ProcessState.Finished
              || info.getActiveProcess().getState() == ProcessState.Waiting))
        {
            _submittedQueue.addLast(info.getActiveProcess());
        }

        if (_submittedQueue.isEmpty())
        {
            info.setActive(null);
        }
        else
        {
            info.setActive(highestPriorityProcess());
        }
    }

    private ProcessControlBlock highestPriorityProcess()
    {
        ProcessControlBlock highest = _submittedQueue.getFirst();
        for (ProcessControlBlock p : _submittedQueue)
        {
            if (p.getPriority() < highest.getPriority())
            {
                highest = p;
            }
        }
        return highest;
    }


    private LinkedList<ProcessControlBlock> _submittedQueue;
}