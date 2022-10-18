package BusSimulation;

public class PersonEvent extends SimulationEvent {
    public PersonEvent(double EventTime,int BusStop) {
        super(EventTime,BusStop); //Using parent constructor
        Type = KindOfEvent.Person; //The event type is person 
    }
}