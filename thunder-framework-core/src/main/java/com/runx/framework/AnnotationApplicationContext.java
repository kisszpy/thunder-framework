package com.runx.framework;

import com.runx.framework.core.ClassPathResourceScanner;
import com.runx.framework.core.Scan;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private Scan scan;

    public AnnotationApplicationContext() {
        this.scan = new ClassPathResourceScanner();
    }

    @Override
    protected void setScanner(Scan scanner) {
        this.scan = scan;
    }

    @Override
    protected Scan getScanner() {
        return scan;
    }
}
