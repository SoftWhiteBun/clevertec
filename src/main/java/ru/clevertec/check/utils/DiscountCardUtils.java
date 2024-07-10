package ru.clevertec.check.utils;

import ru.clevertec.check.models.DiscountCard;

import java.util.List;

public class DiscountCardUtils {

    public static DiscountCard findByCardNumber(List<DiscountCard> cards, int number) {
        for (DiscountCard card : cards) {
            if (card.getNumber() == number) {
                return card;
            }
        }
        DiscountCard card = new DiscountCard();
        card.setAmount(2);
        return card;
    }
}
