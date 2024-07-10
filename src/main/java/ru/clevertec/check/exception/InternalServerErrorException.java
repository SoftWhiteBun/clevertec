package ru.clevertec.check.exception;

public class InternalServerErrorException extends CustomException {
    public InternalServerErrorException(Throwable cause) {
        super("INTERNAL SERVER ERROR");
        System.out.println(cause.getMessage());
    }
}
