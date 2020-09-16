package com.booxj.tools.core.utils;

import com.booxj.tools.core.lang.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_DIV_SCALE = 10;

    public static BigDecimal add(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < values.length; i++) {
            result = result.add(new BigDecimal(null == values[i] ? "0" : values[i].toString()));
        }
        return result;
    }

    public static BigDecimal add(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        for (int i = 1; i < values.length; i++) {
            String value = values[0];
            if (!StringUtil.isBlank(value)) {
                result = result.add(new BigDecimal(value));
            }
        }
        return result;
    }

    public static BigDecimal sub(String v1, String v2) {
        return new BigDecimal(v1).subtract(new BigDecimal(v2));
    }

    public static BigDecimal sub(Number v1, Number v2) {
        return new BigDecimal(v1.toString()).subtract(new BigDecimal(v2.toString()));
    }


    public static BigDecimal mul(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < values.length; i++) {
            result = result.multiply(new BigDecimal(null == values[i] ? "0" : values[i].toString()));
        }
        return result;
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal div(String v1, String v2, int scale, RoundingMode roundingMode) {
        return div(new BigDecimal(v1), new BigDecimal(v2), scale, roundingMode);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        Assert.notNull(v2, "Divisor must be not null !");
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = -scale;
        }
        return v1.divide(v2, scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }
}
