package com.ssaw.ssawsso.service.impl;

import com.ssaw.commons.vo.CommonResult;
import com.ssaw.ssawsso.entity.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import static com.ssaw.commons.constant.Constants.ResultCodes.SUCCESS;

/**
 * @author HuSen.
 * @date 2018/12/10 10:06.
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//    private final UserFeign userFeign;
//
//    @Autowired
//    public UserDetailsServiceImpl(UserFeign userFeign) {
//        this.userFeign = userFeign;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        CommonResult<UserDto> commonResult = userFeign.findByUsername(username);
//        if(commonResult != null && SUCCESS == commonResult.getCode()) {
//            UserDto data = commonResult.getData();
//            UserDetailsImpl userDetails = new UserDetailsImpl();
//            userDetails.setUsername(data.getUsername());
//            userDetails.setPassword(data.getPassword());
//            userDetails.setIsEnable(data.getIsEnable());
//            userDetails.setAuthorities(new ArrayList<>(0));
//            return userDetails;
//        } else {
//            throw new UsernameNotFoundException("用户名或密码错误!");
//        }
        return null;
    }
}
