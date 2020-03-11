package com.runx.framework.bean;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.runx.framework.contants.Constants.ROOT_PATH;

/** 该类已经过时了，请参考 ClassPathResourceScanner
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Deprecated
public class ClassPathScanner {

    private List<String> resources = new ArrayList<>();

    private ClassLoader loader;

    public List<String> getResources() {
        return resources;
    }

    public ClassPathScanner() {
        this.loader = Thread.currentThread().getContextClassLoader();
    }

    private String resolvePath(String basePackage){
        return basePackage.replace(".",ROOT_PATH);
    }

    public void scan(String packageName) throws IOException {
        String basePath = resolvePath(packageName);
        Enumeration<URL> sets = loader.getResources(basePath);
        while (sets.hasMoreElements()) {
            URL url = sets.nextElement();
            if ("file".equals(url.getProtocol())) {
                // 文件类型
                scanFromDir(url.getPath(),packageName);

            }else if ("jar".equals(url.getProtocol())) {
                // fixme jar
            }
        }
    }

    private void scanFromDir(String path, String packageName) throws IOException {
        readFile(path,packageName);
    }

    private void readFile(String path,String basePackage) {
        File root = new File(path);
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                readFile(f.getPath(),basePackage + "." + f.getName());
            }else {
                resources.add(basePackage + "." + f.getName().replace(".class",""));
            }
        }
    }

    public Class<?> loadClass(String className) {
        try {
            return loader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
