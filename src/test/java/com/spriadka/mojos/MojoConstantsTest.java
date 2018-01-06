package com.spriadka.mojos;

import com.spriadka.mojos.mojos.MojoConstants;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class MojoConstantsTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<MojoConstants> mojoConstantsConstructor = MojoConstants.class.getDeclaredConstructor();
        Assert.assertTrue("Constructor should be private", Modifier.isPrivate(mojoConstantsConstructor.getModifiers()));
        mojoConstantsConstructor.setAccessible(true);
        mojoConstantsConstructor.newInstance();
    }

}
