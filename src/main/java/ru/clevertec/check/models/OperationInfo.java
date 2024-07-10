package ru.clevertec.check.models;

import lombok.Data;

import java.util.HashMap;

@Data
public class OperationInfo {

    HashMap<Integer, Integer> productsInfo;
    Integer card;
    Double balance;
    String pathToFile;

    public OperationInfo() {
    }
}
