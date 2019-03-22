# Need more help?
[wiki help](https://github.com/jelly-liu/EOSS/wiki)  

# EOSS With Apache Shiro
Base On RBAC(Role Based Access Control)  
If you want to known more about apache shiro  
view here [Apache Shiro](http://shiro.apache.org/index.html "Apache Shiro")  
view here [shiro-demo](https://github.com/jelly-liu/shiro-demo "shiro-demo")  
view here [EOSS-Shiro](https://github.com/jelly-liu/EOSS "EOSS-Shiro")

# Which Branch Should I Check Out
--**master branch, recommend this branch**  
----Master branch is sync with eoss-shiro-spring-boot branch  
--eoss-pure branch, not recommend  
----without shiro, but imitate shiro  
--eoss-shiro branch, not recommend  
----integrated with Apache Shiro  
--eoss-shiro-spring-boot branch, not recommend  
----integrated with spring boot 2.1.1, thymeleaf, shiro  
# more information
------------ login users ------------  
--admin/111111, has all privilege, CRUD  
--adminViewer/111111, only has view privilege  
------------ integrated framework ------------  
--maven  
--spring boot, latest version is 2.1.1  
--use spring thymeleaf instead of jsp  
--mysql(easy translate to oracle)  
--mybatis, mapper xml and mapper interface, latest version is 3.4.6   
--shiro, latest version is 1.4.0  
--redis cache, support  
------------ basic RBAC features ------------  
--support user[CRUD]  
----support user-role[CRUD]  
----support user-menu[CRUD]  
----support user-resource[CRUD]  
--support role[CRUD]  
----support role-permission[CRUD]  
--support permistion[CRUD]  
--support resource[CRUD]  
--support menu tree[CRUD]  
------------ shiro features ------------  
--sppport custom AntPathMatcher, do not care about capital or lower case of the request path, EossAntPathMatcher  
--support MD5 hashed with salt, HashedCredentialsMatcher  
--support RolesOrAuthorizationFilter, work with EossAntPathMatcher  
--support PermsOrAuthorizationFilter, work with EossAntPathMatcher  
--support after change rules, auto refresh all assosiate perms, do not need relogin or restart server  
--support refresh Authentication cache, when update user or role, do not need relogin or restart server  
--support Redis CacheManager  
--support session redis, if you set your redis cache manager, session will auto use your cache   

# ScreenShot  
![ScreenShot1](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot1.png "ScreenShot1")  
![ScreenShot2](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot2.png "ScreenShot2")  
![ScreenShot3](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot3.png "ScreenShot3")  
