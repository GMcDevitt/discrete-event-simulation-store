/**
 * Class used to for a discrete event simulation of 100 events
 * Written by Gerard McDevitt and Jackie Plum using IntelliJ Idea 14 Ultimate
 * @author Gerard
 */
public class Main {

    public static void main(String[] args) {

        //The time of the previous pass in the simulation.
        int previousClock = 0;
        //The longest line length, wait time and service time
        int lineLength = 0;
        int waitTime = 0;
        int longestService = 0;
        //The shortest service time and the average service time
        int shortestService = 0;
        int averageService = 0;
        //Array of all the service times in the simulation (used to calculate the average service time)
        int[] serviceTimes = new int[100];


        //List used to hold the 100 events;
        LinkedList eventList = new LinkedList();
        //List used to hold the events that cannot be serviced by the server.
        LinkedList serviceQueue = new LinkedList();
        //Server used to setCurrentEvent events from the eventList;
        Server server = new Server();
        //Next event pulled from the linked list.
        LinkedList.Event nextFromEventList;
        //Clock used to track the time of events
        int clock = 0;

        //Load 100 events
        for (int i = 0; i < 100; i++) {
            //use clock to set incrementing times for the arrivals
            clock = clock + RandomForSimulations.getPoisson(5);
            //Create an event
            LinkedList.Event event = new LinkedList.Event(false);
            //Set the time to the clock
            event.setTime(clock);

            //Add a new event to the list
            eventList.add(event);
        }

        //Create the last event for the list
        LinkedList.Event lastEvent = new LinkedList.Event(true);
        //Set the time of the last event for sorting purposes
        lastEvent.setTime(clock + RandomForSimulations.getPoisson(5));
        //Add the last event with isLast set to true;
        eventList.add(lastEvent);

        /** Begin Simulation */

        //While there are events in the list and the first node is not marked to end the loop
        int i = 0;
        while (eventList.size() != 0) {

            //Get the next event
            nextFromEventList = eventList.getFirst();
            //Remove it from the list
            eventList.removeFirst();
            //Save the previous time
            previousClock = clock;
            //Update clock
            clock = nextFromEventList.getTime();

            //If the event is an arrival
            if (nextFromEventList.isArrival()) {
                //If the server is not busy
                if (! server.hasEvent()) {
                    //Add the event to the server
                    server.setCurrentEvent(nextFromEventList);
                    //Update the server's event with a new departure time
                    server.getCurrentEvent().setTime(clock + RandomForSimulations.getPoisson(5));
                    //Add the departure event into the eventList
                    //For every item in the event list
                    for (LinkedList.Node node = eventList.firstNode; node != null; node = node.getNextNode()) {
                        //If the current event time is less than the next nodes time
                        if (server.getCurrentEvent().getTime() < node.getItem().getTime()) {
                            //Add it before that node so that order is preserved
                            eventList.add(eventList.indexOf(node.getItem()), server.getCurrentEvent());
                            //Break out of the loop to keep it from adding over and over
                            //TODO figure out how to not use a break statement
                            break;
                        }
                    }
                }
                //Otherwise the server is busy
                else {
                    //Add the event to the service queue
                    serviceQueue.add(nextFromEventList);
                }
            }

            //Otherwise its a departure.
            else {
                //Remove the event from the server
                server.removeCurrentEvent();
                //If the service queue is not empty
                if (serviceQueue.size() != 0) {
                    //Get the next event from the service queue
                    LinkedList.Event nextFromServiceQueue = serviceQueue.getFirst();
                    //Remove the event from the service queue
                    serviceQueue.removeFirst();
                    //Add the event to the server
                    server.setCurrentEvent(nextFromServiceQueue);
                    //Update the current event with a new departure time
                    server.getCurrentEvent().setTime(clock + RandomForSimulations.getPoisson(5));
                    //Add the departure event into the eventList
                    //For every item in the event list
                    for (LinkedList.Node node = eventList.firstNode; node != null; node = node.getNextNode()) {
                        //If the current event time is less than the next nodes time
                        if (server.getCurrentEvent().getTime() < node.getItem().getTime()) {
                            //Add it before that node so that order is preserved
                            eventList.add(eventList.indexOf(node.getItem()), server.getCurrentEvent());
                            //Break out of the loop to keep it from adding over and over
                            //TODO figure out how to not use a break statement
                            break;
                        }
                    }
                    //If the service time for the customer was longer than the previous maximum
                    if(longestService < nextFromEventList.getTime() - previousClock) {
                        longestService = nextFromEventList.getTime() - previousClock;
                    }

                    serviceTimes[i] = nextFromEventList.getTime() - previousClock;
                    i++;
                }
            }
            //Getting the statistics about the simulation
            //If the service queue's max length got bigger
            if(lineLength < serviceQueue.size()) {
                lineLength = serviceQueue.size();
            }
        }


        System.out.printf("The longest the line got was %s", lineLength);
        System.out.printf("The longest service time was %s", longestService);
        int average = 0;
        //Calculate the average service time
        for (i = 0; i < 100; i++) {
            average = + serviceTimes[i];
        }
        System.out.printf("The average service time was %s", average/100);

    }
}
