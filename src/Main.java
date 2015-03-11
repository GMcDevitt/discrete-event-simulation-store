/**
 * Class used for a discrete event simulation of 100 events.
 * Written by Gerard McDevitt and Jackie Plum using IntelliJ Idea 14 Ultimate
 * @author Gerard McDevitt, Jackie Plum
 */
public class Main {

    public static void main(String[] args) {
        //Clock used to track the time of events
        int clock = 0;
        //The time of the previous pass in the simulation.
        int previousClock;
        //The longest line length, server idle time and service time
        int lineLength = 0;
        int idleTime = 0;
        int longestService = 0;

        //List used to hold the 100 events;
        LinkedList eventList = new LinkedList();
        //List used to hold the events that cannot be serviced by the server.
        LinkedList serviceQueue = new LinkedList();
        //Server used to setCurrentEvent events from the eventList;
        Server server = new Server();
        //Next event pulled from the linked list.
        LinkedList.Event nextFromEventList;

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

        /** Begin Simulation */

        //While there are events in the list...
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
                if (!server.hasEvent()) {
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
                    if (longestService < nextFromEventList.getTime() - previousClock) {
                        longestService = nextFromEventList.getTime() - previousClock;
                    }
                }
            }
            //Getting the statistics about the simulation
            //If the service queue's max length got bigger
            if (lineLength < serviceQueue.size()) {
                lineLength = serviceQueue.size();
            }
            //Calculate the idle time
            //If the server does not have a customer, it is idle
            if (!server.hasEvent()) {
            idleTime = idleTime + (clock - previousClock);
            }
        }
        //Statistics reporting
        System.out.printf("The longest the line got was %s customers \n", lineLength);
        System.out.printf("The longest service time was %s minutes \n", longestService);
        System.out.printf("The Server was idle for %s minutes \n", idleTime);

    }
}
