package com.kenik.utils;

public class Utils {
    public void dash() throws Exception {
        for(int i=0; i<25; i++) {
            System.out.print("-");
            Thread.sleep(50);
        }
    }

    public void dashWithSpace() throws Exception {
        for(int i=0; i<25; i++) {
            System.out.print("-");
            Thread.sleep(50);
        }
        System.out.print('\n');
    }
}
