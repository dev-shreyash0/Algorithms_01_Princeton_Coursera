/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node head, end;
    private int size;

    public Deque() {
        head = null;
        end = null;
        size = 0;
    }

    private class FrontToBackIterator implements Iterator<Item> {

        private Node curr = head;

        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (curr == null) throw new NoSuchElementException();
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        if (head == null) end = newNode;
        else head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        if (end == null) head = newNode;
        else end.next = newNode;
        newNode.prev = end;
        end = newNode;
    }

    public Item removeFirst() {
        if (head == null) throw new NoSuchElementException();
        size--;
        Item item = head.item;

        if (head != end) {
            head = head.next;
            head.prev = null;
        }
        else {
            head = null;
            end = null;
        }

        return item;
    }

    public Item removeLast() {
        if (head == null) throw new NoSuchElementException();
        size--;
        Item item = end.item;

        if (head != end) {
            end = end.prev;
            end.next = null;
        }
        else {
            head = null;
            end = null;
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new FrontToBackIterator();
    }

    private static void print(Deque<Integer> queue) {
        for (int i : queue) StdOut.print(i + " ");
        StdOut.println();
    }

    public static void main(String[] args) {

        Deque<Integer> queue = new Deque<>();

        StdOut.println(queue.isEmpty());
        queue.addFirst(1);
        print(queue);
        StdOut.println(queue.isEmpty());
        queue.addLast(2);
        print(queue);
        queue.addFirst(0);
        print(queue);

        Iterator<Integer> iterator = queue.iterator();

        while (iterator.hasNext()) StdOut.print(iterator.next() + " ");

        StdOut.println();
        StdOut.println(queue.removeLast());
        print(queue);
        StdOut.println(queue.removeLast());
        print(queue);
        StdOut.println(queue.removeLast());
        print(queue);
        StdOut.println(queue.size());

        iterator.remove();

    }
}
