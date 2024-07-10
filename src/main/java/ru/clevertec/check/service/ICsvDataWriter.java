package ru.clevertec.check.service;

import ru.clevertec.check.exception.CustomException;

public interface ICsvDataWriter<T> {
    void writeError(String filePath, CustomException e);
    void fileWriter(String filePath, T data);
}
