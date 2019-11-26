package com.avst.authorize.common.config.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
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
     * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
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
    public EhCacheManager ehCacheManager(@Qualifier("cacheManager2") CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    /***
     * 判断以前的缓存是否存在
     * @return
     */
    @Bean(name = "cacheManager2")
    public CacheManager ehCacheManagerFactoryBean() {
        CacheManager cacheManager = CacheManager.getCacheManager("users");
        if(cacheManager == null){
            try {
                cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:config/ehcache-shiro.xml"));
            } catch (IOException e) {
                throw new RuntimeException("initialize cacheManager failed");
            }
        }
        return cacheManager;
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

//        System.out.println("shiro工厂过滤");
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
        filterMap.put("/logout", "anon");
        filterMap.put("/ai", "anon");
        filterMap.put("/getai", "anon");

        //设置必须要登录才可以访问的页面
//        filterMap.put("/**", "authc");
        filterMap.put("/admin", "authc");
        filterMap.put("/base/tobasegninfo", "authc");
        filterMap.put("/base/tobasetype", "authc");
//        filterMap.put("/ac/**", "authc");

        //授权过滤器
//        filterMap.put("/getUser", "perms[user:getUser]");

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //成功调到的页面
        shiroFilterFactoryBean.setSuccessUrl("/admin");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

}
