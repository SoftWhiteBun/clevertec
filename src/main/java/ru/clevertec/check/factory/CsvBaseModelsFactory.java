package ru.clevertec.check.factory;

import lombok.Setter;
import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.models.DiscountCard;
import ru.clevertec.check.models.Product;
import ru.clevertec.check.service.CsvDataReader;

import java.util.ArrayList;

@Setter
public class CsvBaseModelsFactory implements IBaseModelsFactory {

    private final String pathToProductFile;
    private final String pathToDiscountCardFile;

    public CsvBaseModelsFactory(String pathToProductFile, String pathToDiscountCardFile) {
        this.pathToProductFile = pathToProductFile;
        this.pathToDiscountCardFile = pathToDiscountCardFile;
    }

    @Override
    public ArrayList<Product> getProducts() throws InternalServerErrorException {
        CsvDataReader reader = new CsvDataReader();
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<ArrayList<String>> data = reader.readAllData(pathToProductFile);
        for (ArrayList<String> line : data) {
            if (data.indexOf(line) == 0) {
                continue;
            }
            Product product = Product.builder()
                    .id(Integer.parseInt(line.get(0)))
                    .description(line.get(1))
                    .price(Double.parseDouble(line.get(2)))
                    .quantity(Integer.parseInt(line.get(3)))
                    .isWholesale("+".equals(line.get(4)))
                    .build();
            products.add(product);
        }
        return products;
    }

    @Override
    public ArrayList<DiscountCard> getDiscountCards() throws InternalServerErrorException {
        CsvDataReader reader = new CsvDataReader();
        ArrayList<DiscountCard> cards = new ArrayList<>();
        ArrayList<ArrayList<String>> data = reader.readAllData(pathToDiscountCardFile);
        for (ArrayList<String> line : data) {
            if (data.indexOf(line) == 0) {
                continue;
            }
            DiscountCard card = DiscountCard.builder()
                    .id(Integer.parseInt(line.get(0)))
                    .number(Integer.parseInt(line.get(1)))
                    .amount(Integer.parseInt(line.get(2)))
                    .build();
            cards.add(card);
        }
        return cards;
    }
}
