package com.baixin.prayblog.shiro;

import cn.hutool.core.bean.BeanUtil;


import com.baixin.prayblog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangdada
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

//    @Autowired
//    IUserInfoService iUserInfoService;
    /**
     * supports：为了让realm支持jwt的凭证校验
    */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    /**
     * doGetAuthorizationInfo：授权
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("执行doGetAuthorizationInfo方法进行授权");
        log.info(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户名 account
        AccountProfile accountProfile = (AccountProfile)principals.getPrimaryPrincipal();
        String account = accountProfile.getAccount();
        //user role user_role permission role_permission
        //根据用户名 查 角色 user - user_role role
//        List<String> roleNames = iUserInfoService.getRolesByaccount(account);

        //根据 角色 查 权限 user - role - role_permission permission
//        info.addRoles(roleNames);

//        List<String> permissions = iUserInfoService.getPermissionsByaccount(account);
//        info.addStringPermissions(permissions);



        return info;
    }

    /**
     * 认证 doGetAuthenticationInfo：登录认证校验
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwt = (JwtToken) token;
        log.info("jwt----------------->{}", jwt);
        String userId =  jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        String account = (String) jwtUtils.getClaimByToken((String) jwt.getPrincipal()).get("account");
//        UserInfo user = iUserInfoService.getById(Integer.parseInt(userId));

//        if (user == null) {
//            throw new UnknownAccountException("账户不存在");
//        }
//
//        if (user.getStatus() == -1) {
//            throw new LockedAccountException("账户已被锁定");
//        }


        AccountProfile profile = new AccountProfile();
//        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());

        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());

    }
}
