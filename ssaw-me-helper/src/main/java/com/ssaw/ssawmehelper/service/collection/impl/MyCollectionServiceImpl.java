package com.ssaw.ssawmehelper.service.collection.impl;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawmehelper.dao.redis.MyCollectionDao;
import com.ssaw.ssawmehelper.dao.ro.MyCollectionRo;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionCreateRequestVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionQueryVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionVO;
import com.ssaw.ssawmehelper.service.collection.MyCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen
 * @date 2019/4/19 14:16
 */
@Slf4j
@Service
public class MyCollectionServiceImpl implements MyCollectionService {

    private final MyCollectionDao myCollectionDao;

    @Autowired
    public MyCollectionServiceImpl(MyCollectionDao myCollectionDao) {
        this.myCollectionDao = myCollectionDao;
    }

    /**
     * 新增收藏类别
     *
     * @param classification 收藏类别
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> newClassification(String classification) {
        myCollectionDao.newClassification(classification);
        return CommonResult.createResult(SUCCESS, "成功", classification);
    }

    /**
     * 获取所有收藏类别
     *
     * @return 所有收藏类别
     */
    @Override
    public CommonResult<Set<String>> allClassification() {
        return CommonResult.createResult(SUCCESS, "成功", myCollectionDao.allClassification());
    }

    /**
     * 创建我的收藏
     *
     * @param requestVO 创建我的收藏请求数据模型
     * @return 创建结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<MyCollectionCreateRequestVO> create(MyCollectionCreateRequestVO requestVO) {
        MyCollectionRo ro = new MyCollectionRo();
        Long userId = UserContextHolder.currentUser().getId();
        ro.setUserId(userId);
        ro.setTitle(requestVO.getTitle());
        ro.setLink(requestVO.getLink());
        ro.setDesc(requestVO.getDesc());
        long time = System.currentTimeMillis();
        ro.setTime(time);
        ro.setVotes(0);
        ro.setClassification(requestVO.getClassification());
        String key = myCollectionDao.insert(ro);
        Long timeScore = myCollectionDao.insertTimeScore(key, time);
        Long edenScore = myCollectionDao.insertScore(key, time);
        Long groupChange = myCollectionDao.insertInGroup(key, requestVO.getClassification());
        log.info("创建我的收藏成功:{} - {} - {} - {}", key, timeScore, edenScore, groupChange);
        return CommonResult.createResult(SUCCESS, "成功", requestVO);
    }

    /**
     * 增加收藏分数
     *
     * @param id 收藏ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Long> addScore(Long id) {
        String key = myCollectionDao.idToKey(id);
        Double score = myCollectionDao.addScore(key);
        log.info("增加收藏分数成功{} - {}", key, score);
        Long votes = myCollectionDao.addVotes(key);
        log.info("增加浏览次数成功{} - {}", key, votes);
        return CommonResult.createResult(SUCCESS, "成功", id);
    }

    /**
     * 收藏列表
     *
     * @param pageReqVO 分页参数
     * @return 收藏列表
     */
    @Override
    public TableData<MyCollectionVO> list(PageReqVO<MyCollectionQueryVO> pageReqVO) {
        return myCollectionDao.list(pageReqVO);
    }
}