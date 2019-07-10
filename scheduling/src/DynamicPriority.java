import ucll.processscheduler.ProcessControlBlock;
import ucll.processscheduler.ProcessSchedulingAlgorithm;
import ucll.processscheduler.ProcessState;
import ucll.processscheduler.ScheduleInformation;

import java.util.HashMap;
import java.util.LinkedList;

public final class DynamicPriority extends ProcessSchedulingAlgorithm
{
    private LinkedList<ProcessControlBlock> _submittedQueue;
    private final HashMap<ProcessControlBlock, Integer> _readyTime;

    public DynamicPriority()
    {
        super(true);
        _submittedQueue = new LinkedList<ProcessControlBlock>();
        _readyTime = new HashMap<ProcessControlBlock, Integer>();
    }

    public void processReady(ScheduleInformation info, ProcessControlBlock readyProcess)
    {
        if(readyProcess.getState() == ProcessState.Ready)
        {
            _readyTime.put(readyProcess, info.getCurrentTime());
        }
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

    private ProcessControlBlock getDynamicPriority(ScheduleInformation info, ProcessControlBlock process)
    {
       Integer delta = info.getCurrentTime() - _readyTime.get(process);
       if(delta > 100)
       {
           Integer priorityIncrease = delta / 100;
           System.out.println(priorityIncrease);
           Integer dynamicPriority = process.getPriority() - priorityIncrease;
       }
    }

}