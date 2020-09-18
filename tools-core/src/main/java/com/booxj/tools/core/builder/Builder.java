package com.booxj.tools.core.builder;

import java.io.Serializable;

/**
 * 建造者模式接口规范
 */
public interface Builder<T> extends Serializable {

    T build();
}
