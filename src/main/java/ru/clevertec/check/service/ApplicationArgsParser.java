package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.models.OperationInfo;

import java.util.HashMap;

@AllArgsConstructor
public class ApplicationArgsParser {
    private String[] args;

    public OperationInfo getOperationInfo() throws BadRequestException {
        OperationInfo info = new OperationInfo();
        HashMap<Integer, Integer> productsInfo = new HashMap<>();

        try {
            for (String arg : args) {
                if (arg.contains("discountCard=")) {
                    String[] cardNumber = arg.split("=");
                    info.setCard(Integer.parseInt(cardNumber[1]));
                } else if (arg.contains("balanceDebitCard=")) {
                    String[] balance = arg.split("=");
                    info.setBalance(Double.parseDouble(balance[1]));
                } else {
                    String[] product = arg.split("-");
                    if (productsInfo.containsKey(Integer.parseInt(product[0]))) {
                        productsInfo.compute(Integer.parseInt(product[0]), (k, v) -> v + Integer.parseInt(product[1]));
                    }
                    else {
                        productsInfo.put(Integer.parseInt(product[0]),Integer.parseInt(product[1]));
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new BadRequestException();
        }

        if (productsInfo.isEmpty() || info.getBalance() == null) {
            throw new BadRequestException();
        }

        info.setProductsInfo(productsInfo);

        return info;
    }
}
