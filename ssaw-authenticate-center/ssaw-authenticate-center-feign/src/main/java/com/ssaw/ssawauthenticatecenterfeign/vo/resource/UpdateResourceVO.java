package com.ssaw.ssawauthenticatecenterfeign.vo.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author HuSen.
 * @date 2018/12/11 14:08.
 */
@Data
public class UpdateResourceVO implements Serializable {
    private static final long serialVersionUID = -176708107593950995L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    @NotBlank(message = "资源ID不能为空!")
    @Length(max = 50, message = "资源的最大长度不能超过50!")
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
