package com.lib.util;

import java.util.Objects;

public class HashMap<K, V> implements Map<K, V>{

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private final Entry<K, V> [] hashTable;
    private int capacity;
    private int size;

    public HashMap() {
        capacity = DEFAULT_INITIAL_CAPACITY;
        size = 0;
        hashTable = new Entry[capacity];
    }

    public HashMap(int initialCapacity) {
        if(initialCapacity < 1) {
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        }
        if(initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        capacity = tableSize(initialCapacity);
        size = 0;
        hashTable = new Entry[capacity];
    }

    @Override
    public void put(K key, V value) {
        int hashCode = getHashCode(key);
        Entry<K, V> node = hashTable[hashCode];
        if(node == null) {
            hashTable[hashCode] = new Entry<>(key, value);
            size++;
        } else {
            while(node.next != null)  {
                if(Objects.equals(node.key, key)) {
                    node.value = value;
                    return;
                }
                node = node.next;
            }
            node.next = new Entry<>(key, value);
            size++;
        }
    }

    @Override
    public V get(K key) {
        int hashCode = getHashCode(key);
        Entry<K, V> node = hashTable[hashCode];

        while(node != null && !Objects.equals(node.key, key)) {
            node = node.next;
        }
        if(node == null) return null;
        return node.value;
    }

    @Override
    public V remove(K key) {
        int hashCode = getHashCode(key);
        Entry<K, V> node = hashTable[hashCode];
        Entry<K, V> prevNode = null;

        while(node != null && !Objects.equals(node.key, key)) {
            prevNode = node;
            node = node.next;
        }
        if(node == null) return null;

        if(prevNode == null) {
            hashTable[hashCode] = node.next;
        } else {
            prevNode.next = node.next;
        }
        size--;
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    private int tableSize(int n) {
        n = n -1;
        n = n | n >>> 1;
        n = n | n >>> 2;
        n = n | n >>> 4;
        n = n | n >>> 8;
        n = n | n >>> 16;

        if(n < 0) return 1;
        return Math.min(n +1, MAXIMUM_CAPACITY);
    }

    private int getHashCode(K key) {
        return key.hashCode() % capacity;
    }

    public static class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry (K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

}
