package ru.clevertec.check.service;

public interface ICsvDataWriter<T> {
    void consoleWriter(T data);
    void fileWriter(T data);
}
