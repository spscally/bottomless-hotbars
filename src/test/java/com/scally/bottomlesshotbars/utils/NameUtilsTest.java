package com.scally.bottomlesshotbars.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NameUtilsTest {

   @Test
   public void validAlphanumeric() {
       final String name = "asdf-ASDF_1234";
       assert NameUtils.isValid(name) == true;
   }

   @Test
   public void invalidName() {
       final String name = "asdf.1234.fdf";
       assert NameUtils.isValid(name) == false;
   }

}
