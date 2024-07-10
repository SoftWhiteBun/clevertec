package ru.clevertec.check.exception;

public class BadRequestException extends CustomException {
    public BadRequestException() {
        super("BAD REQUEST");
    }
}