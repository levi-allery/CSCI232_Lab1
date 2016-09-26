import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


/**
 * @author Levi Allery
 *         Montana State University CSCI 232
 *         09/26/2016
 **/
public class Driver {
    String[] binaryArray;

    public Driver() {
        // Variables
        binaryArray = new String[29]; // Create a new Array to hold the binary code that matches our characters
        PriorityQueue<Node> pq = new PriorityQueue<Node>(); //This queue organizes the nodes based on their count
        int[] freqArray = new int[29]; //Array to hold the frequency that the character is used.
        Scanner scn = new Scanner(System.in);
        Node root = null;


        //Start of menu. Left in driver instead of making everything static and accessing from outside of class.
        boolean menuFinished = false;
        boolean done = false;
        boolean somethingEntered = false;
        String input = null;


        while (!menuFinished) {
            System.out.println("Enter first letter of enter, show, code, decode, or finished: ");
            String selection = scn.nextLine();
            switch (selection) {
                case "e": // Allows user to enter text
                    System.out.println("Enter text lines, terminate with $");
                    input = "";
                    while (!done) {
                        String ns = scn.nextLine().toUpperCase();//Assigns capitals to make using ascii easier
                        ns = ns.replace(" ", "["); // Replacing spaces with '['
                        if (ns.charAt(0) == '$') { //Keeps scanning until '$' is found
                            done = true;
                        } else {
                            input += ns;
                            input += "\\";
                        }
                    }
                    System.out.println(input);
                    for (int j = 0; j < input.length(); j++) {
                        if (input.charAt(j) == ' ') { //Checks for spaces
                            freqArray[26]++;// The '[' variable increments
                        } else {
                            freqArray[(int) input.charAt(j) - 65]++;
                        }
                    }
                    for (int i = 0; i < 29; i++) { // For loop to create nodes
                        System.out.print((char) (i + 65) + " ");//Prints the Characters with their counts
                        if (freqArray[i] > 0) {//Checking for the characters that weren't used
                            Node current = new Node(freqArray[i], (char) (i + 65));// Creates a Node with the characters
                            pq.add(current); // Adding the Nodes to a priority queue based on the count
                        }
                    }
                    System.out.println();
                    for (int j = 0; j < 29; j++) {
                        System.out.print(freqArray[j] + " ");

                    }
                    while (pq.size() > 1) { // While the queue has something besides the root
                        Node one = pq.poll();
                        Node two = pq.poll();//Pop two out of the queue
                        int total = one.getCount() + two.getCount(); // Add their counts
                        Node newNode = new Node(total, '+'); // Create a node with the new count and a dummy character
                        newNode.setLeftChild(one);// Set children just like a tree
                        newNode.setRightChild(two);
                        pq.add(newNode);// Put this new node back in the queue
                    }
                    root = pq.poll();// Get the root out of the queue
                    String s = "";
                    getChildren(root, s);
                    somethingEntered = true;
                    System.out.println();
                    break;

                case "s": // Shows the tree
                    if (somethingEntered == false) {
                        System.out.println("Please enter something into the tree before trying to view it \n");

                    } else {
                        displayTree(root);
                    }
                    break;
                case "c": //Display code
                    if (somethingEntered == false) {
                        System.out.println("Please enter something into the tree before trying to view it \n");

                    } else {
                        printCode(input);
                    }
                    break;
                case "d": //Run display code
                    if (somethingEntered == false) {
                        System.out.println("Please enter something into the tree before trying to view it \n");

                    } else {
                        System.out.println("Please enter the coded message");
                        String codedMessage = scn.nextLine();
                        decode(codedMessage);
                    }
                    break;
                case "f": // Exits from code
                    menuFinished = true;
                    break;
                default:
                    System.out.println("Please enter either 'e', 's', 'c', 'd' or 'f'");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Driver d = new Driver(); //This prevents Static errors when calling displayTree and getChildren
    }


    void getChildren(Node a, String theString) { //Goes through the tree recursively and assigns binary code to characters
        if (a.getLeftChild() == null) { // If a child is null then we are at the end of the tree
            binaryArray[a.getCharacter() - 65] = theString; //Assign the binary string to the char in the binary array
        } else {
            getChildren(a.getLeftChild(), theString + "0");//Else assign a 0 for going left
        }

        if (a.getRightChild() == null) {
            binaryArray[a.getCharacter() - 65] = theString;//Assign the binary string to the char in the binary array
        } else {
            getChildren(a.getRightChild(), theString + "1"); //Else assign a 1 for going right
        }

    }

    // Display Tree Code was provided by MSU CSCI 232 Class Instructor
    public void displayTree(Node root) { // prints the the tree in a simple easy to see view
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.getCharacter() + ": " + temp.getCount());
                    localStack.push(temp.getLeftChild());
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null ||
                            temp.getRightChild() != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        } // end while isRowEmpty is false
        System.out.println(
                "......................................................");
        // end displayTree()
        // -------------------------------------------------------------
        // end class Tree
    }

    void printCode(String input) {
        String codedMessage = "";

        for (int i = 0; i < binaryArray.length; i++) {
            if (binaryArray[i] == null) {

            } else {
                System.out.println((char) (i + 65) + " " + binaryArray[i]);
            }
        }
        for (int i = 0; i < input.length(); i++) { // Take the input character and match the code to it
            codedMessage += binaryArray[input.charAt(i) - 65];
        }
        System.out.println("Coded mesage: " + codedMessage);
    }

    void decode(String code) { //Decodes message given a coded string
        String s = ""; //String holds decoded message
        for (int i = 0; i < code.length(); i++) {
            s += code.charAt(i);
            for (int j = 0; j < binaryArray.length; j++) {
                if (binaryArray[j] != null) {
                    if (binaryArray[j].toString().equals(s)) {
                        System.out.print((char) (j + 65));
                        s = "";
                    } else {
                    }
                } else {

                }
            }
        }
        System.out.println();
    }
}

