package com.chvs.webserverdemo.task;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private final Product product = new Product();

    @Test
    public void productTest() {
        assertFalse(product.isEnable());
        assertFalse(product.isReadonly());
        assertFalse(product.isProvided());
        assertFalse(product.isSpecial());
        assertFalse(product.isStub());

        product.setEnable(true);
        assertTrue(product.isEnable());
        product.setEnable(false);
        assertFalse(product.isEnable());

        product.setReadonly(true);
        assertTrue(product.isReadonly());
        product.setReadonly(false);
        assertFalse(product.isReadonly());

        product.setProvided(true);
        assertTrue(product.isProvided());
        product.setProvided(false);
        assertFalse(product.isProvided());

        product.setSpecial(true);
        assertTrue(product.isSpecial());
        product.setSpecial(false);
        assertFalse(product.isSpecial());

        product.setStub(true);
        assertTrue(product.isStub());
        product.setStub(false);
        assertFalse(product.isStub());

        product.setEnable(true);
        product.setReadonly(true);
        product.setProvided(true);
        product.setSpecial(true);
        product.setStub(true);

        assertTrue(product.isEnable());
        assertTrue(product.isReadonly());
        assertTrue(product.isProvided());
        assertTrue(product.isSpecial());
        assertTrue(product.isStub());

        product.setStub(false);
        product.setSpecial(false);
        assertTrue(product.isEnable());
        assertTrue(product.isReadonly());
        assertTrue(product.isProvided());
        assertFalse(product.isSpecial());
        assertFalse(product.isStub());
    }
}
