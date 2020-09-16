package com.booxj.tools.core.net;

import com.booxj.tools.core.collection.CollectionUtil;
import com.booxj.tools.core.exceptions.UtilException;
import com.booxj.tools.core.lang.Filter;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * 网络相关工具
 */
public class NetUtil {

    public final static String LOCAL_IP = "127.0.0.1";

    /**
     * 默认最小端口，1024
     */
    public static final int PORT_RANGE_MIN = 1024;
    /**
     * 默认最大端口，65535
     */
    public static final int PORT_RANGE_MAX = 0xFFFF;

    /**
     * 根据long值获取ip v4地址
     *
     * @param longIP IP的long表示形式
     * @return IP V4 地址
     * @see Ipv4Util#longToIpv4(long)
     */
    public static String longToIpv4(long longIP) {
        return Ipv4Util.longToIpv4(longIP);
    }

    /**
     * 根据ip地址计算出long型的数据
     *
     * @param strIP IP V4 地址
     * @return long值
     * @see Ipv4Util#ipv4ToLong(String)
     */
    public static long ipv4ToLong(String strIP) {
        return Ipv4Util.ipv4ToLong(strIP);
    }

    /**
     * 检测本地端口可用性<br>
     * 来自org.springframework.util.SocketUtils
     *
     * @param port 被检测的端口
     * @return 是否可用
     */
    public static boolean isUsableLocalPort(int port) {
        if (false == isValidPort(port)) {
            // 给定的IP未在指定端口范围中
            return false;
        }

        // issue#765@Github, 某些绑定非127.0.0.1的端口无法被检测到
        try (ServerSocket ss = new ServerSocket(port)) {
            ss.setReuseAddress(true);
        } catch (IOException ignored) {
            return false;
        }

        try (DatagramSocket ds = new DatagramSocket(port)) {
            ds.setReuseAddress(true);
        } catch (IOException ignored) {
            return false;
        }

        return true;
    }

    /**
     * 是否为有效的端口<br>
     * 此方法并不检查端口是否被占用
     *
     * @param port 端口号
     * @return 是否有效
     */
    public static boolean isValidPort(int port) {
        // 有效端口是0～65535
        return port >= 0 && port <= PORT_RANGE_MAX;
    }

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp;
        long ipNum = NetUtil.ipv4ToLong(ipAddress);

        long aBegin = NetUtil.ipv4ToLong("10.0.0.0");
        long aEnd = NetUtil.ipv4ToLong("10.255.255.255");

        long bBegin = NetUtil.ipv4ToLong("172.16.0.0");
        long bEnd = NetUtil.ipv4ToLong("172.31.255.255");

        long cBegin = NetUtil.ipv4ToLong("192.168.0.0");
        long cEnd = NetUtil.ipv4ToLong("192.168.255.255");

        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals(LOCAL_IP);
        return isInnerIp;
    }

    /**
     * 获取本机网卡IP地址，规则如下：
     *
     * <pre>
     * 1. 查找所有网卡地址，必须非回路（loopback）地址、非局域网地址（siteLocal）、IPv4地址
     * 2. 如果无满足要求的地址，调用 {@link InetAddress#getLocalHost()} 获取地址
     * </pre>
     * <p>
     * 此方法不会抛出异常，获取失败将返回<code>null</code><br>
     * <p>
     */
    public static InetAddress getLocalhost() {
        final LinkedHashSet<InetAddress> localAddressList = localAddressList(address -> {
            // 非loopback地址，指127.*.*.*的地址
            return false == address.isLoopbackAddress()
                    // 非地区本地地址，指10.0.0.0 ~ 10.255.255.255、172.16.0.0 ~ 172.31.255.255、192.168.0.0 ~ 192.168.255.255
                    && false == address.isSiteLocalAddress()
                    // 需为IPV4地址
                    && address instanceof Inet4Address;
        });

        if (CollectionUtil.isNotEmpty(localAddressList)) {
            return localAddressList.iterator().next();
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // ignore
        }

        return null;
    }

    /**
     * 获取所有满足过滤条件的本地IP地址对象
     *
     * @param addressFilter 过滤器，null表示不过滤，获取所有地址
     * @return 过滤后的地址对象列表
     */
    public static LinkedHashSet<InetAddress> localAddressList(Filter<InetAddress> addressFilter) {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new UtilException(e);
        }

        if (networkInterfaces == null) {
            throw new UtilException("Get network interface error!");
        }

        final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();

        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                final InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress != null && (null == addressFilter || addressFilter.accept(inetAddress))) {
                    ipSet.add(inetAddress);
                }
            }
        }

        return ipSet;
    }

    /**
     * 获得指定地址信息中的MAC地址
     *
     * @param inetAddress {@link InetAddress}
     * @param separator   分隔符，推荐使用“-”或者“:”
     * @return MAC地址，用-分隔
     */
    public static String getMacAddress(InetAddress inetAddress, String separator) {
        if (null == inetAddress) {
            return null;
        }

        byte[] mac = null;
        try {
            final NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            if(null != networkInterface){
                mac = networkInterface.getHardwareAddress();
            }
        } catch (SocketException e) {
            throw new UtilException(e);
        }
        if (null != mac) {
            final StringBuilder sb = new StringBuilder();
            String s;
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append(separator);
                }
                // 字节转换为整数
                s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            return sb.toString();
        }

        return null;
    }

    /**
     * 指定IP的long是否在指定范围内
     *
     * @param userIp 用户IP
     * @param begin  开始IP
     * @param end    结束IP
     * @return 是否在范围内
     */
    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }
}
