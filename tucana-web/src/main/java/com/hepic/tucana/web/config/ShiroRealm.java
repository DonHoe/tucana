package com.hepic.tucana.web.config;

import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.service.SystemService;
import com.hepic.tucana.util.exception.BaseException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SystemService systemService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principal.getPrimaryPrincipal();
        //角色键赋值
        List<String> roleKeys = systemService.findRoleKeyByUserId(user.getId());
        authorizationInfo.setRoles(new HashSet<>(roleKeys));
        //菜单键赋值
        List<String> permits = systemService.findPermitByUserId(user.getId());
        authorizationInfo.setStringPermissions(new HashSet<>(permits));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userNameToken = (UsernamePasswordToken) token;
        String password = new String(userNameToken.getPassword());
        User user = systemService.selectSysUserByName(userNameToken.getUsername());
        try {
            if (user == null) {
                throw new BaseException(900, "用户不存在");
            }
            if (!user.getPassword().equals(systemService.encryptPassword(user.getUserName(), password, user.getSalt()))) {
                throw new BaseException(901, "密码不正确");
            }
        } catch (BaseException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
