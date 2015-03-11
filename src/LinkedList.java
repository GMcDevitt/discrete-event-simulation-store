import java.util.NoSuchElementException;

/**
 * Developed in IntelliJ Idea 14 Ultimate by
 * Gerard McDevitt
 * Jackie Plum
 *
 * A Linked List class that can handle Events
 */

public class LinkedList {
    //Points to the first node
    Node firstNode;
    //Points to the last node
    Node lastNode;
    //The size of the list
    private int size = 0;

    /**
     * A node to contain a single Event used in the LinkedList
     */
    public class Node {
        //The integer inside of the node
        private Event item;
        //The next node in succession
        private Node nextNode;
        //The previous node in order
        private Node prevNode;

        Node(Node prev, Event item, Node next) {
            this.item = item;
            this.nextNode = next;
            this.prevNode = prev;
        }

        public Event getItem() {
            return this.item;
        }

        public Node getNextNode() {
            return this.nextNode;
        }
    }

    /**
     * An Event used in the simulation setCurrentEvent.
     *
     * @author Gerard
     */
    public static class Event {
        private int time;           //The arrival time and then departure time of the customer
        private boolean last;       //Flag to end simulation
        private boolean isArrival;  //Flag to determine if the customer is arriving or departing.

        /**
         * Generates a customer with a random arrival time.
         * Sets the event as an arrival.
         * @param last boolean to decide whether this is the last event in the list.
         */
        public Event(boolean last) {
            this.isArrival = true;
            this.time = 0;
            this.last = last;
        }

        /**
         * Returns the time of the event
         */
        public int getTime() {
            return this.time;
        }

        /**
         * Sets the time of the event
         */
        public void setTime(int time) {
            this.time = time;
        }

        /**
         * Sets the event as a departure.
         * Also updates the time of an event by adding a new randomly generated time to the old time.
         */
        public void setAsDeparture() {
            this.isArrival = false;
        }

        public boolean isArrival() {
            return this.isArrival;
        }

        /**
         * Check if the event is the last event in the list
         */
        public boolean isLast() {
            return last;
        }
    }

    /**
     * Links the first element of the list
     */
    public void linkFirst(Event item) {
        //Assign a node as the first node in the list
        final Node firstNode = this.firstNode;
        //Construct a new node with an integer
        final Node newNode = new Node(null, item, firstNode);
        //fill the first node of the list with the new node
        this.firstNode = newNode;
        //if no item was passed into the node, then the list is still empty
        if (firstNode == null) {
            //Make the last node the new node aswell
            lastNode = newNode;
        }
        else {
            //Otherwise
            firstNode.prevNode = newNode;
        }
        size++;
    }

    /**
     * Links the last element of the list
     */
    public void linkLast(Event item) {
        //Assign a node as the last node in the list
        final Node lastNode = this.lastNode;
        //Construct a new node with an integer
        final Node newNode = new Node(lastNode, item, null);
        //Fill the last node with the new node
        this.lastNode = newNode;
        //If there are no other nodes in the list, then this is also the first node
        if (lastNode == null) {
            firstNode = newNode;
        }
        else {
            lastNode.nextNode = newNode;
        }
        size++;
    }

    /**
     * Unlinks the first node in the list
     */
    public void unlinkFirst(Node node) {
        //Node used to re-link the list
        final Node next = node.nextNode;
        //Empty the node specified
        node.nextNode = null;
        //Re-assign the first node as the next node
        firstNode = next;
        //If the next node is null, that is the end of the list and this ensures the list is empty
        if (next == null) {
            lastNode = null;
        }
        else {
            next.prevNode = null;
        }
        size--;
    }

    /**
     * Unlinks the last item in the list
     */
    public void unlinkLast(Node node) {
        //Node used to re-link the list
        final Node prev = node.prevNode;
        //Empty the node specified
        node.prevNode = null;
        //Assign the previous node as the new last node
        lastNode = prev;
        //If the previous node is null then it was the first node and the list is empty
        if (prev == null) {
            firstNode = null;
        }
        else {
            prev.nextNode = null;
        }
        size--;
    }

    /**
     * Returns the first element in the list
     */
    public Event getFirst() {
        final Node first = firstNode;
        //If first is null, then throw the java.util NoSuchElementException
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    /**
     * Returns the last element in the list
     */
    public Event getLast() {
        final Node last = lastNode;
        //if last is null then throw the java.util NoSuchElementException
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.item;
    }

    /**
     * Removes the first item in the list
     */
    public void removeFirst() {
        final Node first = firstNode;
        //if first is null then throw the java.util NoSuchElementException
        if (first == null) {
            throw new NoSuchElementException();
        }
        //Call unlinkFirst to unlink the node
        unlinkFirst(first);
    }

    /**
     * Removes the last item in the list
     */
    public void removeLast() {
        final Node l = lastNode;
        //if last is null then throw the java.util NoSuchElementException
        if (l == null) {
            throw new NoSuchElementException();
        }
        //Call unlinkLast to unlink the node
        unlinkLast(l);
    }

    /**
     * Adds a node to the beginning of the list
     * Simply calls linkFirst
     */
    public void addFirst(Event item) {
        linkFirst(item);
    }

    /**
     * Adds a node to the end of the list
     * Simply calls linkLast
     */
    public void addLast(Event item) {
        linkLast(item);
    }

    /**
     * Adds a node to the list
     * it really just adds it to the end
     */
    public void add(Event item) {
        linkLast(item);
    }

    /**
     * Checks if the list Contains an Event
     */
    public boolean contains(Event i){
        //Returns the index if it does not equal -1
        return indexOf(i) != -1;
    }

    /**
     * Returns the size of the list
     */
    public int size() {
        return size;
    }

    /*
     * Returns the node at a specified index, this relies upon you knowing the index
     */
    public Event get(int index){
        return node(index).item;

    }

    /**
     * Returns the node at the specified index
     * Starts from one end of the list or the other depending what index you give
     */
    Node node(int index) {
        //If the node is in the first half of the list
        if (index < (size / 2)) {
            Node x = firstNode;
            //Iterate up until the index starting from the beginning
            for (int i = 0; i < index; i++)
                x = x.nextNode;
            return x;
        } else {
            Node x = lastNode;
            //Else iterate to the index starting from the end
            for (int i = size - 1; i > index; i--)
                x = x.prevNode;
            return x;
        }
    }

    /**
     * Finds the index of a given event
     */
    public int indexOf(Event event) {
        //Used to find the index of a number
        int index = 0;
        //If the search criteria is null
        if (event == null) {
            //Search through all non null nodes
            for (Node x = firstNode; x != null; x = x.nextNode) {
                //If the item in the node is null, return the index
                if (x.item == null) {
                    return index;
                }
                //Else increase the index
                index++;
            }
        } else {
            //Search through all non null nodes
            for (Node x = firstNode; x != null; x = x.nextNode) {
                //If the number is in the node, then return the index
                if (event.equals(x.item)) {
                    return index;
                }
                //Else increase the index
                index++;
            }
        }
        //Return -1 if nothing is found
        return -1;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, Event element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /**
     * Tells if the argument is the index of a valid position for an
     * iterator or an add operation.
     */
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: " + size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Inserts element e before non-null Node succ.
     */
    void linkBefore(Event e, Node succ) {
        // assert succ != null;
        final Node pred = succ.prevNode;
        final Node newNode = new Node(pred, e, succ);
        succ.prevNode = newNode;
        if (pred == null)
            firstNode = newNode;
        else
            pred.nextNode = newNode;
        size++;
    }
}
