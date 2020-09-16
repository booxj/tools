package com.booxj.tools.core.lang;

import com.booxj.tools.core.utils.StringUtil;

import java.util.Scanner;

import static java.lang.System.*;

/**
 * 命令行（控制台）工具方法类<br>
 * 此类主要针对{@link System#out} 和 {@link System#err} 做封装。
 */
public class Console {

    // --------------------------------------------------------------------------------- Log

    public static void log() {
        out.println();
    }

    public static void log(Object obj) {
        if (obj instanceof Throwable) {
            Throwable e = (Throwable) obj;
            log(e, e.getMessage());
        } else {
            log("{}", obj);
        }
    }

    public static void log(String template, Object... values) {
        log(null, template, values);
    }

    public static void log(Throwable t, String template, Object... values) {
        out.println(StringUtil.format(template, values));
        if (null != t) {
            t.printStackTrace();
            out.flush();
        }
    }

    // --------------------------------------------------------------------------------- Error

    public static void error() {
        err.println();
    }

    public static void error(String template, Object... values) {
        error(null, template, values);
    }

    public static void error(Throwable t, String template, Object... values) {
        err.println(StringUtil.format(template, values));
        if (null != t) {
            t.printStackTrace();
            err.flush();
        }
    }

    // --------------------------------------------------------------------------------- in

    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static String input() {
        return scanner().next();
    }

    /**
     * 返回当前位置+行号 (不支持Lambda、内部类、递归内使用)
     *
     * @return
     */
    public static String where() {
        final StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        final String className = stackTraceElement.getClassName();
        final String methodName = stackTraceElement.getMethodName();
        final String fileName = stackTraceElement.getFileName();
        final Integer lineNumber = stackTraceElement.getLineNumber();
        return String.format("%s.%s(%s:%s)", className, methodName, fileName, lineNumber);
    }

    /**
     * 返回当前行号
     *
     * @return
     */
    public static Integer lineNumber() {
        return new Throwable().getStackTrace()[1].getLineNumber();
    }

}
