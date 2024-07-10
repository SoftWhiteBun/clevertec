package ru.clevertec.check.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ru.clevertec.check.exception.CustomException;
import ru.clevertec.check.models.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

public class CsvReceiptWriter implements ICsvDataWriter<Receipt> {

    private final String filePath;

    private static final String[] RESULT_DATETIME_HEADERS = {"Date", "Time"};
    private static final String[] RESULT_PRODUCTS_HEADERS = {"QTY", "DESCRIPTION", "PRICE", "DISCOUNT", "TOTAL"};
    private static final String[] RESULT_DISCOUNT_CARD_HEADERS = {"DISCOUNT CARD", "DISCOUNT PERCENTAGE"};
    private static final String[] RESULT_TOTAL_PRICE_HEADERS = {"TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT"};

    public CsvReceiptWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeError(CustomException e) {
        File file = new File(filePath);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("ERROR\n");
            fw.write(e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("Can't write error file:" + ex.getMessage());
        }
    }

    @Override
    public void fileWriter(Receipt receipt) {
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

    @Override
    public void consoleWriter(Receipt receipt) {
        if (receipt.getCard() != null) {
            printConsolePartialData(receipt);
            System.out.println();
            System.out.println("Discount card: " + receipt.getCard().getNumber() + ", amount: " + receipt.getCard().getAmount());
            System.out.println("Total: " + receipt.getTotal() + ", Discount: " + receipt.getDiscount() + ", Total with discount: " + (receipt.getTotal() - receipt.getDiscount()));
            System.out.println();
            return;
        }
        printConsolePartialData(receipt);
        System.out.println("No Discount card");
        System.out.println("Total: " + receipt.getTotal() + ", Discount: " + receipt.getDiscount() + ", Total with discount: " + (receipt.getTotal() - receipt.getDiscount()));
        System.out.println();
    }

    private void printConsolePartialData(Receipt receipt) {
        System.out.println("Time: " + receipt.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + receipt.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println();
        System.out.println("Product items");
        System.out.println();
        receipt.getItems().forEach(f -> System.out.println(f.getDescription() + "\t\t" + f.getPrice() + "\t\t" + f.getQuantity() + "\t\t" + f.getTotal() + "\t\t" + f.getDiscount()));
    }

}
