package model;

public class Node<T> {
    public T info;
    public Node<T> next;

    public Node(T info) {
        this.info = info;
        this.next = null;
    }
}