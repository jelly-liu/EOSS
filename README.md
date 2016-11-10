# EOSS
RBAC(Role Based Access Control) Project Use Spring MyBatis MySQL  
User, Role, Permission, Resource, FilterChainDefinition  
Simulate Apache Shiro, Or integration with Apache Shiro  
If you want to known about Apache Shiro  
view here [Apache Shiro](http://shiro.apache.org/index.html "Apache Shiro")  
view here [shiro-demo](https://github.com/jelly-liu/shiro-demo "shiro-demo")  
view here [EOSS-Shiro](https://github.com/jelly-liu/EOSS "EOSS-Shiro")

# About Master Branch
Master branch is sync with EOSS-Shiro branch

# About EOSS-Pure Branch
This is the default branch, also master branch, just use apache shiro and ini config

# About EOSS-Shiro Branch
This is the EOSS completed integrated with Apache Shiro  
------------ integrated framework ------------  
--integrate mysql  
--integrate mybatis  
--integrate spring  
--integrate apache shiro  
------------ funtional support ------------  
--support user[CRUD]  
----support user-role[CRUD]  
----support user-menu[CRUD]  
----support user-resource[CRUD]  
--support role[CRUD]  
----support role-permission[CRUD]  
--support permistion[CRUD]  
--support resource[CRUD]  
--support menu tree[CRUD]  
--support RolesOrAuthorizationFilter  
--support PermsOrAuthorizationFilter  
--support dynamic FilterChainDefinitions, change filter chain definitions on line, and do not need restart tomcat  
--support MemoryConstrainedCacheManager  
--support refresh AuthenticationInfoCache and AuthorizationInfoCache in local cache, when update user or role  
--more support in the future  

# ScreenShot  
![ScreenShot1](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot1.png "ScreenShot1")  
![ScreenShot2](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot2.png "ScreenShot2")  
![ScreenShot3](https://github.com/jelly-liu/EOSS/blob/master/ScreenShot3.png "ScreenShot3")  
