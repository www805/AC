package com.avst.accredit.common.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 安全管理器
     * @param myShiroRealm
     * @param ehCacheManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager  securityManager(@Qualifier("myShiroRealm") ShiroRealm myShiroRealm, @Qualifier("ehCacheManager") EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);//配置自定义验证类
        securityManager.setCacheManager(ehCacheManager);//配置缓存
        return securityManager;
    }

    /**
     * 设置缓存
     * @return
     */
    @Bean(value = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 创建自定义验证类
     * @return
     */
    @Bean(value = "myShiroRealm")
    public ShiroRealm myShiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }


    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * shiro工厂过滤
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){

        System.out.println("shiro工厂过滤");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * anon  无序认证（登录）可以访问
         * authc  必须认证才可以访问
         * user  如果使用rememberMe的功能可以直接访问
         * perms 该资源必须得到资源权限才可以访问
         * role 该资源必须得到角色权限才可以访问
         */

        //添加shiro内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        //设置无需登录就可以访问
        filterMap.put("/static/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/templates/**", "anon");
        filterMap.put("/loginCaChe", "anon");

        //设置必须要登录才可以访问的页面
        filterMap.put("/**", "authc");

        //授权过滤器
        filterMap.put("/getUser", "perms[user:getUser]");

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //成功调到的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

}
