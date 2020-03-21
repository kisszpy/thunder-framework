package com.runx.pattern;

public interface Filter {
    void doFilter(RequestMessage message,FilterChain chain);
}
