package com.mywallet.util;

import java.math.BigDecimal;

/**
 * 格式化工具
 *
 * @author linapex
 */
public class FormatUtil {

    public static Double formatEth(String value) {
        BigDecimal bd1 = new BigDecimal(value);
        BigDecimal bd2 = new BigDecimal("1000000000000000000");
        return bd1.divide(bd2).doubleValue();
    }

}
