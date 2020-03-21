package com.runx.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSqlBuilder {

    protected static final Map<String, String> cache = new ConcurrentHashMap<>();

}
