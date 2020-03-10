package com.runx.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.runx.framework.contants.Constants.*;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public class ClassPathResourceScanner implements Scan  {


    @Override
    public List<String> scan(String basePackage, ClassLoader classLoader) {
        List<String> classResource = new ArrayList<>();
        String basePath = resolvePath(basePackage);
        Enumeration<URL> sets = null;
        try {
            sets = classLoader.getResources(basePath);
            while (sets.hasMoreElements()) {
                URL url = sets.nextElement();
                if (FILE_PROTOCOL.equals(url.getProtocol())) {
                    // 文件类型
                    scanFromDir(url.getPath(),basePackage,classResource);
                }else if (JAR.equals(url.getProtocol())) {
                    // fixme jar
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classResource;
    }

    private void scanFromDir(String path, String basePackage,List<String> resources) {
        readFile(path,basePackage,resources);
    }

    private void readFile(String path,String basePackage,List<String> resources) {
        File root = new File(path);
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                readFile(f.getPath(),basePackage + "." + f.getName(),resources);
            }else {
                resources.add(basePackage + "." + f.getName().replace(".class",""));
            }
        }
    }

    private String resolvePath(String basePackage){
        return basePackage.replace(".",ROOT_PATH);
    }
}
