package com.company;

import com.company.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Goods {
    private final String id;
    private final String name;
    // доставна цена
    private BigDecimal price;
    private final Category category;
    private final LocalDate dateOfExpiry;

    public Goods(String id, String name, BigDecimal price, Category category, LocalDate dateOfExpiry) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.dateOfExpiry = dateOfExpiry;
    }

    public LocalDate getDateOfExpiry() {
        return this.dateOfExpiry;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Category getCategory() {
        return this.category;
    }

    public BigDecimal getFinalPrice(int daysBeforeExpiry, BigDecimal percent) {
        BigDecimal price = this.price;

        // Прилагаме markupPrice
        price = price.add(price.multiply(this.category.getMarkupPrice().divide(BigDecimal.valueOf(100))));

        // Намаляме цената на база срок за годност
        if (LocalDate.now().isBefore(this.dateOfExpiry)
                && LocalDate.now().getMonth() == this.dateOfExpiry.getMonth()
                && LocalDate.now().getDayOfMonth() + daysBeforeExpiry >= this.dateOfExpiry.getDayOfMonth()) {
            price = price.subtract(price.multiply(percent.divide(BigDecimal.valueOf(100))));
        }

        return price;
    }

    @Override
    public String toString() {
        return "Goods [id: " + this.id + ", name: " + this.name + ", price: " + this.price + ", category: " + this.category + ", dateOfExpiry: " + this.dateOfExpiry + "]";
    }
}
