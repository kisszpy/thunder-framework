package com.runx;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author: kisszpy
 * @date: 2020/3/17
 */
@Slf4j
public class MyArray<E> implements List<E> {

    private E[] data;

    private int size;

    public MyArray(int capacity) {
        this.data = (E[]) new Object[capacity];
        this.size = 0;
    }

    public MyArray() {
        this(16);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkArguments(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("参数非法");
        }
    }

    @Override
    public void add(int index, E e) {
        checkArguments(index);
        if (size >= data.length) {
            // scala capacity
            grow(data.length << 1);
        }
        for (int i = size - 1; i >= index; i--) {
            // move as right
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    private void grow(int newCapacity) {
        if (log.isDebugEnabled()) {
            log.debug("grow array space");
        }
        E[] newData = Arrays.copyOf(data, newCapacity);
        data = newData;
    }

    @Override
    public void addFirst(E e) {
        add(0, e);
    }

    @Override
    public void addLast(E e) {
        add(size, e);
    }

    @Override
    public void remove(E e) {
        int index = findIndex(e);
        if (index != -1) {
            for (int i = index; i < size; i++) {
                // move as left
                data[i] = data[i + 1];
            }
            size--;
            if (size < data.length / 4) {
                // 缩容操作
                grow(data.length >> 2);
            }
        }
    }

    private int findIndex(E e) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public E get(int index) {
        checkArguments(index);
        return data[index];
    }

    @Override
    public void set(int index, E e) {
        checkArguments(index);
        data[index] = e;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                builder.append(data[i]);
            } else {
                builder.append(data[i]).append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
