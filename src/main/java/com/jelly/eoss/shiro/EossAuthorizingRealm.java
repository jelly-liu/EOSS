package com.jelly.eoss.shiro;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jelly-liu on 2016/10/15.
 */
public class EossAuthorizingRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(EossAuthorizingRealm.class);

    @Resource
    BaseService baseService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.fromRealm(this.getName()).iterator().next();
        AuthorizationInfo authorizationInfo = this.doGetAuthorizationInfo(user.getId());
        return authorizationInfo;
    }

    private AuthorizationInfo doGetAuthorizationInfo(Integer userId){
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        boolean hasAuthorization = false;

        //load roles and permissions
        List<String> roleList = this.baseService.mySelectList("_EXT.Role_QueryByUserId", userId);
        List<String> permList = this.baseService.mySelectList("_EXT.Permission_QueryByUserId", userId);
        log.debug("load roles of user, userId={}, roles={}", userId, roleList);
        log.debug("load perms of user, userId={}, perms={}", userId, permList);

        if(roleList != null && roleList.size() > 0){
            hasAuthorization = true;
            for(String role : roleList){
                simpleAuthorInfo.addRole(StringUtils.trim(role));
            }
        }
        if(permList != null && permList.size() > 0){
            hasAuthorization = true;
            for(String perm : permList){
                simpleAuthorInfo.addStringPermission(StringUtils.trim(perm));
            }
        }

        if(hasAuthorization){
            return simpleAuthorInfo;
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        //only do check out user is exit or not, do not need do password matching
        User user = this.baseService.mySelectOne(User.Select, new User().setUsername(token.getUsername()));
        if(user == null){
            throw new UnknownAccountException("can not find user, name=" + token.getUsername());
        }

        if(user.getLocked() == 1){
            throw new LockedAccountException("account was locked, name=" + token.getUsername());
        }

        if(user.getDisabled() == 1){
            throw new DisabledAccountException("account was disabled, name=" + token.getUsername());
        }

        //pick out stored password and salt to AuthenticationInfo
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                new SimpleByteSource(user.getSalt()),
                this.getName());
        return authenticationInfo;
    }

    //refresh authentication info in local cache
    private void refreshAuthorizationInfo(PrincipalCollection principals, User user){
        AuthorizationInfo authorizationInfo = this.doGetAuthorizationInfo(user.getId());

        Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
        if (authorizationCache != null) {
            authorizationCache.remove(principals);
            authorizationCache.put(principals, authorizationInfo);
        }
    }

    //refresh authentication info in local cache
    private void refreshAuthenticationInfo(PrincipalCollection principals, User user){
        Cache<Object, AuthenticationInfo> authenticationCache = getAuthenticationCache();
        if(authenticationCache != null){
            authenticationCache.remove(principals);
            authenticationCache.put(principals, new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    new SimpleByteSource(user.getSalt()),
                    this.getName()));
        }
    }

    public void refreshAuthInfo(PrincipalCollection principals, User user){
        this.refreshAuthenticationInfo(principals, user);
        this.refreshAuthorizationInfo(principals, user);
    }

    public static void main(String[] args) {
        SimpleHash simpleHash = new SimpleHash("MD5", "111111", "683", 1);
        System.out.println(simpleHash.toString());
        System.out.println((System.currentTimeMillis() + "").length());
    }
}
