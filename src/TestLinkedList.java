/**
 * Main method used to test the custom made Linked List class
 * Made using IntelliJ Idea 14
 *
 * @author Gerard McDevitt, Jackie Plum
 */
public class TestLinkedList {

    public  static void main(String[] args){

        //Construst my linked list
        LinkedList linkedList = new LinkedList();

        //Add the firstNode item to my list
        linkedList.addFirst(new LinkedList.Event(false));

        //Load the list with 15 items
        for(int i = 2; i < 16; i++){
            linkedList.add(new LinkedList.Event(false));
        }

        //Add the lastNode item to my list
        linkedList.addLast(new LinkedList.Event(true));


        //Checking that my get methods work
        System.out.println("The size of the list should be 16 and is: " + linkedList.size());
        System.out.println("The firstNode item should be 1 and is: " + linkedList.getFirst().toString());
        System.out.println("The middle item should be 8 and is: " + linkedList.get(7).toString());
        System.out.println("The lastNode item should be 16 and is: " + linkedList.getLast().toString());

        //Print out my list
        for(int i = 0; i < linkedList.size(); i++){
            System.out.println(linkedList.get(i).toString());
        }

        //Remove the firstNode item
        linkedList.removeFirst();

        System.out.println("The firstNode item should now be 2 and is: " + linkedList.getFirst().toString());

        //Remove the lastNode item
        linkedList.removeLast();

        System.out.println("The firstNode item should now be 15 and is: " + linkedList.getLast().toString());

        //Create 2 new events
        LinkedList.Event bill = new LinkedList.Event(false);
        LinkedList.Event harold = new LinkedList.Event(true);

        //Add harold to the list
        linkedList.add(harold);

        //Check the contain method
        System.out.println("Does the list contain bill? (it shouldn't): " + linkedList.contains(bill));
        System.out.println("Does the list contain harold? (it should): " + linkedList.contains(harold));

        //Remove the last event who should be harold
        linkedList.removeLast();

        System.out.println("Does the list contain harold? (it shouldn't): " + linkedList.contains(harold));

        //Check the size method
        System.out.println("The list should have 14 items: " + linkedList.size());



    }

}
