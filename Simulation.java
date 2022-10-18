package BusSimulation;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

//import java.util.PriorityQueue;
public class Simulation {

    //List and ArrayLists
    private static ArrayList<BusStop> busStops; //Stores the bus stops originally had this as a circular linked list but changed it to an arraylist
    private static PriorityQueue<SimulationEvent> eventHandler;//Priority queue that does the event with the smaller time first. Uses EventComparator.java. Stores bus events.
    private static ArrayList<Integer> ListOfBuses;


    //Int and Double Variables
    private static int numOfbusStops; //The total number of bus stops 
    private static int numOfBuses; //The total number of buses 
    private static int timeBetweenTwoStops; //the time it takes a bus to travel from one stop to another
    private static double boardTime; //The total boarding time 
    private static int simulationTime; //The total time for the whole simulation
    private static double simulationCurrentTimeStatus = 0; //The default time. This is used to keep track of the simulations runtime.
    private static double speedPerPerson; //speed per person used as lambda for get next person time function
    private static double timeBetweenSnapShots;



    ////////////////////////////////
    ////////////////////////////////
    /// INTIALIZATION FUNCTIONS: ///
    ////////////////////////////////
    ////////////////////////////////

    public static void InitializeParameterVariables(){
        //Usage: sets the default values for the variables above
        numOfbusStops = 5;
        numOfBuses = 5;
        timeBetweenTwoStops = 300;
        boardTime = 3;
//        simulationTime = 5000;
//        simulationTime = 28800;
        simulationTime = 100000;
        speedPerPerson = .0333;
        timeBetweenSnapShots = 250;
    }
    public static void InitializeBusRoute(){
        //Usage: Adds the bus stops to the busStops arraylist 
        //and adds 1 person to bus stop by adding a newPersonArrivalEvent to the priority queue "eventHandler"
        busStops = new ArrayList<>();
        for(int i =0; i<numOfbusStops;i++){
            busStops.add(new BusStop(i, numOfBuses));
            eventHandler.add(new PersonEvent(0,i)); //Because the personArrivalEvents times are set to 0, they will happen first.
        }
    }
    public static void InitializeBusLocations(){
        //Usage: Uniformly distributes the buses across the route
    	ListOfBuses = new ArrayList<>();
        int currentStop = 0;
        int busDistance = numOfbusStops/numOfBuses; //The distance to have each bus uniformly distributed
        for(int i=0;i<numOfBuses;i++){
            eventHandler.add(new ArrivalEvent(0,currentStop,i)); //Because the event time is 0, this happens before the simulation starts.
            ListOfBuses.add(currentStop);
            currentStop += busDistance;
        }

    }

    public static void IntializeTheSimulation(){
        //Usage: //Initializes the simulation buses, bus route, and variables 
        eventHandler = new PriorityQueue<>(100, new PriorityQueueComparator()); 
        eventHandler.add(new SnapShotEvent(0));
        InitializeParameterVariables();
        InitializeBusRoute();
        InitializeBusLocations();
    }

    ////////////////////////////////
    ////////////////////////////////
    //// Simulation Functions: /////
    ////////////////////////////////
    ////////////////////////////////

    public static void ChooseEventCase(SimulationEvent currentEvent, double simulationCurrentTime ){
        BusStop curBusStop;
        switch (currentEvent.Type){
	        case SnapShot:
	            for(int i =0; i<numOfbusStops;i++) {
	                System.out.print(busStops.get(i));
	            }
	            for(int i=0;i<numOfBuses;i++) {
	                System.out.println("Bus number: " + (i+1) +" is at " + ListOfBuses.get(i));
	            }
	            System.out.println("\n");
	            eventHandler.add(new SnapShotEvent(simulationCurrentTime + timeBetweenSnapShots));
	            break;
            case Boarder:
                curBusStop = busStops.get(currentEvent.busStop);
                curBusStop.numPeople -= 1;
                if (curBusStop.numPeople == 0){
                    eventHandler.add(curBusStop.generateArrival(simulationCurrentTime,timeBetweenTwoStops,currentEvent.busIndex,numOfbusStops));
                }
                else {
                    eventHandler.add(curBusStop.generateBoarding(simulationCurrentTime,boardTime,currentEvent.busIndex));
                }
                break;
            case Arrival:
                curBusStop = busStops.get(currentEvent.busStop);
                ListOfBuses.set(currentEvent.busIndex, curBusStop.busStopNumber);
                if(curBusStop.isBusy || !curBusStop.isFirstinQueue(currentEvent.busIndex)){
                    eventHandler.add(new ArrivalEvent(1+simulationCurrentTime,curBusStop.busStopNumber,currentEvent.busIndex));
                }
                else if (curBusStop.numPeople==0){
                    eventHandler.add(curBusStop.generateArrival(simulationCurrentTime+1,timeBetweenTwoStops,currentEvent.busIndex,numOfbusStops));
                } else{
                    curBusStop.busArrives(currentEvent.busIndex);
                    eventHandler.add(curBusStop.generateBoarding(simulationCurrentTime,boardTime,currentEvent.busIndex));
                }
                break;
            case Person:
                curBusStop = busStops.get(currentEvent.busStop);
                curBusStop.addPassenger();
                eventHandler.add(curBusStop.generatePerson(simulationCurrentTime,speedPerPerson));
                break;
        }
        
    }



    public static void main(String[] args){
        IntializeTheSimulation(); //Initializes the simulation buses, bus route, and variables
        while (simulationCurrentTimeStatus <= simulationTime){
            SimulationEvent currentEvent = eventHandler.poll(); //pops the next event from the priority queue
            simulationCurrentTimeStatus = currentEvent.time; //Updates the current time status of the simulation
            ChooseEventCase(currentEvent, simulationCurrentTimeStatus); //function chooses the right event using a switch statement
        }
    }
}
