package com.kenik;

import com.kenik.utils.Utils;

public class Main {
    public static void main(String[] args) throws Exception {
        Account account = new Account();
        Utils utils = new Utils();
        System.out.println("Добро пожаловать в банк!");
        utils.dashWithSpace();
        account.userID();
    }
}
