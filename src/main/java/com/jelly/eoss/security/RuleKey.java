package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */
/**
 /index.html = anon
 /user/create = anon
 /user/** = authc
 /admin/** = authc, roles[administrator]
 /rest/** = authc, rest
 /remoting/rpc/** = authc, perms[remote:invoke]
 */
public enum RuleKey {
    anon, authc, roles, perms
}
