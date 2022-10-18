package BusSimulation;

public class BoarderEvent extends SimulationEvent {
	
    public BoarderEvent(double EventTime,int BusStop,int BusNumber) {
        super(EventTime,BusStop);
        this.busIndex = BusNumber;
        Type = KindOfEvent.Boarder;
    }
}