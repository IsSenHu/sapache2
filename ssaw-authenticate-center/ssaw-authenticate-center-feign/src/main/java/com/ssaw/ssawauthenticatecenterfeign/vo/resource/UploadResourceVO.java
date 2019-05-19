package com.ssaw.ssawauthenticatecenterfeign.vo.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author HuSen.
 * @date 2018/12/11 14:08.
 */
@Data
public class UploadResourceVO implements Serializable {
    private static final long serialVersionUID = -176708107593950995L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    private String resourceId;

    private String description;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime createTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime modifyTime;
    private String createMan;
    private String modifyMan;
}
