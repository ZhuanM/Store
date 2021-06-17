package com.company;

import com.company.enums.Category;
import com.company.storeExceptions.InsufficientGoodsException;
import com.company.storeExceptions.InsufficientMoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Goods goods1 = new Goods("123", "Carrot", BigDecimal.valueOf(14.25), Category.EDIBLE, LocalDate.of(2023, 5, 17));

        Goods goods2 = new Goods("124", "Carrot", BigDecimal.valueOf(9.3), Category.EDIBLE, LocalDate.of(2023, 6, 13));

        Goods goods3 = new Goods("456", "Cucumber", BigDecimal.valueOf(1.5), Category.EDIBLE, LocalDate.of(2022, 10, 10));

        Goods goods4 = new Goods("754", "Table", BigDecimal.valueOf(75.65), Category.NON_EDIBLE, LocalDate.of(2025, 11, 19));

        Employee employee1 = new Employee("564", "Nikola", BigDecimal.valueOf(700));

        CashRegistry cashRegistry1 = new CashRegistry(employee1);

        Store store = new Store(15, BigDecimal.valueOf(3));

        store.addGoods(goods1, 50);
        store.addGoods(goods2, 65);
        store.addGoods(goods3, 75);
        store.addGoods(goods4, 150);

        store.addEmployee(employee1);

        store.addCashRegistry(cashRegistry1);

        Map<Goods, Integer> shoppingCart = new HashMap<>();
        shoppingCart.put(goods1, 2);
        shoppingCart.put(goods2, 5);
        shoppingCart.put(goods3, 3);
        shoppingCart.put(goods4, 1);

        try {
            Receipt receipt = cashRegistry1.sell(store, BigDecimal.valueOf(1500.50), shoppingCart);
            receipt.createReceipt();

            Receipt receipt2 = cashRegistry1.sell(store, BigDecimal.valueOf(1500.50), shoppingCart);
            receipt2.createReceipt();

            Receipt receipt3 = cashRegistry1.sell(store, BigDecimal.valueOf(1500.50), shoppingCart);
            receipt3.createReceipt();
        } catch (InsufficientGoodsException e) {
            e.printStackTrace();
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
        }

        System.out.println(store.toString()+ "\n");

        System.out.println(cashRegistry1.toString()+ "\n");

        System.out.println(employee1.toString()+ "\n");

        System.out.println("Expenses: " + store.allExpenses() + "\n");

        System.out.println("MoneyMade: " + store.allMoneyMade()+ "\n");

        System.out.println("Profit: " + store.allProfit()+ "\n");
    }
}
