package com.ssaw.ssawmehelper.service.collection;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionCreateRequestVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionQueryVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionVO;

import java.util.Set;

/**
 * @author HuSen
 * @date 2019/4/19 14:16
 */
public interface MyCollectionService {

    /**
     * 新增收藏类别
     *
     * @param classification 收藏类别
     * @return 新增结果
     */
    CommonResult<String> newClassification(String classification);

    /**
     * 获取所有收藏类别
     *
     * @return 所有收藏类别
     */
    CommonResult<Set<String>> allClassification();

    /**
     * 创建我的收藏
     *
     * @param requestVO 创建我的收藏请求数据模型
     * @return 创建结果
     */
    CommonResult<MyCollectionCreateRequestVO> create(MyCollectionCreateRequestVO requestVO);

    /**
     * 增加收藏分数
     *
     * @param id 收藏ID
     * @return 操作结果
     */
    CommonResult<Long> addScore(Long id);

    /**
     * 收藏列表
     *
     * @param pageReqVO 分页参数
     * @return 收藏列表
     */
    TableData<MyCollectionVO> list(PageReqVO<MyCollectionQueryVO> pageReqVO);
}
