package com.ssaw.ssawmehelper.service.consumption;

import com.ssaw.commons.vo.PageReqVO;

/**
 * @author HuSen
 * @date 2019/3/19 18:06
 */
public class BaseService {

    protected <T> PageReqVO<T> getPage(PageReqVO<T> pageReqVO) {
        pageReqVO.setPage(pageReqVO.getPage() == null ? 1 : pageReqVO.getPage());
        pageReqVO.setSize(pageReqVO.getSize() == null ? 10 : pageReqVO.getSize());
        return pageReqVO;
    }
}