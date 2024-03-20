/**
 * StackNode.java
 * Author: Ethan Ayers
 * Date: 09/21/2023
 * Collaborators: Franklin Young, Luke O'drobinak, Roan Silver
 * Citations: Google, Oracle Java Docs, GitHub Documentation, Stack Overflow
 **/

public class Stack<T> {

    int size = 0;
    StackNode<T> currentNode = new StackNode<>();
    public Stack(){
    }

    public void push(T element){
        StackNode<T> createdNode = new StackNode<>(element);
        if (size == 0){
            currentNode = createdNode;
        }
        else{
            createdNode.setParentNode(currentNode);
            currentNode = createdNode;
        }
        size++;
    }    //add an element

    public T pop(){
        StackNode<T> deletedNode = currentNode;
        currentNode = currentNode.getParentNode();
        size--;
        return deletedNode.getValue();
    }                 //remove and return the top element

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public T peek(){
        return currentNode.getValue();
    }                //look at the top element without removing


}