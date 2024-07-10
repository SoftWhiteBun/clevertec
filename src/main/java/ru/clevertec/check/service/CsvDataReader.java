package ru.clevertec.check.service;

import ru.clevertec.check.exception.InternalServerErrorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvDataReader {

    public ArrayList<ArrayList<String>> readAllData(String pathToCsv) throws InternalServerErrorException {

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToCsv))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<String> fields = new ArrayList<String>(Arrays.asList(line.split(";")));
                data.add(fields);
            }
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
        return data;
    }
}
