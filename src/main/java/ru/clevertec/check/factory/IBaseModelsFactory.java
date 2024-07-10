package ru.clevertec.check.factory;

import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.models.DiscountCard;
import ru.clevertec.check.models.Product;

import java.util.ArrayList;

public interface IBaseModelsFactory {
    ArrayList<Product> getProducts() throws InternalServerErrorException;
    ArrayList<DiscountCard> getDiscountCards() throws InternalServerErrorException;
}
