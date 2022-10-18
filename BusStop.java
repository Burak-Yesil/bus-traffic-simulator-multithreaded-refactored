package BusSimulation;
import java.util.*;

public class BusStop {

    int numPeople;
    int busStopNumber;
    Random rand;
    boolean isBusy;
    int curBus;
    int numOfBuses;

    BusStop(int busStopNumber, int numOfBuses){
        this.busStopNumber = busStopNumber;
        rand = new Random();
        numPeople = 0;
        isBusy = false;
        curBus = -1;
        this.numOfBuses = numOfBuses;
    }

    public void addPassenger(){
        numPeople++;
    }

    public boolean isFirstinQueue(int testBus){
        return (((curBus+1)%numOfBuses) == testBus);
    }

    public PersonEvent generatePerson(double curTime,double lambda){
        return new PersonEvent(curTime+getNextPersonTime(lambda),busStopNumber);
    }

    public BoarderEvent generateBoarding(double curTime, double boardingTime,int busNumber){
        return new BoarderEvent(curTime+boardingTime,busStopNumber, busNumber);
    }

    public ArrivalEvent generateArrival(double curTime, double driveTime,int busNumber,int numBusStops){
    	isBusy = false;
    	return new ArrivalEvent(driveTime+curTime,(busStopNumber+1)%(numBusStops),busNumber);
    }

    public void busArrives(int busNumber){
        isBusy = true;
        curBus = busNumber;
    }


    public double getNextPersonTime(double lambda){
        return -Math.log(1-rand.nextDouble())/lambda;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("busStopNumber: ").append(busStopNumber+1).append(" | ");
        //sb.append("busQueue: ").append(busQueue).append(" | ");
        sb.append("numPassengers: ").append(numPeople).append(" | ").append("\n");
        return sb.toString();
    }
}