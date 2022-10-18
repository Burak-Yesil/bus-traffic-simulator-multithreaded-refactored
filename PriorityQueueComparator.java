package BusSimulation;
import java.util.Comparator;

public class PriorityQueueComparator implements Comparator<SimulationEvent> {

    @Override
    public int compare(SimulationEvent Event1, SimulationEvent Event2) {
        //Usage: this is the comparator function used by the priority queue.
        return (int) ((Event1.time - Event2.time)*1000);
    }
}