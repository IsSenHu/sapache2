package com.ssaw.ssawauthenticatecenterfeign.vo.role;

import com.ssaw.ssawauthenticatecenterfeign.vo.TreeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuSen.
 * @date 2019/1/23 13:51.
 */
@Data
public class EditRoleVO implements Serializable {
    private static final long serialVersionUID = -8979178837755101695L;

    private RoleVO roleVO;

    private List<TreeVO> treeVOS;

    private List<Long> defaultExpandedKeys;

    private List<Long> defaultCheckedKeys;
}
