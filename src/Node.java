/**
 * Created by leviallery on 9/14/16.
 */
public class Node implements Comparable<Node> {
    private int count; //# of times it showed up in the string
    private char character;
    private Node leftChild;
    private Node rightChild;

    public Node(int a, char b) {
        count = a;
        character = b;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(Node otherNode) { //Queue uses this to organize Nodes based on the count
        if (this.count == otherNode.count) {
            return 0;
        } else if (this.count > otherNode.count) {
            return 1;
        } else if (this.count < otherNode.count) {
            return -1;
        } else {
            return 0;
        }
    }
}
