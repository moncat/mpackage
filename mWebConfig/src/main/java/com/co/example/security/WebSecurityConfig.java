package com.co.example.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import com.co.example.constant.CookieConstant;

/**
 * 主配置文件
 * @author zyl
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private DataSource dataSource;
	
	@Autowired
	CustomAuthenticationDetailsSource authenticationDetailsSource;
	@Autowired
	
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

	
	@Autowired
    private SessionRegistry sessionRegistry;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

		http
		.headers().frameOptions().disable()
//			增加过滤器 
		.and()
//        .addFilter(new CaptchaAuthenticationFilter())
//        .addFilterAfter(filter, afterFilter)
//        .addFilterAt(filter, atFilter)
//          .addFilter(new MultipartFilter())
	      .addFilterAt(new ConcurrentSessionFilter(sessionRegistry,sessionInformationExpiredStrategy()),ConcurrentSessionFilter.class)
//	      .addFilterBefore(new MultipartFilter(), ConcurrentSessionFilter.class)
		  
//			应用配置        
//        .apply(configurer)
				
//                .authenticationProvider(customAuthenticationProvider())
//                .build()
//                .cors()
//                .csrf()
//                .exceptionHandling().accessDeniedPage("/error") //统一异常处理
//                .getOrBuild()
//                .headers()
//                .httpBasic() //http basic认证
//                .jee()
//                .objectPostProcessor(objectPostProcessor)
//                .openidLogin()
//                  .portMapper() //重定向端口
//                  .and().requestCache()
//                .requestMatcher(requestMatcher) // 所有matcher 底层包
//                .requiresChannel()
//                .securityContext()
//                .servletApi()
//                .setSharedObject(sharedType, new FormLoginConfigurer())// 一个可以共享的对象
//                .userDetailsService(userDetailsService) //自定义一个
//                .x509() //认证方式
                .authorizeRequests()
//                .antMatchers("/").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()     
                .and()               
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .successHandler(loginSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
                .authenticationDetailsSource(authenticationDetailsSource)
                
                //session              
                .and()
				.sessionManagement()
				.sessionAuthenticationErrorUrl("/login")
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                .authenticationSuccessHandler(loginSuccessHandler())
                .rememberMeParameter("remember-me")
                
                //设置cookie有效期
//                .tokenValiditySeconds(60)
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .tokenRepository(tokenRepository())
                .and()
                .logout()
//                .addLogoutHandler(logoutHandler)
                .deleteCookies(CookieConstant.COOKIE_ADMIN_KEY)
                .deleteCookies(CookieConstant.COOKIE_REMEMBER_ME)
                .logoutSuccessUrl("/login")
                .permitAll()
                //成功退出后的操作
//                .logoutSuccessHandler(logoutSuccessHandler())
                //销毁session
                .invalidateHttpSession(true)
                .and().csrf().requireCsrfProtectionMatcher(new CsrfSecurityRequestMatcher())
                ;
        
		
    }
    
   
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**","/base/**","/css/**","/js/**","/img/**","/identifyCode");
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    	    .userDetailsService(customUserDetailsService())
            .passwordEncoder(passwordEncoder());
//            auth.authenticationProvider(customAuthenticationProvider());
    	auth.eraseCredentials(false);  
    }
    
    
    
  //session失效跳转
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    
//    @Bean
//    public AuthenticationProvider customAuthenticationProvider(){
//    	return new CustomAuthenticationProvider();
//    }
    
    // remember-me 记住我，该表操作被spring security写死，需要注入数据源
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){        
        JdbcTokenRepositoryImpl jdbc=new JdbcTokenRepositoryImpl();
        jdbc.setDataSource(dataSource);
        return jdbc;
    }
    

    /**
     * 设置用户密码的加密方式为MD5加密
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }
    
    
    
    @Bean  
    public LoginSuccessHandler loginSuccessHandler(){  
        return new LoginSuccessHandler();  
    } 
    
    @Bean  
    public LogoutSuccessHandler logoutSuccessHandler(){  
    	return new LogoutSuccessHandler();  
    } 
    
   
}