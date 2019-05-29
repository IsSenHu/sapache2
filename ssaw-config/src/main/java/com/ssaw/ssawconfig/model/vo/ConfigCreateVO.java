package com.ssaw.ssawconfig.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author HuSen
 * @date 2019/5/28 15:39
 */
@Data
public class ConfigCreateVO {

    @NotBlank(message = "application require not null")
    private String application;

    @NotBlank(message = "label require not null")
    private String label;

    @NotBlank(message = "profile require not null")
    private String profile;

    @NotBlank(message = "text require not null")
    private String text;
}