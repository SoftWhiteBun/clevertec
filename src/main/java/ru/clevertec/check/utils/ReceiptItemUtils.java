package ru.clevertec.check.utils;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.models.Product;
import ru.clevertec.check.models.ReceiptItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptItemUtils {

    public static ArrayList<ReceiptItem> getReceiptItems(ArrayList<Product> products,
                                                         HashMap<Integer, Integer> productsInfo,
                                                         int cardDiscount) throws BadRequestException {
        ArrayList<ReceiptItem> items = new ArrayList<>();
        for (Integer id : productsInfo.keySet()) {
            Product product = findById(products, id);
            if (productsInfo.get(id) <= product.getQuantity()) {
                ReceiptItem item = new ReceiptItem(product);
                item.setQuantity(productsInfo.get(id));
                item.countTotal();
                item.countDiscount(cardDiscount);
                items.add(item);
            }
            else {
                throw new BadRequestException();
            }
        }
        return items;
    }

    private static Product findById(ArrayList<Product> products, int id) throws BadRequestException {
        for (Product elem : products) {
            if (elem.getId() == id) {
                return elem;
            }
        }
        System.out.println("Product with id " + id + " not found");
        throw new BadRequestException();
    }
}
