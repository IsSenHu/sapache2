package com.ssaw.ssawauthenticatecenterservice.details;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;

/**
 * @author HuSen.
 * @date 2018/12/11 9:55.
 */
@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 2000972919141399547L;
    private Long id;
    private String username;
    private String realName;
    private String description;
    private Boolean isEnable;
    private String password;
    private Boolean inner;
    private String otherInfo;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private Set<String> resourceIds;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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
        return true;
    }
}
