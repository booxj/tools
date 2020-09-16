package com.booxj.tools.core.utils;

import org.junit.Test;

import java.util.*;

public class ArrayUtilTest {

    @Test
    public void toArray(){
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.add("4");
        System.out.println(ArrayUtil.toArray(stringList,String.class));

        Set<String> stringSet = new HashSet<>();
        stringSet.add("1");
        stringSet.add("2");
        stringSet.add("3");
        stringSet.add("4");
        System.out.println(ArrayUtil.toArray(stringSet,String.class));
    }
}
