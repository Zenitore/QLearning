/**
 * StackNode.java
 * Author: Ethan Ayers
 * Date: 09/21/2023
 * Collaborators: Franklin Young, Luke O'drobinak, Roan Silver
 * Citations: Google, Oracle Java Docs, GitHub Documentation, Stack Overflow
 **/



public class StackNode<T> {

    private StackNode<T> parentNode;
    private T value;

    public StackNode() {          //Default constructor
        parentNode = null;
        value = null;
    }

    public StackNode(T value) {  //Constructor for data input only
        this.value = value;
        parentNode = null;
    }

    public StackNode(T value, StackNode<T> parentNode) {  //Constructor for data and node input
        this.value = value;
        this.parentNode = parentNode;
    }

    //Setters and getters for the node class
    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setParentNode(StackNode<T> parentNode) {
        this.parentNode = parentNode;
    }

    public StackNode<T> getParentNode() {
        return parentNode;
    }

    //toString and equals methods (Overrides)
    public String toString() {
        if (value == null) {
            return "Node: null";
        } else {
            return "Node: " + value.toString();
        }
    }

    public boolean equals(StackNode<T> node) {
        return this.value.equals(node.getValue());
    }

}

