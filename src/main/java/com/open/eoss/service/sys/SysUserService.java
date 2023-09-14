package com.open.eoss.service.sys;

import com.open.eoss.db.entity.SysUser;
import com.open.eoss.db.entity.SysUserPerm;
import com.open.eoss.db.entity.SysUserRole;
import com.open.eoss.db.mapper.basic.SysUserMapper;
import com.open.eoss.db.mapper.basic.SysUserPermMapper;
import com.open.eoss.db.mapper.basic.SysUserRoleMapper;
import com.open.eoss.exception.BusinessException;
import com.open.eoss.util.Const;
import com.open.eoss.util.security.Digest;
import com.open.eoss.web.vo.EossRes;
import com.open.eoss.web.vo.UserRoleParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class SysUserService {
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysUserRoleMapper userRoleMapper;
    @Autowired
    SysUserPermMapper userPermMapper;

    public EossRes<List<SysUser>> list(SysUser v){
        v.calculateOffsetLength();
        v.likeAdd(SysUser.Property_username);

        Long totalRow = userMapper.selectCount(v);
        List<SysUser> dataList = null;
        if(totalRow > 0){
            dataList = userMapper.selectPage(v);
        }

        return EossRes.success(totalRow, dataList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txUpdate(UserRoleParam v){
        if(v.getId() == null){
            throw new BusinessException("id 不能为空");
        }
        if(v.getId().equals(Const.RobotUserId)){
            throw new BusinessException("id为1的Robot账号不能更新，请重新选择");
        }

        // 如果密码不为空，就更新密码
        if(StringUtils.isNotBlank(v.getPassword())){
            String salt = new Date().getTime() + "";
            String passwordMd5WithSalt = Digest.MD5(v.getPassword(), salt);
            v.setPassword(passwordMd5WithSalt);
            v.setSalt(salt);
        }

        v.setUpdateTime(new Date());
        this.userMapper.update(v);

        // 更新用户拥有的权限
        this.userRoleMapper.delete(new SysUserRole().setUserId(v.getId()));
        if(!CollectionUtils.isEmpty(v.getRoleIds())){
            List<SysUserRole> list = new LinkedList<>();
            for(Integer roleId : v.getRoleIds()){
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(v.getId());
                userRole.setRoleId(roleId);
                list.add(userRole);
            }
            this.userRoleMapper.insertMulti(list);
        }

        this.userPermMapper.delete(new SysUserPerm().setUserId(v.getId()));
        // 更新用户拥有的权限
        if(!CollectionUtils.isEmpty(v.getPermIds())){
            List<SysUserPerm> list = new LinkedList<>();
            for(Integer permId : v.getPermIds()){
                SysUserPerm userPerm = new SysUserPerm();
                userPerm.setUserId(v.getId());
                userPerm.setPermId(permId);
                list.add(userPerm);
            }
            this.userPermMapper.insertMulti(list);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txAdd(UserRoleParam v){
        // 添加数据
        String salt = new Date().getTime() + "";
        String passwordMd5WithSalt = Digest.MD5(v.getPassword(), salt);

        v.setPassword(passwordMd5WithSalt);
        v.setSalt(salt);
        v.setCreateTime(new Date());
        v.setUpdateTime(new Date());
        this.userMapper.insert(v);

        // 用户拥有的角色
        if(!CollectionUtils.isEmpty(v.getRoleIds())){
            List<SysUserRole> list = new LinkedList<>();
            for(Integer roleId : v.getRoleIds()){
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(v.getId());
                userRole.setRoleId(roleId);
                list.add(userRole);
            }
            this.userRoleMapper.insertMulti(list);
        }

        // 用户拥有的权限
        if(!CollectionUtils.isEmpty(v.getPermIds())){
            List<SysUserPerm> list = new LinkedList<>();
            for(Integer permId : v.getPermIds()){
                SysUserPerm userPerm = new SysUserPerm();
                userPerm.setUserId(v.getId());
                userPerm.setPermId(permId);
                list.add(userPerm);
            }
            this.userPermMapper.insertMulti(list);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txDelete(List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            throw new BusinessException("ids 不能为这空");
        }

        for(Integer id : ids){
            if(id.equals(Const.RobotUserId)){
                throw new BusinessException("id为1的Robot账号不能删除，请重新选择");
            }
        }

        for(Integer id : ids){
            this.userMapper.deleteByPk(id);
            // 删除用户拥有的角色
            this.userRoleMapper.delete(new SysUserRole().setUserId(id));
            // 删除用户拥有的权限
            this.userPermMapper.delete(new SysUserPerm().setUserId(id));
        }
    }
}
