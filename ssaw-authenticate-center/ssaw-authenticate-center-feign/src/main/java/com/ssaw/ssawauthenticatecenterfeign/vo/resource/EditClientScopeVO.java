package com.ssaw.ssawauthenticatecenterfeign.vo.resource;

import com.ssaw.ssawauthenticatecenterfeign.vo.TreeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hszyp
 * @date 2019/01/27
 */
@Data
public class EditClientScopeVO implements Serializable {
    private static final long serialVersionUID = -4674883187565346398L;
    private List<TreeVO> treeVOS;
    private List<Long> defaultExpandedKeys;
}
