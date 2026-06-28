package com.guymontag.eventapi;

import com.guymontag.eventapi.exception.PageSizeOutOfBoundException;
import com.guymontag.eventapi.util.Page;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageTest {

    @Test
    void shouldReturnNumberElementsInPageWhenGetNumElements() {

        //Arrange
        int pageNumber = 0;

        int numberObjectsExcepted = 15;

        int maxSize = 20;

        Long totalElements = 200L;

        List<Object> listOf20Element = new ArrayList<>(20);

        for (int i = 0; i <= numberObjectsExcepted - 1; i++) {
            listOf20Element.add(new Object());
        }

        Page<Object> page = new Page<>(listOf20Element, pageNumber, maxSize, totalElements);

        //Action
        int numberElementsResult = page.getNumberElements();

        //Assert
        assertEquals(numberObjectsExcepted, numberElementsResult);
    }

    @Test
    void shouldThrowExceptionWhenListGivenIsBiggerThanMaxSize() {
        //Arrange
        int pageNumber = 0;

        int numberOfElements = 35;

        int maxSize = 20;

        Long totalElements = 200L;

        List<Object> listOf20Element = new ArrayList<>(20);

        for (int i = 0; i <= numberOfElements - 1; i++) {
            listOf20Element.add(new Object());
        }
        PageSizeOutOfBoundException pageSizeOutOfBoundExceptionExcepted =
                new PageSizeOutOfBoundException("List given is bigger than maxSize");

        //Action
        PageSizeOutOfBoundException pageSizeOutOfBoundExceptionResult =
                assertThrows(PageSizeOutOfBoundException.class, () -> new Page<Object>(listOf20Element, pageNumber, maxSize, totalElements));

        //Assert
        assertEquals(pageSizeOutOfBoundExceptionExcepted.getClass(), pageSizeOutOfBoundExceptionResult.getClass());
        assertEquals(pageSizeOutOfBoundExceptionExcepted.getMessage(), pageSizeOutOfBoundExceptionResult.getMessage());
    }

}
