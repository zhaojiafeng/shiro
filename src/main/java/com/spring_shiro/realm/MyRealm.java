package com.spring_shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 18/1/2.
 */
public class MyRealm extends AuthorizingRealm {


    @Override
    public String getName() {
        return "myRealm";
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    //    授权的方法，优先后写
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        授权。获取当前登录的用户，认证成功的用户信息
        String username = (String) principalCollection.getPrimaryPrincipal();

//            ========假数据========
        List<String> permission = new ArrayList<>();
        permission.add("user:create");
        permission.add("user:query");
//            ========假数据========

//        添加授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permission);

        return info;
    }


    //    认证的方法，优先先写，认证一定会抛出异常
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        获取用户输入的认证信息。获取用户名
        String userName = (String) authenticationToken.getPrincipal();

//        使用用户输入的用户名和数据库中做匹配查询
//        =========假数据开始========
        if (!"zhangsan".equals(userName)) {
            throw new UnknownAccountException("账号不存在");
        }
//        =========假数据结束========

//        获取用户输入的认证密码
        String password = new String((char[]) authenticationToken.getCredentials());
//        ==========假数据开始==============
        if (!"1234".equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }
//        ==========假数据结束===============
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
