package ru.clevertec.check.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class ReceiptItem extends Product{
    private double discount;
    private double total;

    public ReceiptItem(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.isWholesale = product.isWholesale();
        this.quantity = 0;
        this.total = 0;
        this.discount = 0;
    }

    public void countTotal() {
        this.total = this.quantity * this.price;
    }

    public void countDiscount(int per) {
        if (this.isWholesale && this.quantity > 5) {
            this.discount = total * 0.9;
            return;
        }
        this.discount = total/100*per;
    }
}
