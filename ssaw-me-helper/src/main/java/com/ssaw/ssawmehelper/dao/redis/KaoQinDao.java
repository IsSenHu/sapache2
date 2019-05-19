package com.ssaw.ssawmehelper.dao.redis;

import com.ssaw.ssawmehelper.model.vo.kaoqin.CommitLeaveReqVO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.CommitOverTimeInfoReqVO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.IForgetPlayCardReqVO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.IOnlineReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author HuSen
 * @date 2019/4/12 16:52
 */
@Slf4j
@Repository
public class KaoQinDao {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String I_ONLINE_TABLE_PREFIX = "i_online_";

    private static final String I_FORGET_TABLE_PREFIX = "i_forget_";

    private static final String I_COMMIT_OVER_TIME_PREFIX = "i_commit_over_time_";

    private static final String I_COMMIT_LEAVE_PREFIX = "i_commit_leave_";

    @Autowired
    public KaoQinDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean insertOnlineTime(IOnlineReqVO reqVO) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_ONLINE_TABLE_PREFIX.concat(reqVO.getBn()));
            members = Objects.isNull(members) ? new HashSet<>() : members;
            members.add(reqVO.getDutyTime());
            set.add(I_ONLINE_TABLE_PREFIX.concat(reqVO.getBn()), members.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("insertOnlineTime fail:", e);
            return false;
        }
    }

    public Set<String> allOnlineTime(String bn) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_ONLINE_TABLE_PREFIX.concat(bn));
            members = Objects.isNull(members) ? new HashSet<>() : members;
            return members;
        } catch (Exception e) {
            log.error("allOnlineTime fail:", e);
            return new HashSet<>(0);
        }
    }

    public boolean deleteAllOnlineTime(String bn) {
        try {
            Boolean delete = stringRedisTemplate.delete(I_ONLINE_TABLE_PREFIX.concat(bn));
            return Objects.nonNull(delete) ? delete : false;
        } catch (Exception e) {
            log.error("deleteAllOnlineTime fail:", e);
            return false;
        }
    }

    public boolean insertForgetTime(IForgetPlayCardReqVO reqVO) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_FORGET_TABLE_PREFIX.concat(reqVO.getBn()));
            members = Objects.nonNull(members) ? members : new HashSet<>();
            members.add(reqVO.getDutyTime());
            set.add(I_FORGET_TABLE_PREFIX.concat(reqVO.getBn()), members.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("insertForgetTime fail:", e);
            return false;
        }
    }

    public Set<String> allForgetTime(String bn) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_FORGET_TABLE_PREFIX.concat(bn));
            members = Objects.isNull(members) ? new HashSet<>() : members;
            return members;
        } catch (Exception e) {
            log.error("allForgetTime fail:", e);
            return new HashSet<>(0);
        }
    }

    public boolean deleteAllForgetTime(String bn) {
        try {
            Boolean delete = stringRedisTemplate.delete(I_FORGET_TABLE_PREFIX.concat(bn));
            return Objects.nonNull(delete) ? delete : false;
        } catch (Exception e) {
            log.error("deleteAllForgetTime fail:", e);
            return false;
        }
    }

    public boolean insertCommitOverTime(CommitOverTimeInfoReqVO reqVO) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_COMMIT_OVER_TIME_PREFIX.concat(reqVO.getBn()));
            members = Objects.nonNull(members) ? members : new HashSet<>();
            members.add(reqVO.getDutyTime());
            set.add(I_COMMIT_OVER_TIME_PREFIX.concat(reqVO.getBn()), members.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("insertCommitOverTime fail:", e);
            return false;
        }
    }

    public Set<String> allCommitOverTime(String bn) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_COMMIT_OVER_TIME_PREFIX.concat(bn));
            members = Objects.isNull(members) ? new HashSet<>() : members;
            return members;
        } catch (Exception e) {
            log.error("allCommitOverTime fail:", e);
            return new HashSet<>(0);
        }
    }

    public boolean insertCommitLeave(CommitLeaveReqVO reqVO) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_COMMIT_LEAVE_PREFIX.concat(reqVO.getBn()));
            members = Objects.nonNull(members) ? members : new HashSet<>();
            members.add(reqVO.getDutyTime());
            set.add(I_COMMIT_LEAVE_PREFIX.concat(reqVO.getBn()), members.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("insertCommitOverTime fail:", e);
            return false;
        }
    }

    public Set<String> allCommitLeave(String bn) {
        try {
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            Set<String> members = set.members(I_COMMIT_LEAVE_PREFIX.concat(bn));
            members = Objects.isNull(members) ? new HashSet<>() : members;
            return members;
        } catch (Exception e) {
            log.error("allCommitOverTime fail:", e);
            return new HashSet<>(0);
        }
    }
}