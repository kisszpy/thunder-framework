package com.runx.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HtmlFilter implements Filter {

    @Override
    public void doFilter(RequestMessage message,FilterChain chain) {
        log.info("html filter process");
        message.setMessage(message.getMessage().replace("<","小于号"));
        chain.doFilter(message,chain);
    }
}
