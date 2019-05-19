package com.ssaw.ssawauthenticatecenterservice.config.security;

import com.ssaw.ssawauthenticatecenterservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HuSen.
 * @date 2018/11/28 10:53.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private static final String LOGIN_PAGE = "/login";

    private static final String FORM_ACTION = "/authentication/form";

    private static final String LOGOUT_PAGE = "/logout";

    /** 静态资源 */
    private static final String BUILD = "/build/**";
    private static final String CSS = "/css/**";
    private static final String DIST = "/dist/**";
    private static final String FONT = "/font/**";
    private static final String JS = "/js/**";
    private static final String PLUGINS = "/plugins/**";

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(LOGIN_PAGE, LOGOUT_PAGE).permitAll();
        http.formLogin().loginPage(LOGIN_PAGE).loginProcessingUrl(FORM_ACTION).failureHandler(new MyAuthenticationFailHandler()).defaultSuccessUrl("/index", false);
        http.logout().logoutUrl(LOGOUT_PAGE).logoutSuccessUrl(LOGIN_PAGE);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        // 不去拦截这些静态资源
        web.ignoring().antMatchers(BUILD, CSS, DIST, FONT, JS, PLUGINS);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证失败处理器
     */
    public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
            String error;
            if (e instanceof BadCredentialsException) {
                error = "用户名或密码错误";
            } else if (e instanceof DisabledException) {
                error = "该帐号已被禁用";
            } else {
                error = "登录失败";
            }
            httpServletRequest.getSession(true).setAttribute("error", error);
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, LOGIN_PAGE.concat("?error=true"));
        }
    }
}
