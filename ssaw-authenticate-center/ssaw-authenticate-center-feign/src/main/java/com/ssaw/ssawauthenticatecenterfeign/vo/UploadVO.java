package com.ssaw.ssawauthenticatecenterfeign.vo;

import com.ssaw.ssawauthenticatecenterfeign.vo.resource.UploadResourceVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.scope.ScopeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/3/19 12:19
 */
@Data
public class UploadVO implements Serializable {
    private static final long serialVersionUID = -7237259664372509484L;

    /** 需要上传的资源 */
    private UploadResourceVO uploadResourceVO;

    /** 白名单 */
    private List<String> whiteList;

    /** 作用域 */
    private List<ScopeVO> scopeVOList;

    /** 菜单 */
    private MenuVO menuVO;

    /** 按钮 */
    private List<ButtonVO> buttonVOS;
}