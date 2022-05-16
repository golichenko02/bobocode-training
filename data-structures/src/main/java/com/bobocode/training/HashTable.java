package com.bobocode.training;

import java.util.Objects;

/**
 * A simple implementation of the Hash Table that allows storing a generic key-value pair. The table itself is based
 * on the array of {@link Node} objects.
 * <p>
 * An initial array capacity is 16.
 * <p>
 * Every time a number of elements is equal to the array size that tables gets resized
 * (it gets replaced with a new array that it twice bigger than before). E.g. resize operation will replace array
 * of size 16 with a new array of size 32. PLEASE NOTE that all elements should be reinserted to the new table to make
 * sure that they are still accessible  from the outside by the same key.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class HashTable<K, V> {

    private Node<K, V>[] table;
    private final int INITIAL_CAPACITY = 16;
    private final int CAPACITY_EXTENSION_MULTIPLIER = 2;
    private int size = 0;


    @SuppressWarnings("unchecked")
    public HashTable() {
        this.table = new Node[INITIAL_CAPACITY];
    }

    /**
     * Puts a new element to the table by its key. If there is an existing element by such key then it gets replaced
     * with a new one, and the old value is returned from the method. If there is no such key then it gets added and
     * null value is returned.
     *
     * @param key   element key
     * @param value element value
     * @return old value or null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        Node<K, V> newNode = new Node<>(key, value);
        resize();
        int index = calculateIndex(key);
        return put(index, newNode);
    }


    /**
     * Prints a content of the underlying table (array) according to the following format:
     * 0: key1:value1 -> key2:value2
     * 1:
     * 2: key3:value3
     * ...
     */
    public void printTable() {
        for (int index = 0; index < table.length; index++) {
            Node<K, V> currentBucket = table[index];
            System.out.print(index + ": ");
            while (currentBucket != null) {
                System.out.print(currentBucket.key + ":" + currentBucket.value);
                currentBucket = currentBucket.next;
                if (currentBucket != null) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

    private V put(int index, Node<K, V> newNode) {
        if (Objects.isNull(table[index])) {
            table[index] = newNode;
            size++;
            return null;
        }
        Node<K, V> currentNode = table[index];

        if (isRepeatedKey(currentNode.key, newNode.key)) {
            return replaceValue(currentNode, newNode);
        }

        while (currentNode.next != null) {
            if (isRepeatedKey(currentNode.key, newNode.key)) {
                return replaceValue(currentNode, newNode);
            }
            currentNode = currentNode.next;
        }
        currentNode.next = newNode;
        size++;
        return null;
    }

    private V replaceValue(Node<K, V> currentNode, Node<K, V> newNode) {
        V oldValue = currentNode.value;
        currentNode.value = newNode.value;
        return oldValue;
    }

    private boolean isRepeatedKey(K existingKey, K newKey) {
        return existingKey.equals(newKey);
    }

    private void resize() {
        if (this.size == this.table.length) {
            Node<K, V>[] oldTable = this.table;

            @SuppressWarnings("unchecked")
            Node<K, V>[] resizedTable = new Node[this.size * CAPACITY_EXTENSION_MULTIPLIER];
            this.table = resizedTable;
            this.size = 0;
            for (int i = 0; i < oldTable.length; i++) {
                Node<K, V> currentElement = oldTable[i];
                while (currentElement != null) {
                    put(currentElement.key, currentElement.value);
                    currentElement = currentElement.next;
                }
            }
        }
    }

    private int calculateIndex(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }
}