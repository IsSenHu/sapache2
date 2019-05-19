package com.ssaw.ssawmehelper.dao.redis;

import com.google.common.collect.Lists;
import com.ssaw.commons.id.DefaultIdService;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.util.json.jack.JsonUtils;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.redis.dao.BaseDao;
import com.ssaw.redis.request.PageRequest;
import com.ssaw.ssawmehelper.dao.ro.MyCollectionRo;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionQueryVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * @date 2019/4/17 17:34
 */
@Slf4j
@Repository
public class MyCollectionDao extends BaseDao<MyCollectionRo> {

    private final DefaultIdService defaultIdService;

    private final StringRedisTemplate stringRedisTemplate;

    private static final String COLLECTION_PREFIX = "collection:";

    private static final String ID_GENERATION_OFFSET = "id_generation_offset";

    private static final String COLLECTION_TIME_SCORE = "collection_time_score";

    private static final String COLLECTION_SCORE = "collection_score";

    private static final String CLASSIFICATION = "classification_set";

    private static final String GROUP_PREFIX = "group:";

    private static final long ONE_TIME_SCORE = 86400000;

    public String idToKey(Long id) {
        return COLLECTION_PREFIX.concat(id.toString());
    }

    @Autowired
    public MyCollectionDao(StringRedisTemplate stringRedisTemplate, DefaultIdService defaultIdService) {
        super(stringRedisTemplate);
        this.stringRedisTemplate = stringRedisTemplate;
        this.defaultIdService = defaultIdService;
    }

    public String insert(MyCollectionRo ro) {
        try {
            Long id = defaultIdService.genId();
            if (Objects.isNull(id)) {
                return null;
            }
            ro.setId(id);

            log.info("插入收藏:{}", JsonUtils.object2JsonString(ro));
            Map<String, String> map = new HashMap<>(8);
            map.put("id", ro.getId().toString());
            map.put("userId", Objects.isNull(ro.getUserId()) ? "" : ro.getUserId().toString());
            map.put("title", ro.getTitle());
            map.put("link", ro.getLink());
            map.put("desc", ro.getDesc());
            map.put("classification", ro.getClassification());
            map.put("time", ro.getTime().toString());
            map.put("votes", ro.getVotes().toString());

            HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
            String key = COLLECTION_PREFIX.concat(id.toString());
            hash.putAll(key, map);
            return key;
        } catch (Exception e) {
            log.error("插入收藏失败:", e);
            throw new RuntimeException("插入收藏失败");
        }
    }

    public Long insertInGroup(String key, String classification) {
        SetOperations<String, String> set = stringRedisTemplate.opsForSet();
        return set.add(GROUP_PREFIX.concat(classification), key);
    }

    public Long insertTimeScore(String key, Long unix) {
        try {
            ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
            zSet.add(COLLECTION_TIME_SCORE, key, unix);
            return unix;
        } catch (Exception e) {
            log.error("插入收藏时间分数失败:", e);
            throw new RuntimeException("插入收藏时间分数失败");
        }
    }

    public Long insertScore(String key, Long unix) {
        try {
            ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
            zSet.add(COLLECTION_SCORE, key, unix);
            return unix;
        } catch (Exception e) {
            log.error("插入收藏分数失败:", e);
            throw new RuntimeException("插入收藏分数失败");
        }
    }

    private MyCollectionRo findOne(String key) {
        try {
            HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
            Map<String, String> entries = hash.entries(key);
            return JsonUtils.jsonString2Object(JsonUtils.object2JsonString(entries), MyCollectionRo.class);
        } catch (Exception e) {
            log.error("根据key查询收藏失败:", e);
            return null;
        }
    }

    public Double addScore(String key) {
        try {
            ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
            return zSet.incrementScore(COLLECTION_SCORE, key, ONE_TIME_SCORE);
        } catch (Exception e) {
            log.error("给Key新增分数失败:", e);
            throw new RuntimeException("给Key新增分数失败");
        }
    }

    public Long addVotes(String key) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        return hash.increment(key, "votes", 1);
    }

    /**
     * 新增收藏类别
     *
     * @param classification 收藏类别
     */
    public void newClassification(String classification) {
        SetOperations<String, String> zet = stringRedisTemplate.opsForSet();
        zet.add(CLASSIFICATION, classification);
    }

    /**
     * 获取所有收藏类别
     *
     * @return 所有收藏类别
     */
    public Set<String> allClassification() {
        return stringRedisTemplate.opsForSet().members(CLASSIFICATION);
    }

    public TableData<MyCollectionVO> list(PageReqVO<MyCollectionQueryVO> pageReqVO) {
        TableData<MyCollectionVO> tableData = new TableData<>();
        // 说明要进行分组查询
        MyCollectionQueryVO query = pageReqVO.getData();
        Integer size = pageReqVO.getSize();
        String witchScore = query.getByTime() != null && query.getByTime() ? COLLECTION_TIME_SCORE : COLLECTION_SCORE;
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        PageRequest<MyCollectionRo> pageRequest;
        if (!StringUtils.isEmpty(query.getClassification())) {
            String group = GROUP_PREFIX.concat(query.getClassification());
            // 目标Key
            String distKey = group.concat("_").concat(witchScore);
            Long count = zSet.intersectAndStore(group, Lists.newArrayList(witchScore), distKey, RedisZSetCommands.Aggregate.MAX);
            // 5秒后过期
            stringRedisTemplate.expire(distKey, 5, TimeUnit.SECONDS);
            pageRequest = PageRequest.of(Long.valueOf(pageReqVO.getPage()), pageReqVO.getSize(), false, count);
            page(distKey, pageRequest, this::findOne);
        } else {
            pageRequest = PageRequest.of(Long.valueOf(pageReqVO.getPage()), pageReqVO.getSize(), false);
            page(witchScore, pageRequest, this::findOne);
        }
        return pageRequest.toViews(input -> CopyUtil.copyProperties(input, new MyCollectionVO()));
    }
}