package ru.clevertec.check.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {
    int id;
    int number;
    int amount;
}
