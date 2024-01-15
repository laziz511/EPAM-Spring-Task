package com.epam.esm.core.utils;

import com.epam.esm.core.exception.OperationException;

import static com.epam.esm.core.constants.ErrorMessageConstants.PAGE_NUMBER_AND_SIZE_ERROR_MESSAGE;

public class Validator {
    public static void validatePageAndSize(int page, int size, Object validationClass) {
        if (page <= 0 || size <= 0)
            throw new OperationException(PAGE_NUMBER_AND_SIZE_ERROR_MESSAGE, validationClass);
    }

    private Validator() {

    }

}
