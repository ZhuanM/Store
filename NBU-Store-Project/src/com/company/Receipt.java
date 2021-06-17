package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Receipt {
    private final String id;
    // Също може да се използва за проверка на бройката на касовите бележки
    private static int idCounter = 1;

    private LocalDate date;
    private Employee employee;
    // Goods -> ArrayList има само 2 стойности - Цена, Бройка (в ред)
    private Map<Goods, ArrayList<BigDecimal>> markedGoods;
    private BigDecimal totalMoney;

    public Receipt(LocalDate date, Employee employee, Map<Goods, ArrayList<BigDecimal>> markedGoods, BigDecimal totalMoney) {
        // '"" +' защото idCounter е integer и ни трябва String като резултат
        this.id = "" + Receipt.idCounter++;

        this.date = date;
        this.employee = employee;
        this.markedGoods = new HashMap<>();
        // Пълним map-а
        markedGoods.forEach((key, value) -> this.markedGoods.put(key, value));

        this.totalMoney = totalMoney;
    }

    public Receipt() {
        this.id = "-1";
    }

    // Използва се за вземане на бройката на касови бележки в магазина
    public static int getIdCounter() {
        return Receipt.idCounter;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    // За да принтираме правилно Receipt пълним с String Name-а на Goods, за да не се принтират всички данни на съответния Goods toString()
    public Map<String, ArrayList<BigDecimal>> getGoods() {
        Map<String, ArrayList<BigDecimal>> goods = new HashMap<>();

        for (Map.Entry<Goods, ArrayList<BigDecimal>> entry : this.markedGoods.entrySet()) {
            goods.put(entry.getKey().getName(), entry.getValue());
        }

        return goods;
    }

    public String getId() {
        return this.id;
    }

    public void createReceipt() {
        String fileName = "Receipt_" + this.id + ".txt";

        try (FileWriter fw = new FileWriter("./Receipts/" + fileName, false)) {
            fw.write(this.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printReceipt(String fileName) {
        fileName += ".txt";

        try {
            File file = new File("./Receipts/" + fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String info = scanner.nextLine();
                System.out.println(info);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Receipt [id: " + this.id + ", employee: " + this.employee + ", date: " + this.date + ", markedGoods: " + this.getGoods() + ", totalMoney: " + this.totalMoney + "]";
    }
}
