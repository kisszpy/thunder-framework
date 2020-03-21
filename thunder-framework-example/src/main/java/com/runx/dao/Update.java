package com.runx.dao;

public class Update extends AbstractSqlBuilder {

    private static final String template = "update %s %s where %s = ?";

    public <T> void update(T entity){

    }
}
