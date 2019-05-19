package com.ssaw.ssawauthenticatecenterfeign.store;

import com.ssaw.ssawauthenticatecenterfeign.vo.user.SimpleUserAttributeVO;
import org.springframework.util.Assert;

/**
 * 用户上下文
 * @author HuSen
 * @date 2019/3/1 9:52
 */
public class UserContextHolder {

    private static final ThreadLocal<SimpleUserAttributeVO> USER = new InheritableThreadLocal<>();

    /**
     * 清除当前用户
     */
    public static void clearUser() {
        USER.remove();
    }

    /**
     * 获取当前用户
     * @return SimpleUserAttributeVO
     */
    public static SimpleUserAttributeVO currentUser() {
        SimpleUserAttributeVO simpleUserAttributeVO = USER.get();
        if (simpleUserAttributeVO == null) {
            simpleUserAttributeVO = createEmptyUser();
            USER.set(simpleUserAttributeVO);
        }
        return simpleUserAttributeVO;
    }

    /**
     * 保存当前用户
     * @param simpleUserAttributeVO SimpleUserAttributeVO
     */
    public static void storeUser(SimpleUserAttributeVO simpleUserAttributeVO) {
        Assert.notNull(simpleUserAttributeVO, "Only non-null SimpleUserAttributeVO instances are permitted");
        USER.set(simpleUserAttributeVO);
    }

    private static SimpleUserAttributeVO createEmptyUser() {
        return new SimpleUserAttributeVO();
    }
}