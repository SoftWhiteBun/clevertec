package ru.clevertec.check.models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class Receipt {

    private final LocalDateTime dateTime;
    private final ArrayList<ReceiptItem> items;
    private final DiscountCard card;
    private final double total;
    private final double discount;

    public Receipt(ArrayList<ReceiptItem> items, DiscountCard card) {
        this.dateTime = LocalDateTime.now();
        this.items = items;
        this.card = card;
        this.total = countTotal();
        this.discount = countDiscount();
    }

    private double countTotal() {
        double total = 0;
        for (ReceiptItem item : this.items) {
            total += item.getTotal();
        }
        return total;
    }

    private double countDiscount() {
        double discount = 0;
        for (ReceiptItem item : this.items) {
            discount += item.getDiscount();
        }
        return discount;
    }
}
