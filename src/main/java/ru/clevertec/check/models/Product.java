package ru.clevertec.check.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@Getter
@NoArgsConstructor
@ToString
public class Product{
    int id;
    String description;
    double price;
    int quantity;
    boolean isWholesale;
}
