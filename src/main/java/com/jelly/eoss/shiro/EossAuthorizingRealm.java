package com.jelly.eoss.shiro;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        boolean hasAuthorization = false;

        //load roles and permissions
        List<String> roleList = this.baseService.mySelectList("_EXT.Role_QueryByUserId", user.getId());
        List<String> permList = this.baseService.mySelectList("_EXT.Permission_QueryByUserId", user.getId());
        log.debug("load roles of user, userId={}, roles={}", user.getId(), roleList);
        log.debug("load perms of user, userId={}, perms={}", user.getId(), permList);

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
            throw new UnknownAccountException();
        }

        //pick out stored password and salt to AuthenticationInfo
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                new SimpleByteSource(user.getSalt()),
                this.getName());
        return authenticationInfo;
    }

    public static void main(String[] args) {
        SimpleHash simpleHash = new SimpleHash("MD5", "111111", "abc", 1);
        System.out.println(simpleHash.toString());
        System.out.println((System.currentTimeMillis() + "").length());
    }
}
