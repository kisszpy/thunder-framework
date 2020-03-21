package com.runx.pattern;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {

    private List<Filter> list = new ArrayList<>();

    private int index = -1;

    public FilterChain add(Filter filter) {
        list.add(filter);
        return this;
    }

    public List<Filter> getChain() {
        return list;
    }

    @Override
    public void doFilter(RequestMessage message, FilterChain chain) {
        if (index == list.size()) {
            return;
        }
        index++;
        if (index <= list.size() -1 ) {
            list.get(index).doFilter(message, chain);
        }
    }
}
