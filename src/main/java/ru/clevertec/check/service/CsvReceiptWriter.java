package ru.clevertec.check.service;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ru.clevertec.check.exception.CustomException;
import ru.clevertec.check.models.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CsvReceiptWriter implements ICsvDataWriter<Receipt> {

    private static final String DEFAULT = "result.csv";
    private static final String[] RESULT_DATETIME_HEADERS = {"Date", "Time"};
    private static final String[] RESULT_PRODUCTS_HEADERS = {"QTY", "DESCRIPTION", "PRICE", "DISCOUNT", "TOTAL"};
    private static final String[] RESULT_DISCOUNT_CARD_HEADERS = {"DISCOUNT CARD", "DISCOUNT PERCENTAGE"};
    private static final String[] RESULT_TOTAL_PRICE_HEADERS = {"TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT"};

    @Override
    public void writeError(String filePath, CustomException e) {
        if (filePath == null) {
            filePath = DEFAULT;
        }

        File file = new File(filePath);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("ERROR\n");
            fw.write(e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("Can't write error file:" + ex.getMessage());
        }
    }

    @Override
    public void fileWriter(String filePath, Receipt receipt) {
        try {
            File file = new File(filePath);
            CSVPrinter printer = new CSVPrinter(new PrintWriter(file), CSVFormat.DEFAULT.builder().setDelimiter(";").build());

            if (receipt.getCard() != null) {
                printFilePartialData(receipt, printer, RESULT_DISCOUNT_CARD_HEADERS);
                printer.printRecord(receipt.getCard().getNumber(), receipt.getCard().getAmount() + "%");
                printer.println();
                printer.printRecord((Object[]) RESULT_TOTAL_PRICE_HEADERS);
                printer.printRecord((receipt.getTotal()) + "$", (receipt.getDiscount()) + "$", receipt.getTotal() - receipt.getDiscount() + "$");
                printer.flush();
                return;
            }

            printFilePartialData(receipt, printer, RESULT_TOTAL_PRICE_HEADERS);
            printer.printRecord((receipt.getTotal()) + "$", (receipt.getDiscount()) + "$", receipt.getTotal() - receipt.getDiscount() + "$");
            printer.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Can't write file:" + ex.getMessage());
        }
    }

    private void printFilePartialData(Receipt receipt, CSVPrinter printer, Object[] resultDiscountCardHeaders) throws IOException {
        printer.printRecord((Object[]) RESULT_DATETIME_HEADERS);
        printer.printRecord(receipt.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), receipt.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        printer.println();
        printer.printRecord((Object[]) RESULT_PRODUCTS_HEADERS);
        receipt.getItems().forEach(f -> {
            try {
                printer.printRecord(f.getQuantity(), f.getDescription(), f.getPrice() + "$", f.getDiscount() + "$", f.getTotal() + "$");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printer.println();
        printer.printRecord(resultDiscountCardHeaders);
    }

}
