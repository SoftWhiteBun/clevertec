package ru.clevertec.check;

import ru.clevertec.check.exception.CustomException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.factory.CsvBaseModelsFactory;
import ru.clevertec.check.utils.DiscountCardUtils;
import ru.clevertec.check.utils.ReceiptItemUtils;
import ru.clevertec.check.models.*;
import ru.clevertec.check.service.ApplicationArgsParser;
import ru.clevertec.check.service.CsvReceiptWriter;

import java.util.ArrayList;
import java.util.Optional;


public class CheckRunner {

    private static final String DISCOUNT_CARDS_CSV = "src/main/resources/discountCards.csv";

    public static void main(String[] args) {

        ApplicationArgsParser parser = new ApplicationArgsParser(args);
        CsvReceiptWriter writer = new CsvReceiptWriter();

        try {
            OperationInfo info = parser.getOperationInfo();

            CsvBaseModelsFactory factory = new CsvBaseModelsFactory(info.getPathToFile(), DISCOUNT_CARDS_CSV);
            ArrayList<Product> products = factory.getProducts();
            ArrayList<DiscountCard> cards = factory.getDiscountCards();

            DiscountCard card = Optional.ofNullable(info.getCard())
                    .map(cardNumber -> DiscountCardUtils.findByCardNumber(cards, cardNumber))
                    .orElse(new DiscountCard());
            ArrayList<ReceiptItem> items = ReceiptItemUtils.getReceiptItems(products, info.getProductsInfo(), card.getAmount());

            Receipt receipt = new Receipt(items, card);
            if (receipt.getTotal() > info.getBalance()) {
                throw new NotEnoughMoneyException();
            }

            writer.fileWriter(parser.getSaveToFile(), receipt);
        } catch (CustomException e) {
            writer.writeError(parser.getSaveToFile(), e);
        }
    }

}
