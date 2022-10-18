package BusSimulation;

public class SnapShotEvent extends SimulationEvent{

    public SnapShotEvent(double EventTime) {
        super(EventTime, -1);
        Type = KindOfEvent.SnapShot;
    }
}