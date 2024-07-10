package ru.clevertec.check.repository;

import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.models.DiscountCard;
import ru.clevertec.check.models.Product;

import java.sql.SQLException;
import java.util.List;

public interface IModelsRepository {
    List<Product> getProducts() throws InternalServerErrorException, SQLException;
    List<DiscountCard> getDiscountCards() throws InternalServerErrorException, SQLException;
}
