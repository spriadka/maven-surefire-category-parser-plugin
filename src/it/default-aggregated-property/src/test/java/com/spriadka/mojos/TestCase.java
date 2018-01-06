package com.spriadka.mojos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestCase {

    @Test
    @Category(Dog.class)
    public void test() {
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        Assert.assertTrue(true);
    }

    @Test
    @Category(Cow.class)
    public void test3() {
        Assert.assertTrue(true);
    }

}
