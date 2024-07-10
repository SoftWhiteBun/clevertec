package ru.clevertec.check.exception;

public class NotEnoughMoneyException extends CustomException {
    public NotEnoughMoneyException() {
        super("NOT ENOUGH MONEY");
    }
}