package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    // Например - домат (за да reference-ва самия обект домат) и после лист с всички домати на разположение
    private Map<Goods, ArrayList<Goods>> goods;
    private Map<Goods, ArrayList<Goods>> soldGoods;
    private int daysBeforeExpiry;
    private BigDecimal percentForSale;
    private BigDecimal expenseForDeliveredGoods;
    private List<Employee> employees;
    private List<CashRegistry> cashRegistries;
    private BigDecimal moneyMade;

    public Store(int daysBeforeExpiry, BigDecimal percentForSale) {
        this.goods = new HashMap<>();
        this.soldGoods = new HashMap<>();
        this.daysBeforeExpiry = daysBeforeExpiry;
        this.percentForSale = percentForSale;
        this.expenseForDeliveredGoods = BigDecimal.ZERO;
        this.employees = new ArrayList<>();
        this.cashRegistries = new ArrayList<>();
        this.moneyMade = BigDecimal.ZERO;
    }

    public Map<Goods, ArrayList<Goods>> getGoods() {
        return this.goods;
    }

    public Map<Goods, ArrayList<Goods>> getSoldGoods() {
        return this.soldGoods;
    }

    public BigDecimal getPriceForDeliveredGoods() {
        return expenseForDeliveredGoods;
    }

    public int getDaysBeforeExpiry() {
        return this.daysBeforeExpiry;
    }

    public BigDecimal getPercentForSale() {
        return this.percentForSale;
    }

    public BigDecimal getMoneyMade() {
        return this.moneyMade;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public List<CashRegistry> getCashRegistries() {
        return this.cashRegistries;
    }

    public void addEmployee(Employee employee) {
        if (!this.employees.contains(employee)) {
            this.employees.add(employee);
        }
    }

    public void addCashRegistry(CashRegistry cashRegistry) {
        this.cashRegistries.add(cashRegistry);
    }

    public BigDecimal allExpenses() {
        BigDecimal allExpenses = this.expenseForDeliveredGoods;

        for (Employee employee : this.employees) {
            allExpenses = allExpenses.add(employee.getSalary());
        }

        return allExpenses;
    }

    public BigDecimal allMoneyMade() {
        BigDecimal moneyMade = BigDecimal.ZERO;

        for (CashRegistry cashRegistry : this.cashRegistries) {
            moneyMade = moneyMade.add(cashRegistry.getTotalMoney());
        }

        return moneyMade;
    }

    public BigDecimal allProfit() {
        return this.allMoneyMade().subtract(this.allExpenses());
    }

    public void addGoods(Goods goods, int quantity) {
        for (int i = 0; i < quantity; i++) {
            // Проверка за годност на продукта
            if (goods.getDateOfExpiry().compareTo(LocalDate.now()) > -1) {
                if (this.goods.get(goods) == null) {
                    ArrayList<Goods> goodsList = new ArrayList<>();

                    goodsList.add(goods);

                    this.goods.put(goods, goodsList);
                }
                else {
                    this.goods.get(goods).add(goods);
                }

                this.expenseForDeliveredGoods = this.expenseForDeliveredGoods.add(goods.getPrice());
            }
        }
    }

    public void addSoldGoods(Goods goods) {
        if (this.soldGoods.get(goods) == null) {
            ArrayList<Goods> soldGoodsList = new ArrayList<>();

            soldGoodsList.add(goods);

            this.soldGoods.put(goods, soldGoodsList);
        }
        else {
            this.soldGoods.get(goods).add(goods);
        }
    }

    @Override
    public String toString() {
        return "Store [goods: " + this.goods + ", daysBeforeExpiryCheck: " + this.daysBeforeExpiry + ", closeToExpirePercent: " + this.percentForSale
                + ", priceForDeliveredGoods: " + this.expenseForDeliveredGoods + ", employees: " + this.employees + "]";
    }
}
