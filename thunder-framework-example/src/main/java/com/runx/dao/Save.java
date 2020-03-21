package com.runx.dao;

import sun.tools.jconsole.Tab;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Save extends AbstractSqlBuilder {

    private static final String template = "insert into %s (%s) values (%s)";

    public <T> void save(T entity) {
        if (cache.get("user#save") != null) {
            System.out.println(cache.get("user#save"));
            return;
        }
        boolean present = entity.getClass().isAnnotationPresent(Table.class);
        if (present) {
            Map<String, Object> entityMap = new LinkedHashMap<>();
            Table table = entity.getClass().getDeclaredAnnotation(Table.class);
            Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object value = field.get(entity);
                    entityMap.putIfAbsent(fieldName, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            StringBuilder fb = new StringBuilder();
            StringBuilder vb = new StringBuilder();
            for (String key : entityMap.keySet()) {
                fb.append("`").append(key).append("`").append(',');
                vb.append("?").append(",");
            }
            String fbSql = fb.deleteCharAt(fb.lastIndexOf(",")).toString();
            String vbSql = vb.deleteCharAt(vb.lastIndexOf(",")).toString();
            cache.put("user#save", String.format(template, table.value(), fbSql, vbSql));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            new Save().save(user);
        }
    }

}
