package com.ssaw.ssawauthenticatecenterfeign.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author HuSen.
 * @date 2019/1/7 16:15.
 */
@Setter
@Getter
public class ResourceProperties implements Serializable {
    private static final long serialVersionUID = 2131331614752412638L;
    private String antMatchers;
    private String scope;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
