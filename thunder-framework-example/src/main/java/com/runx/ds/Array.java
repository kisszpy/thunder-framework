package com.runx.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: kisszpy
 * @date: 2020/3/14
 */
@Slf4j
public class Array<T> {

    private static final int DEFAULT_CAPACITY = 16;

    private T[] data;

    private int size;

    public Array() {
        // default capacity is 16
        this(DEFAULT_CAPACITY);
    }

    public Array(int capacity) {
        data = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * add to head
     * @param e
     */
    public void addFirst(T e) {
        add(0,e);
    }

    /**
     * add to tail
     * @param e
     */
    public void addLast(T e) {
        add(size,e);
    }

    /**
     * [1,2,3]
     * size = 3
     * index = size - 1 , index >= targetIndex , index --
     * [1,size-1]
     * data[i+1] = data[i] move data
     * data[targetIndex] = element
     * size++
     *
     * @param index
     * @param e
     */
    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("非法参数");
        }
        if (size == data.length) {
            log.info("grow capacity for add ");
            grow(size << 1);
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    /**
     * origin data is:  [1,2,3,4,5]
     * remove 3; [1,2,4,5]
     * move element [3,size-1]
     * @param index
     */
    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("参数非法");
        }
        for (int i = index; i < size; i++) {
            data[i] = data[i+1];
        }
        size--;
        if (size < data.length / 4 && data.length > DEFAULT_CAPACITY) {
            // 触发缩容操作
            log.info("grow capacity for remove ");
             grow(data.length >> 2);
        }
    }

    private void grow(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    public T get(int index) {
        return data[index];
    }

    public void set(int index,T e) {
        data[index] = e;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        Arrays.stream(data)
                .filter(x->x!=null)
                .forEach(item -> build.append(item).append(" "));
        return build.toString();
    }
}
