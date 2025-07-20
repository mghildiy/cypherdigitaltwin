package com.cypherlabs.cypherdigitaltwin.common;

import org.junit.jupiter.api.Test;

public abstract class BaseUnitTestTemplate {

    @Test
    public final void run() {
        prepareData();
        Object result = executeTest();
        validateResult(result);
    }

    protected abstract void prepareData();
    protected abstract Object executeTest();
    protected abstract void validateResult(Object result);
}
