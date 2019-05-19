package com.ssaw.uaa.store;

/**
 * @author HuSen
 * @date 2019/4/28 21:46
 */
public class TokenStore {

    private static final ThreadLocal<String> TOKEN = new InheritableThreadLocal<>();

    /**
     * 清除当前用户
     */
    public static void clear() {
        TOKEN.remove();
    }

    /**
     * 获取当前用户
     * @return SimpleUserAttributeVO
     */
    public static String current() {
        return TOKEN.get();
    }

    /**
     * 保存当前用户
     * @param token Token
     */
    public static void store(String token) {
        TOKEN.set(token);
    }
}