/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAP = 8;

    private Item[] q;
    private int first, last, n;

    public RandomizedQueue() {
        q = (Item[]) new Object[DEFAULT_CAP];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(2 * q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;
    }

    private Item dequeue1() {
        last = Math.floorMod(last - 1, q.length);
        Item item = q[last];
        q[last] = null;
        return item;
    }

    public Item dequeue() {

        if (n == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniformInt(n);
        rand = (rand + first) % n;
        Item item = q[rand];

        if (rand == Math.floorMod(last - 1, q.length)) {
            q[rand] = null;
            last = Math.floorMod(last - 1, q.length);
        }
        else {
            q[rand] = dequeue1();
        }

        n--;
        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;

    }

    public Item sample() {
        if (n == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniformInt(n);
        rand = (rand + first) % q.length;
        Item item = q[rand];
        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {

        private int[] arr = shuffled();

        private int i = 0;

        private int[] shuffled() {
            int[] rand = new int[n];
            for (int j = 0; j < n; j++) rand[j] = j;
            StdRandom.shuffle(rand);
            return rand;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(first + arr[i]) % q.length];
            i++;
            return item;
        }

    }

    private void resize(int cap) {
        assert n <= cap;
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < n; i++) copy[i] = q[(first + i) % q.length];
        first = 0;
        last = n;
        q = copy;
    }

    private static void print(RandomizedQueue<Integer> queue) {
        for (int a : queue) StdOut.print(a + " ");
        StdOut.println();
    }

    public static void main(String[] args) {

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        print(queue);

        StdOut.println(queue.dequeue());

        print(queue);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.println(queue.sample());

        print(queue);

        Iterator<Integer> iterator = queue.iterator();

        while (iterator.hasNext()) StdOut.print(iterator.next() + " ");

        iterator.remove();

    }

}
