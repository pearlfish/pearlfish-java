package com.natpryce.pearlfish.formats;


public interface Format {
    TemplateFormatter formatterFor(String testName, Class<?> testClass);
}
