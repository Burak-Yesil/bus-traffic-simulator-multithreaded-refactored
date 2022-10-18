package BusSimulation;

public class ArrivalEvent extends SimulationEvent {
    public ArrivalEvent(double EventTime,int BusStop,int BusNumber) {
        super(EventTime,BusStop);
        this.busIndex = BusNumber;
        Type = KindOfEvent.Arrival;
    }
}