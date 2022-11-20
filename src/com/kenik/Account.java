package com.kenik;

import com.kenik.utils.database.If;
import com.kenik.utils.database.Mysql;
import com.kenik.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Account {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    Mysql mysql = new Mysql();
    If ifs = new If();
    ATM atm = new ATM();
    Utils utils = new Utils();

    private int eIdentifier;
    private String username;
    private int balance;
    public void registration() throws Exception {
        utils.dash();
        System.out.print("\nВведите своё имя: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Введите свой возраст: ");
        int age = scanner.nextInt();
        if(age >= 18) {
            System.out.print("Введите PIN-код: ");
            int pin = scanner.nextInt();

            int userID = random.nextInt(99999999);
            if(ifs.isIdentifier(userID) == 1) {
                while(ifs.isIdentifier(userID) == 1) {
                    userID = random.nextInt(99999999);
                }
            }

            Connection connection = mysql.setConnection();
            try(PreparedStatement ps = connection.prepareStatement("INSERT INTO accounts (name, age, userID, pinCode, balance) VALUES (?, ?, ?, ?, 0)")) {
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setInt(3, userID);
                ps.setInt(4, pin);
                int rs = ps.executeUpdate();
                System.out.println("Вы успешно зарегистрировались! Ваш идентификатор: "+userID);
                utils.dashWithSpace();
                userID();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Вам нельзя зарегистрировать аккаунт!");
        }
    }

    public void userID() throws Exception {
        System.out.print("Введите идентификатор пользователя: ");
        eIdentifier = scanner.nextInt();
        int returned = ifs.isIdentifier(eIdentifier);
        if(returned == 1) {
            username = mysql.getUserName(eIdentifier);
            pinCode();
        } else {
            System.out.print("К сожалению такого пользователя не существует. Если вы хотите зарегистрироваться, введите 1, если вы ошиблись, введите 2: ");
            byte choose = scanner.nextByte();
            switch(choose) {
                case 1:
                    registration();
                    break;
                case 2:
                    utils.dashWithSpace();
                    userID();
                    break;
                default:
                    System.out.println("Упс.. Ошибка!");
                    break;

            }
        }
    }

    public void pinCode() throws Exception {
        System.out.print("Введите ваш PIN-код: ");
        int ePIN = scanner.nextInt();
        if(ifs.isPIN(ePIN) == 1) {
            if(ifs.isAdmin(eIdentifier) == 1) {
                atm.printAdminMenu(eIdentifier);
            } else {
                atm.printUserMenu(eIdentifier);
            }
        }
    }
}
