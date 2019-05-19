package com.ssaw.ssawauthenticatecenterfeign.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/2/26 10:29
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 7208951230047304571L;

    private String scope;

    private String index;

    private String to;

    private String title;

    private Template template;

    private List<MenuVO> items;

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Template {
        private String icon;
        private String title;
    }
}