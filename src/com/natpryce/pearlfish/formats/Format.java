package com.natpryce.pearlfish.formats;


import org.rococoa.okeydoke.Formatter;

public interface Format {
    Formatter<Object,String> formatterFor(String testName, Class<?> testClass);
    String fileExtension();
}
