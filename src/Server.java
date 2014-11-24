
/**
 * Server used to simulate processing an event.
 *
 * @author Gerard McDevitt, Jackie Plum
 */
public class Server {

    private LinkedList.Event currentEvent;  //The customer being served.

    public Server() {
        this.currentEvent = null;
    }

    /**
     * Process the customer from the event list by
     * adding the departure time and
     * setting it as the server's current customer
     */
    public void setCurrentEvent(LinkedList.Event event) {
        event.setAsDeparture();
        this.currentEvent = event;
    }

    /**
     * Return the servers current customer
     * @return
     */
    public LinkedList.Event getCurrentEvent() {
        return this.currentEvent;
    }

    /**
     * Remove the servers current event
     */
    public void removeCurrentEvent() {
        this.currentEvent = null;
    }

    /**
     * Check to see if the server has a event
     * @return
     */
    public boolean hasEvent() {
        return this.currentEvent != null;
    }
}
