package ru.clevertec.check.core;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.exception.*;
import ru.clevertec.check.models.OperationInfo;
import ru.clevertec.check.service.ApplicationArgsParser;

import java.util.Map;

class ApplicationArgsParserTest {

    @Test
    void parserTest() throws BadRequestException {
        OperationInfo info = new OperationInfo();
        info.setProductsInfo(Map.of(2,1,1,3,1,4));
        info.setCard(1111);
        info.setBalanse(192.1);
        info.setUrl("url");
        info.setUsername("user");
        info.setPassword("pass");

        ApplicationArgsParser parser = new ApplicationArgsParser();
        parser.setArgs(new String[]{"2-1", "1-3", "1-4", "1111", "192.1", "url", "user", "pass"});
        OperationInfo info1 = parser.getOperationInfo();

        Assertions.assertEquals(info, info1);
    }

    @Test
    void parserTest2() throws BadRequestException {
        OperationInfo info = new OperationInfo();
        info.setProductsInfo(Map.of(1,2,3,4));
        info.setCard(1111);
        info.setBalanse(192.1);
        info.setUrl("url");
        info.setUsername("user");
        info.setPassword("pass");

        ApplicationArgsParser parser = new ApplicationArgsParser();
        parser.setArgs(new String[]{"3-4", "2-1", "1111", "192.1", "url", "user", "pass"});
        OperationInfo info1 = parser.getOperationInfo();

        Assertions.assertEquals(info, info1);
    }
}