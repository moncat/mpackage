package com.co.example.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

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

	
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
	@Override
	protected UserDetailsService userDetailsService() {		
		return super.userDetailsService();
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
//        .addFilterBefore(filter, beforeFilter)
	      .addFilterAt(new ConcurrentSessionFilter(sessionRegistry,sessionInformationExpiredStrategy()),ConcurrentSessionFilter.class)
		  
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
                .antMatchers(
                		"/*"
                		,"/register/**"  //注册
                		,"/carousel/**"  //轮播图
                		,"/recommend/**"  //推荐产品
                		,"/contrast/**"  //产品对比
                		,"/product/**"  //产品
                		,"/ingredient/**"  //成分
                		,"/ad/**"  //首页广告位
                		,"/wechat/**"  //微信自动登录回调相关接口
                		).permitAll()
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
//                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                .authenticationSuccessHandler(loginSuccessHandler())
//                .rememberMeParameter(CookieConstant.COOKIE_REMEMBER_ME)
                
                //设置cookie有效期
//                .tokenValiditySeconds(60)
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .tokenRepository(tokenRepository())
                .and()
                .logout()
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(CookieConstant.COOKIE_USER_KEY)
                .logoutSuccessUrl("/login")
                .permitAll()
                //成功退出后的操作
//                .logoutSuccessHandler(logoutSuccessHandler())
                //销毁session
                .invalidateHttpSession(true)
                ;
        
		
    }
    
   
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**","/base/**","/css/**","/js/**","/img/**","/identifyCode");
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/webjars/**");
       
    }



	//session失效跳转
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    

    
    // remember-me 记住我，该表操作被spring security写死，需要注入数据源
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){        
        JdbcTokenRepositoryImpl jdbc=new JdbcTokenRepositoryImpl();
        jdbc.setDataSource(dataSource);
        return jdbc;
    }


    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}

	/**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean("userDetailsService")
    public UserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }
    
    /**
     * 设置用户密码的加密方式为MD5加密
     */
    @Bean("passwordEncoder")
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
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