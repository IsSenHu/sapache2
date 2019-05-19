package com.ssaw.ssawauthenticatecenterfeign.vo.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ssaw.ssawauthenticatecenterfeign.vo.TreeVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HuSen.
 * @date 2018/12/14 19:11.
 */
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID = -1554043858049185218L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    @NotBlank(message = "角色名称不能为空!")
    private String name;

    private String description;

    private List<TreeVO> permissions;

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
