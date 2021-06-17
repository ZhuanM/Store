package com.company.enums;

import java.math.BigDecimal;

public enum Category {
    // Default стойности
    NON_EDIBLE(BigDecimal.valueOf(20)),
    EDIBLE(BigDecimal.valueOf(15));

    BigDecimal markupPrice;

    Category(BigDecimal markupPrice) {
        // В случай, че markupPrice е под 0
        if (markupPrice.compareTo(BigDecimal.ZERO) == -1) {
            this.markupPrice = BigDecimal.ZERO;
        }
        else {
            this.markupPrice = markupPrice;
        }
    }

    public void setMarkupPriceForNonEdible(BigDecimal markupPrice) {
        // В случай, че markupPrice е под 0
        if (markupPrice.compareTo(BigDecimal.ZERO) == -1) {
            Category.NON_EDIBLE.markupPrice = BigDecimal.ZERO;
        }
        else {
            Category.NON_EDIBLE.markupPrice = markupPrice;
        }
    }

    public void setMarkupPriceForEdible(BigDecimal markupPrice) {
        // В случай, че markupPrice е под 0
        if (markupPrice.compareTo(BigDecimal.ZERO) == -1) {
            Category.EDIBLE.markupPrice = BigDecimal.ZERO;
        } else {
            Category.EDIBLE.markupPrice = markupPrice;
        }
    }

    public BigDecimal getMarkupPrice() {
        return this.markupPrice;
    }
}