package ru.clevertec.check.repository;

import lombok.Setter;
import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.models.DiscountCard;
import ru.clevertec.check.models.OperationInfo;
import ru.clevertec.check.models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Setter
public class ModelsRepositoryImpl implements IModelsRepository {

    private static final String PRODUCT_QUERY = "SELECT id, description, price, quantity_in_stock, wholesale_product FROM product";
    private static final String DISCOUNT_CARD_QUERY = "SELECT id, number, amount FROM discount_card";

    private final String url;
    private final String username;
    private final String password;

    public ModelsRepositoryImpl(OperationInfo info) {
        url = info.getUrl();
        username = info.getUsername();
        password = info.getPassword();
    }

    public List<Product> getProducts() throws InternalServerErrorException {
        List<Product> products = new ArrayList<>();

        try (Connection connection = JdbcConnection.getConnection(url, username, password); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(PRODUCT_QUERY);
            while (rs.next()) {
                products.add(
                        Product.builder()
                                .id(rs.getInt(1))
                                .description(rs.getString(2))
                                .price(rs.getDouble(3))
                                .quantity(rs.getInt(4))
                                .isWholesale(rs.getBoolean(5))
                                .build()
                );
            }
            return products;
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public List<DiscountCard> getDiscountCards() throws InternalServerErrorException {
        List<DiscountCard> discountCards = new ArrayList<>();
        try (Connection connection = JdbcConnection.getConnection(url, username, password); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(DISCOUNT_CARD_QUERY);
            while (rs.next()) {
                discountCards.add(DiscountCard.builder()
                        .id(rs.getInt(1))
                        .number(rs.getInt(2))
                        .amount(rs.getInt(3))
                        .build());
            }
            return discountCards;
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
