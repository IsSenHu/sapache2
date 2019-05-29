package com.ssaw.ssawconfig.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author HuSen
 * @date 2019/5/28 15:39
 */
@Data
public class ConfigUpdateVO {

    @NotBlank(message = "appId require not null")
    private String appId;

    @NotBlank(message = "text require not null")
    private String text;
}