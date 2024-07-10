package ru.clevertec.check.models;

import lombok.Data;

import java.util.HashMap;

@Data
public class OperationInfo {

    HashMap<Integer, Integer> productsInfo;
    Integer card;
    Double balance;
    String url;
    String username;
    String password;

    public OperationInfo() {
    }
}
