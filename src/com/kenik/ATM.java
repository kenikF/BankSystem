package com.kenik;

import com.kenik.utils.database.If;
import com.kenik.utils.database.Mysql;
import com.kenik.utils.database.Set;
import com.kenik.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class ATM {
    Scanner scanner = new Scanner(System.in);
    If ifs = new If();
    Set set = new Set();
    Utils utils = new Utils();
    Mysql mysql = new Mysql();
    public void printUserMenu(int eIdentifier) throws Exception {
        utils.dash();
        System.out.println("\nДобро пожаловать "+ mysql.getUserName(eIdentifier).substring(0, 1).toUpperCase() + mysql.getUserName(eIdentifier).substring(1) +", что вы желаете сделать? ");
        System.out.println("1) Баланс");
        System.out.println("2) Перевод");
        System.out.println("3) Выход");
        byte choose = scanner.nextByte();

        switch(choose) {
            case 1:
                balance(eIdentifier);
                break;
            case 2:
                transfer(eIdentifier);
                break;
            case 3:
                System.exit(1);
                break;
            default:
                System.out.println("Упс.. Ошибка!");
                utils.dash();
                break;
        }
    }

    public void printAdminMenu(int eIdentifier) throws Exception {
        utils.dash();
        System.out.println("\nДобро пожаловать администратор "+ mysql.getUserName(eIdentifier).substring(0, 1).toUpperCase() + mysql.getUserName(eIdentifier).substring(1) +", что вы желаете сделать? ");
        System.out.println("1) Баланс");
        System.out.println("2) Добавить денег");
        System.out.println("3) Перевод");
        System.out.println("4) Посмотреть все зарегистрированные идентификаторы");
        System.out.println("5) Удалить аккаунт");
        System.out.println("6) Выход");
        byte choose = scanner.nextByte();

        switch(choose) {
            case 1:
                System.out.println("Узнать свой баланс (1)");
                System.out.println("Узнать чужой баланс (2)");
                byte chooseBal = scanner.nextByte();
                if(chooseBal == 1) {
                    balance(eIdentifier);
                } else if(chooseBal == 2) {
                    utils.dash();
                    System.out.print("\nВведите идентификатор человека: ");
                    int id = scanner.nextInt();
                    balance(eIdentifier, id);
                }
                break;
            case 2:
                addBalance(eIdentifier);
                break;
            case 3:
                transfer(eIdentifier);
                break;
            case 4:
                List<Integer> allId = mysql.getAllId();
                allId.forEach((n) -> System.out.println(n));
                System.out.println("Всего зарегистрированных пользователей: "+allId.size());
                printAdminMenu(eIdentifier);
                break;
            case 5:
                deleteAccount(eIdentifier);
                break;
            case 6:
                System.exit(1);
                break;
            default:
                System.out.println("Упс.. Ошибка!");
                utils.dash();
                break;
        }
    }

    private void balance(int eIdentifier, int id) throws Exception {
        utils.dash();
        System.out.println("\nБаланс пользователя "+id+": "+mysql.getBalance(id));
        printAdminMenu(eIdentifier);
    }

    private void addBalance(int eIdentifier) throws Exception {
        utils.dash();
        System.out.println("\nДобавить себе баланс (1)");
        System.out.println("Добавить баланс по идентификатору (2)");
        byte choose = scanner.nextByte();

        switch(choose) {
            case 1:
                System.out.println("Введите сумму добавления: ");
                int sum = scanner.nextInt();
                mysql.addBalance(eIdentifier, sum);
                System.out.println("Вы успешно добавили себе сумму "+sum+". Ваш баланс теперь: "+mysql.getBalance(eIdentifier));
                printAdminMenu(eIdentifier);
                break;
            case 2:
                System.out.print("Введите идентификатор: ");
                int id = scanner.nextInt();
                if(ifs.isIdentifier(id) == 1) {
                    System.out.print("Введите сумму добавления: ");
                    sum = scanner.nextInt();
                    mysql.addBalance(id, sum);
                    System.out.println("Вы успешно добавили идентификатору "+id+" сумму "+sum+". Баланс "+mysql.getUserName(id)+" теперь "+mysql.getBalance(id));
                    printAdminMenu(eIdentifier);
                } else {
                    System.out.println("Такой пользователь не найден.");
                    addBalance(eIdentifier);
                }
                break;
            default:
                System.out.println("Упс.. Ошибка!");
                addBalance(eIdentifier);
                break;
        }
    }

    private void balance(int eIdentifier) throws Exception {
        utils.dash();
        System.out.println("\nВаш баланс: "+mysql.getBalance(eIdentifier));
        if(ifs.isAdmin(eIdentifier) == 1) {
            printAdminMenu(eIdentifier);
        } else {
            printUserMenu(eIdentifier);
        }
    }

    private void transfer(int ID) throws Exception {
        utils.dash();
        System.out.print("\nВведите идентификатор пользователя: ");
        int id = scanner.nextInt();
        if(ifs.isIdentifier(id) == 1) {
            System.out.print("Введите сумму перевода: ");
            int sum = scanner.nextInt();
            if(sum <= 0) {
                System.out.println("Некоректная сумма!");
                transfer(ID);
            } else {
                set.transferOp(id, sum, ID);
                System.out.println("Успешный перевод! Ваш баланс: "+mysql.getBalance(ID));
                if(ifs.isAdmin(ID) == 1) {
                    printAdminMenu(ID);
                } else {
                    printUserMenu(ID);
                }
            }
        } else {
            System.out.println("Такой пользователь не найден.");
            transfer(ID);
        }
    }

    private void deleteAccount(int eIdentifier) throws Exception {
        utils.dash();
        System.out.print("\nВведите идентификатор пользователя: ");
        int id = scanner.nextInt();
        if(ifs.isIdentifier(id) == 1) {
            System.out.print("Вы уверены что хотите удалить аккаунт?: ");
            scanner.nextLine();
            String choose = scanner.nextLine();
            if(choose.equalsIgnoreCase("Да")) {
                if(ifs.isAdmin(id) == 1) {
                    System.out.println("Извините, но аккаунты администрации удалять нельзя!");
                    deleteAccount(eIdentifier);
                } else {
                    System.out.println("Вы успешно удалили аккаунт под именем "+mysql.getUserName(id)+" с идентификатором "+id+'.');
                    mysql.deleteAccount(id);
                    printAdminMenu(eIdentifier);
                }
            } else if(choose.equalsIgnoreCase("Нет")) {
                printAdminMenu(eIdentifier);
            }
        } else {
            System.out.println("Такой пользователь не найден.");
            deleteAccount(eIdentifier);
        }
    }
}
