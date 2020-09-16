package com.booxj.tools.core.utils;

import org.junit.Test;

public class StringUtilTest {

    @Test
    public void formatTest(){
        String str = "\\\\{}0\\{}123{}456789{ }";
        System.out.println(StringUtil.format(str,"a","b","c","d","e","f","j"));

    }
}
