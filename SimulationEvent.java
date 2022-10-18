package BusSimulation;

public abstract class SimulationEvent {
    public final double time;
    public final int busStop;
    public int busIndex;
    public KindOfEvent Type;

    public SimulationEvent(double EventTime, int BusStop){
    	this.busIndex = -1; //Default busIndex
    	this.busStop = BusStop;
        this.time = EventTime;
    }
    
    
    enum KindOfEvent { //The Event type names are defined according to the slides
    	Person,
    	Arrival,
    	Boarder,
    	SnapShot
    }

    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Event: ").append(Type).append(" | ");
        sb.append("Time Status: ").append(time).append(" | ");
        sb.append("Bus Index: ").append(busIndex).append("\n");
        sb.append("Bus Stop: ").append(busStop).append(" | ");
        return sb.toString();
    }


}