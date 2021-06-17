package com.company;

import com.company.storeExceptions.CashRegistryException;
import com.company.storeExceptions.InsufficientGoodsException;
import com.company.storeExceptions.InsufficientMoneyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class CashRegistry {
    private Employee employee;
    // Goods -> ArrayList има само 2 стойности - Цена, Бройка (в ред)
    private Map<Goods, ArrayList<BigDecimal>> markedGoods;
    private BigDecimal totalMoney;

    public CashRegistry(Employee employee) {
        this.employee = employee;
        this.markedGoods = new HashMap<>();

        // Default стойност на totalMoney
        this.totalMoney = BigDecimal.ZERO;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public Map<Goods, ArrayList<BigDecimal>> getMarkedGoods() {
        return this.markedGoods;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    public void setEmployee(Employee employee) throws CashRegistryException {
        if (this.employee == null) {
            this.employee = employee;
        }
        else {
            throw new CashRegistryException();
        }
    }

    public void removeEmployee() {
        this.employee = null;
    }

    public void clearMarkedGoods() {
        this.markedGoods.clear();
    }

    public Receipt sell(Store store, BigDecimal buyersMoney, Map<Goods, Integer> buyersGoods) throws InsufficientGoodsException, InsufficientMoneyException {
        for (Map.Entry<Goods, Integer> entry : buyersGoods.entrySet()) {
            // Ако магазина го има и има правилната бройка да влезнем
            if (store.getGoods().containsKey(entry.getKey()) && store.getGoods().get(entry.getKey()).size() >= entry.getValue()) {
                // Пълним map-а
                this.markedGoods.put(
                        // goods
                        entry.getKey(),
                        new ArrayList<>(Arrays.asList(
                                // цената
                                entry.getKey().getFinalPrice(store.getDaysBeforeExpiry(), store.getPercentForSale())
                                // бройката
                                , BigDecimal.valueOf(entry.getValue()))));

                // Махаме купените продукти от магазина
                for (int i = 0; i < entry.getValue(); i++) {
                    // Добавяме в продадени продукти
                    store.addSoldGoods(store.getGoods().get(entry.getKey()).get(0));

                    // Премахме от продуктите от магазина
                    store.getGoods().get(entry.getKey()).remove(0);
                }
            }
            else {
                if (store.getGoods().get(entry.getKey()) == null) {
                    throw new InsufficientGoodsException("Insufficient " + entry.getKey().getName() + " in store." + entry.getValue() + " less available.");
                }
                throw new InsufficientGoodsException("Insufficient " + entry.getKey().getName() + " in store." + (entry.getValue() - store.getGoods().get(entry.getKey()).size()) + " less available.");
            }
        }

        // Пълним totalForReceipt с цените на продуктите
        BigDecimal totalForReceipt = BigDecimal.ZERO;
        for (Map.Entry<Goods, ArrayList<BigDecimal>> entry : this.markedGoods.entrySet()) {
            totalForReceipt = totalForReceipt.add(entry.getValue().get(0).multiply(entry.getValue().get(1)));
        }

        // Закръгляме
        totalForReceipt = totalForReceipt.setScale(2, RoundingMode.HALF_EVEN);

        // Проверка за парите на купувача
        if (buyersMoney.compareTo(totalForReceipt) > -1) {
            this.totalMoney = this.totalMoney.add(totalForReceipt);

            Receipt receipt = new Receipt(LocalDate.now(), this.getEmployee(), this.markedGoods, totalForReceipt);

            // Зачистваме маркираните продукти
            this.clearMarkedGoods();

            return receipt;
        }
        else {
            // Зачистваме маркираните продукти
            this.clearMarkedGoods();

            throw new InsufficientMoneyException("Insufficient money. Required " + totalForReceipt.subtract(buyersMoney) + " more for order.");
        }
    }

    @Override
    public String toString() {
        return "CashRegistry [employee: " + this.employee + ", markedGoods: " + this.markedGoods + ", totalMoney: " + this.totalMoney + "]";
    }
}
