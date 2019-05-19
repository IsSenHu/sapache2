package com.ssaw.ssawauthenticatecenterservice.authentication.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.ssawauthenticatecenterfeign.vo.ButtonVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.MenuVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author HuSen.
 * @date 2019/1/23 13:13.
 */
@Slf4j
public class CacheManager {
    private static final Cache<String, List<ScopeVO>> SCOPE_CACHE = CacheBuilder.newBuilder().build();
    private static final ReadWriteLock SCOPE_READ_WRITE_LOCK = new ReentrantReadWriteLock();

    private static final Cache<String, MenuVO> MENU_CACHE = CacheBuilder.newBuilder().build();
    private static final ReadWriteLock MENU_READ_WRITE_LOCK = new ReentrantReadWriteLock();

    private static final Cache<String, List<ButtonVO>> BUTTON_CACHE = CacheBuilder.newBuilder().build();
    private static final ReadWriteLock BUTTON_READ_WRITE_LOCK = new ReentrantReadWriteLock();

    private static final Cache<String, List<String>> WHITE_LIST_CACHE = CacheBuilder.newBuilder().build();
    private static final ReadWriteLock WHITE_LIST_READ_WHITE_LOCK = new ReentrantReadWriteLock();

    /**
     * 刷新作用域
     * @param resourceId 资源ID名称
     * @param scopes 作用域集合
     */
    public synchronized static void refreshScopes(String resourceId, List<ScopeVO> scopes) {
        Lock writeLock = SCOPE_READ_WRITE_LOCK.writeLock();
        try {
            writeLock.lock();
            SCOPE_CACHE.put(resourceId, scopes);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 添加按钮
     * @param resourceId 资源ID名称
     * @param buttonVOS 按钮
     */
    public synchronized static void addButtons(String resourceId, List<ButtonVO> buttonVOS) {
        Lock writeLock = BUTTON_READ_WRITE_LOCK.writeLock();
        try {
            writeLock.lock();
            log.info("新增按钮:{}->{}", resourceId, JsonUtils.object2JsonString(buttonVOS));
            BUTTON_CACHE.put(resourceId, buttonVOS);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 添加菜单
     * @param resourceId 资源ID名称
     * @param menuVO 菜单
     */
    public synchronized static void addMenus(String resourceId, MenuVO menuVO) {
        Lock writeLock = MENU_READ_WRITE_LOCK.writeLock();
        try {
            writeLock.lock();
            log.info("新增菜单:{}->{}", resourceId, JsonUtils.object2JsonString(menuVO));
            MENU_CACHE.put(resourceId, menuVO);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 刷新白名单
     * @param resourceId 资源ID名称
     * @param whiteList 白名单
     */
    public synchronized static void refreshWhiteList(String resourceId, List<String> whiteList) {
        Lock writeLock = WHITE_LIST_READ_WHITE_LOCK.writeLock();
        try {
           writeLock.lock();
           log.info("新增白名单:{}->{}", resourceId, whiteList);
           WHITE_LIST_CACHE.put(resourceId, whiteList);
       } finally {
           writeLock.unlock();
       }
    }

    /**
     * 获得所有作用域
     * @return 作用域
     */
    public static List<ScopeVO> getScopes() {
        Lock readLock = SCOPE_READ_WRITE_LOCK.readLock();
        try {
            readLock.lock();
            ConcurrentMap<String, List<ScopeVO>> concurrentMap = SCOPE_CACHE.asMap();
            List<ScopeVO> scopeVOList = new ArrayList<>();
            concurrentMap.values().forEach(scopeVOList::addAll);
            return scopeVOList;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 获取菜单
     * @param resourceId 资源ID名称
     * @return 菜单
     */
    public static MenuVO getMenu(String resourceId) {
        Lock readLock = MENU_READ_WRITE_LOCK.readLock();
        try {
            readLock.lock();
            return MENU_CACHE.getIfPresent(resourceId);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 获取按钮
     * @param resourceId 资源ID名称
     * @return 按钮
     */
    public static List<ButtonVO> getButtons(String resourceId) {
        Lock readLock = SCOPE_READ_WRITE_LOCK.readLock();
        try {
            readLock.lock();
            return BUTTON_CACHE.getIfPresent(resourceId);
        } finally {
            readLock.unlock();
        }
    }
}
