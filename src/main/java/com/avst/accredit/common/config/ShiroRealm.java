package com.avst.accredit.common.config;


import com.avst.accredit.common.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {
//    @Autowired
//    private Base_admininfoMapper base_admininfoMapper;
//    @Autowired
//    private Base_admintoroleMapper base_admintoroleMapper;
//    @Autowired
//    private Base_roletopermissionsMapper base_roletopermissionsMapper;
//
//    public static List<Base_permissions> menus=new ArrayList<Base_permissions>();


    /**
     * 执行授权逻辑（权限配置）
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        Base_admininfo user = (Base_admininfo) principalCollection.getPrimaryPrincipal();
//        if (null!=user){
//          EntityWrapper roleew=new EntityWrapper();
//          roleew.eq("ar.adminssid",user.getSsid());
//            roleew.eq("r.rolebool",1);//角色状态zhengc
//          List<Base_role> rolelist=base_admintoroleMapper.getRolesByAdminSsid(roleew);
//          if (null!=rolelist&&rolelist.size()>0){
//                for(Base_role role:rolelist){
//                    simpleAuthorizationInfo.addRole(role.getRolename());
//                    EntityWrapper funew=new EntityWrapper();
//                    funew.eq("rp.rolessid",role.getSsid());
//                    List<Base_permissions> funlist=base_roletopermissionsMapper.getPermissionsByRoleSsid(funew);
//                    if (null!=funlist&&funlist.size()>0){
//                        menus=new ArrayList<Base_permissions>();
//                        for(Base_permissions fun:funlist){
//                            String urls=fun.getUrl();
//                            if (StringUtils.isNotBlank(urls)){
//                                String[] result = urls.split(";");
//                                for (int i = 0; i < result.length; i++) {
//                                    if (StringUtils.isNotBlank(result[i])&&!result[i].equals("#")){
//                                        simpleAuthorizationInfo.addStringPermission(result[i]);
//                                    }
//                                }
//                            }
//                            if (null!=fun.getType()&&fun.getType().intValue()==1){
//                                menus.add(fun);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        //添加资源的授权字符串
        simpleAuthorizationInfo.addStringPermission("user:getUser");

        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑（身份验证，登录）
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken )authenticationToken;

        //假设数据库的用户名和密码
        String loginaccount = "admin";
        String password = "admin";
        Integer adminbool = 1;

        User user = new User();
        user.setLoginaccount(loginaccount);
        user.setPassword(password);
        user.setAdminbool(adminbool);

        if (StringUtils.isEmpty(user.getLoginaccount()) || StringUtils.isEmpty(user.getPassword())){  throw new UnknownAccountException();}

        if (user.getAdminbool()==1){
            SecurityUtils.getSubject().getSession().setAttribute(user.getLoginaccount(), user);
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getLoginaccount());
            SimpleAuthenticationInfo  simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    credentialsSalt,
                    getName()
            );
            return simpleAuthenticationInfo;
        }
        return null;
    }


    /**
     * 清空授权
     */
    public void clearCachedAuthorization(){
        clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 重载授权
     */
    public static void reloadAuthorizing() {
        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        ShiroRealm realm = (ShiroRealm)rsm.getRealms().iterator().next();
        realm.clearCachedAuthorization();
    }

}
