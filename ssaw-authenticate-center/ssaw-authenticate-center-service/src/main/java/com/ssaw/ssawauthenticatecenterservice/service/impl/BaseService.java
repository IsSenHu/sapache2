package com.ssaw.ssawauthenticatecenterservice.service.impl;

import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author HuSen.
 * @date 2018/12/12 17:32.
 */
public class BaseService {

    Pageable getPageRequest(PageReqVO pageReqVO) {
        pageReqVO.setPage(pageReqVO.getPage() == null ? 1 : pageReqVO.getPage());
        pageReqVO.setSize(pageReqVO.getSize() == null ? 10 : pageReqVO.getSize());
        Sort.Order order;
        String sortValue = pageReqVO.getSortValue();
        String sortType = pageReqVO.getSortType();
        boolean sortByRequest = StringUtils.isNotBlank(sortValue) && StringUtils.isNotBlank(sortType)
                && (StringUtils.equalsIgnoreCase("asc", sortType) || StringUtils.equalsIgnoreCase("desc", sortType));
        if(sortByRequest) {
            order = StringUtils.equalsIgnoreCase("asc", sortType) ? Sort.Order.asc(sortValue) : Sort.Order.desc(sortValue);
        } else if (StringUtils.isNotBlank(sortValue)){
            order = Sort.Order.desc(sortValue);
        } else {
            order = Sort.Order.desc("createTime");
        }
        return PageRequest.of(pageReqVO.getPage() - 1, pageReqVO.getSize(), Sort.by(order));
    }

    void setTableData(Page<?> page, TableData<?> tableData) {
        if (null != page) {
            tableData.setTotals(page.getTotalElements());
            tableData.setTotalPages(page.getTotalPages());
            tableData.setSize(page.getSize());
            tableData.setPage(page.getNumber() + 1);
        }
    }
}
