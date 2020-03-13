package com.runx.example.service;

import com.runx.framework.aop.CglibProxy;

/**
 * @author: kisszpy
 * @date: 2020/3/13
 */

public class OkService {

    private StockService stockService = StockService.class.newInstance();

    public OkService() throws IllegalAccessException, InstantiationException {
    }

    public void ok() {
        stockService.eat();
        System.out.println("ok.....");
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        OkService okService = new OkService();
        OkService proxyService = (OkService) new CglibProxy(okService).getInstance();
        proxyService.ok();
        System.out.println(proxyService);

    }

}
