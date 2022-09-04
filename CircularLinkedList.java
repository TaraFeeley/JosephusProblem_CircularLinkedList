//JOSEPHUS PROBLEM

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CircularLinkedList<E> implements Iterable<E> {
    //variables
    Node<E> head;
    Node<E> tail;
    int size;  // BE SURE TO KEEP TRACK OF THE SIZE


    //constructor
    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //Helper method theat returns Node<E> found at the specified index
    public Node<E> getNode(int index) {
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public E get(int index) {
//        Node<E> current = head;
//        for (int i = 0; i < index; i++) {
//            current = current.next;
//        }
        return getNode(index).item;
    }

    public void swap(int i, int j) {
        //would want to check that there are at least 2 elements
        // getNode(i) & getNode(j)
        // swap the values (node.item)
        Node<E> iNode = getNode(i);
        Node<E> jNode = getNode(j);
        ;
        E temp = null;
        temp = iNode.item;
        iNode.item = jNode.item;
        jNode.item = temp;

    }

    // attach a node to the end of the list
    public boolean add(E item) {
        this.add(size, item);
        return true;
    }

    // Cases to handle
    // out of bounds
    // adding to empty list
    // adding to front
    // adding to "end"
    // adding anywhere else
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index Out of Bounds");
        }
        Node<E> adding = new Node<>(item);
        if (size == 0) {
            head = adding;
            tail = adding;
            head.next = tail;
        } else if (index == 0) {
            tail.next = adding;
            adding.next = head;
            head = adding;
        } else if (index == size) {
            tail.next = adding;
            adding.next = head;
            tail = adding;
        } else {
            Node<E> before = getNode(index - 1);
            adding.next = before.next;
            before.next = adding;
        }
        size++;
    }


    // remove handles the following cases
    // out of bounds
    // removing the only thing in the list
    // removing the first thing in the list (need to adjust the last thing in the list to point to the beginning)
    // removing the last thing
    // removing any other node
    public E remove(int index) {
        E toReturn = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index Out of Bounds");
        }
        if (size == 1) {
            toReturn = head.item;
            head.next = null;
            head = null;
            tail = null;
        } else if (index == 0) {
            toReturn = head.item;
            head = head.next;
            tail.next = head;

        } else if (index == size - 1) {
            Node<E> before = getNode(index - 1);
            toReturn = tail.item;
            before.next = head;
            tail = before;
        } else {
            Node<E> before = getNode(index - 1);
            toReturn = before.next.item;
            before.next = before.next.next;
        }
        size--;
        return toReturn;
    }


    // Turns list into a string
    public String toString() {
        Node<E> current = head;
        StringBuilder result = new StringBuilder();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return head.item.toString();

        } else {
            do {
                result.append(current.item);
                result.append(" ==> ");
                current = current.next;
            } while (current != head);
        }
        return result.toString();
    }


    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    // provided code for different assignment
    // you should not have to change this
    // change at your own risk!
    // this class is not static because it needs the class it's inside of to survive!
    private class ListIterator<E> implements Iterator<E> {

        Node<E> nextItem;
        Node<E> prev;
        int index;

        @SuppressWarnings("unchecked")
        //Creates a new iterator that starts at the head of the list
        public ListIterator() {
            nextItem = (Node<E>) head;
            index = 0;
        }

        // returns true if there is a next node
        // this is always should return true if the list has something in it
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return size != 0;
        }

        // advances the iterator to the next item
        // handles wrapping around back to the head automatically for you
        public E next() {
            // TODO Auto-generated method stub
            prev = nextItem;
            nextItem = nextItem.next;
            index = (index + 1) % size;
            return prev.item;

        }

        // removed the last node was visted by the .next() call
        // for example if we had just created a iterator
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        public void remove() {
            int target;
            if (nextItem == head) {
                target = size - 1;
            } else {
                target = index - 1;
                index--;
            }
            CircularLinkedList.this.remove(target); //calls the above class
        }

    }

    // singly linked list
    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }

    }

    static int josephus(int n, int k) {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        System.out.println("\nn = " + n +", k = " +k);
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        int x = 0;
        Iterator<Integer> iter = list.iterator();
        while (list.size > 1) {
            System.out.println(list);
            for (int i = 0; i < k; i++) {
                iter.next();
            }
            iter.remove();
        }
//       alternative method
//        while (iter.hasNext()&& list.head != list.tail) {
//            iter.next();
//            x++;
//            if (x%k==0) {
//                iter.remove();
//            }
//        }
        return list.head.item;
    }

    public static void main(String[] args) {
        CircularLinkedList list = new CircularLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println("Original list: " + list);
        list.remove(0);
        System.out.println("After removing index 0: " + list);
        list.add(3,8);
        System.out.println("After adding 8 at index 3: " + list);
        System.out.println(josephus(5, 2));
        System.out.println(josephus(13, 2));
        System.out.println(josephus(5, 3));

    }
}


