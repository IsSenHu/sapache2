package com.ssaw.config.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.vo.TableData;
import com.ssaw.config.sdk.vo.ConfigViewVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/6/13 19:14
 */
@Data
public class ConfigVO {
    /**
     * 主键
     * */
    private String id;

    /**
     * 应用名称
     * */
    private String application;

    /**
     * 环境
     * */
    private String profile;

    /**
     * 标签
     * */
    private String label;

    /**
     * 配置文件类型
     * */
    private String type;

    /**
     * 修改时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;

    /**
     * 修改人
     * */
    private String modifyMan;

    /**
     * 创建时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 创建人
     * */
    private String createMan;

    public static TableData<ConfigVO> of(TableData<ConfigViewVO> t) {
        if (Objects.nonNull(t)) {
            TableData<ConfigVO> r = new TableData<>();
            r.setPage(t.getPage());
            r.setSize(t.getSize());
            r.setTotalPages(t.getTotalPages());
            r.setTotals(t.getTotals());
            r.setContent(t.getContent().stream().map(i -> CopyUtil.copyProperties(i, new ConfigVO())).collect(Collectors.toList()));
            return r;
        }
        return new TableData<>();
    }
}
