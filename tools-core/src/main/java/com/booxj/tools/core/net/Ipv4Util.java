package com.booxj.tools.core.net;

import com.booxj.tools.core.lang.PatternPool;

public class Ipv4Util {

    /**
     * IP段的分割符
     */
    public static final String IP_SPLIT_MARK = "-";

    /**
     * IP与掩码的分割符
     */
    public static final String IP_MASK_SPLIT_MARK = "/";

    /**
     * 最大掩码位
     */
    public static final int IP_MASK_MAX = 32;

    /**
     * 根据long值获取ip v4地址：xx.xx.xx.xx
     *
     * @param longIP IP的long表示形式
     * @return IP V4 地址
     */
    public static String longToIpv4(long longIP) {
        final StringBuilder sb = new StringBuilder();
        // 直接右移24位
        sb.append((longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append((longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * 根据ip地址(xxx.xxx.xxx.xxx)计算出long型的数据
     *
     * @param strIP IP V4 地址
     * @return long值
     */
    public static long ipv4ToLong(String strIP) {
        if (isIpv4(strIP)) {
            long[] ip = new long[4];
            // 先找到IP地址字符串中.的位置
            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);
            // 将每个.之间的字符串转换成整型
            ip[0] = Long.parseLong(strIP.substring(0, position1));
            ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIP.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        }
        return 0;
    }

    public static boolean isIpv4(String ip) {
        return PatternPool.IPV4.matcher(ip).matches();
    }
}
