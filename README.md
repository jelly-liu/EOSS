# EOSS With Apache Shiro
RBAC(Role Based Access Control) Project Use Spring MyBatis MySQL  
User, Role, Permission, Resource, Menu, FilterChainDefinition  
EOSS-Pure simulate but without apache shiro, Or EOSS-Shiro completely integrate with Apache Shiro  
If you want to known more about apache shiro  
view here [Apache Shiro](http://shiro.apache.org/index.html "Apache Shiro")  
view here [shiro-demo](https://github.com/jelly-liu/shiro-demo "shiro-demo")  
view here [EOSS-Shiro](https://github.com/jelly-liu/EOSS "EOSS-Shiro")

# About Master Branch
Master branch is sync with EOSS-Shiro branch

# About EOSS-Pure Branch
This branch just use apache shiro and ini config

# About EOSS-Shiro Branch
This is the EOSS completed integrated with Apache Shiro  
------------ integrated framework ------------  
--maven  
--mysql  
--mybatis  
--spring  
--shiro  
--redis  
------------ basic RBAC feature support ------------  
--support user[CRUD]  
----support user-role[CRUD]  
----support user-menu[CRUD]  
----support user-resource[CRUD]  
--support role[CRUD]  
----support role-permission[CRUD]  
--support permistion[CRUD]  
--support resource[CRUD]  
--support menu tree[CRUD]  
------------ shiro feature support ------------  
--support MD5 HashedCredentialsMatcher  
--support RolesOrAuthorizationFilter  
--support PermsOrAuthorizationFilter  
--support dynamic edit FilterChainDefinitions on line, do not need relogin or restart tomcat  
--support MemoryConstrainedCacheManager  
--support refresh Authentication cache, when update user or role, do not need relogin or restart tomcat  
--support redisCacheManager
--support sessionManager and sessionDAO

# Problem
--1 when use RedisCacheManager, found always put to redis  
--2 access normal, restart server, access normal, will always receive this error:DelegatingSession implementation requires that the SessionKey argument returns a non-null sessionId

# ScreenShot  
![ScreenShot1](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot1.png "ScreenShot1")  
![ScreenShot2](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot2.png "ScreenShot2")  
![ScreenShot3](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot3.png "ScreenShot3")  
