package com.ssaw.ssawsso.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * @author HuSen.
 * @date 2018/12/10 10:08.
 */
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 用户权限 */
    private Collection<? extends GrantedAuthority> authorities;

    /** 是否可用 */
    private Boolean isEnable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
