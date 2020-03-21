package com.runx.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter implements Filter {
    @Override
    public void doFilter(RequestMessage message,FilterChain chain) {
        log.info("auth filter process");
        if (message.getUid() == null) {
            throw new RuntimeException("error, auth fail");
        }
        chain.doFilter(message,chain);
    }
}
