package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawauthenticatecenterfeign.vo.ButtonVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.MenuVO;
import com.ssaw.ssawauthenticatecenterservice.service.MenuService;
import com.ssaw.ssawauthenticatecenterservice.authentication.cache.CacheManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen
 * @date 2019/2/26 10:35
 */
@Service
public class MenuServiceImpl implements MenuService {

    /**
     * 上传菜单
     * @param menuVO 菜单
     * @param resourceId 资源ID
     * @return 上传结果
     */
    @Override
    public CommonResult<String> uploadMenus(MenuVO menuVO, String resourceId) {
        CacheManager.addMenus(resourceId, menuVO);
        return CommonResult.createResult(SUCCESS, "成功", resourceId);
    }

    /**
     * 获取菜单
     * @param scope 作用域
     * @param resourceIds 资源ID
     * @return 菜单
     */
    @Override
    public List<MenuVO> getMenus(Set<String> scope, Set<String> resourceIds) {
        List<MenuVO> result = new ArrayList<>();
        for (String resourceId : resourceIds) {
            MenuVO menuVO = CacheManager.getMenu(resourceId);
            if (Objects.nonNull(menuVO)) {
                List<MenuVO> secondItems = menuVO.getItems().stream()
                        .sorted(Comparator.comparing(x -> Integer.valueOf(x.getIndex().replace("-", "")))).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(secondItems)) {
                    for (MenuVO secondItem : secondItems) {
                        List<MenuVO> collect = secondItem.getItems().stream().filter(t -> scope.contains(t.getScope()))
                                .sorted(Comparator.comparing(x -> Integer.valueOf(x.getIndex().replace("-", "")))).collect(Collectors.toList());
                        secondItem.setItems(collect);
                    }
                    secondItems = secondItems.stream().filter(m -> CollectionUtils.isNotEmpty(m.getItems())).collect(Collectors.toList());
                    menuVO.setItems(secondItems);
                }
                result.add(menuVO);
            }
        }
        return result.stream().sorted(Comparator.comparing(menu -> Integer.valueOf(menu.getIndex().replace("-", "")))).collect(Collectors.toList());
    }

    /**
     * 上传按钮
     * @param buttonVOS 按钮
     * @param resourceId 资源ID
     * @return 上传结果
     */
    @Override
    public CommonResult<String> uploadButtons(List<ButtonVO> buttonVOS, String resourceId) {
        CacheManager.addButtons(resourceId, buttonVOS);
        return CommonResult.createResult(SUCCESS, "成功", resourceId);
    }

    /**
     * 获取按钮
     * @param scope 作用域
     * @param resourceIds 资源ID
     * @return 按钮
     */
    @Override
    public List<ButtonVO> getButtons(Set<String> scope, Set<String> resourceIds) {
        List<ButtonVO> buttonVOS = new ArrayList<>();
        for (String resourceId : resourceIds) {
            List<ButtonVO> buttonVOList = CacheManager.getButtons(resourceId);
            if (Objects.nonNull(buttonVOList)) {
                List<ButtonVO> collect = buttonVOList.stream().filter(button -> scope.contains(button.getScope())).collect(Collectors.toList());
                buttonVOS.addAll(collect);
            }
        }
        return buttonVOS;
    }

    /**
     * 上传白名单
     * @param whiteList 白名单
     * @param resourceId 资源ID
     * @return 上传结果
     */
    @Override
    public CommonResult<String> uploadWhiteList(List<String> whiteList, String resourceId) {
        CacheManager.refreshWhiteList(resourceId, whiteList);
        return CommonResult.createResult(SUCCESS, "成功", resourceId);
    }
}