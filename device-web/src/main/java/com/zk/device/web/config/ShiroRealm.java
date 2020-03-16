package com.zk.device.web.config;

import com.zk.device.model.shiro.User;
import com.zk.device.service.SystemService;
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
        User user = systemService.selectSysUserByLoginName(userNameToken.getUsername());
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getPassword() == null || !user.getPassword().equals(systemService.encryptPassword(user.getLoginName(), password, user.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
