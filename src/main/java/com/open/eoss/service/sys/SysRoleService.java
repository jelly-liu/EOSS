package com.open.eoss.service.sys;

import com.open.eoss.db.entity.SysRole;
import com.open.eoss.db.entity.SysRolePerm;
import com.open.eoss.db.mapper.basic.SysRoleMapper;
import com.open.eoss.db.mapper.basic.SysRolePermMapper;
import com.open.eoss.exception.BusinessException;
import com.open.eoss.web.vo.EossRes;
import com.open.eoss.web.vo.RolePermParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class SysRoleService {
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysRolePermMapper rolePermMapper;

    public EossRes<List<SysRole>> list(RolePermParam v){
        v.calculateOffsetLength();
        v.likeAdd(SysRole.Property_name);

        Long totalRow = roleMapper.selectCount(v);
        List<SysRole> dataList = null;
        if(totalRow > 0){
            dataList = roleMapper.selectPage(v);
        }

        return EossRes.success(totalRow, dataList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txUpdate(RolePermParam v){
        if(v.getId() == null){
            throw new BusinessException("id 不能为空");
        }
        v.setUpdateTime(new Date());

        // 删除旧关系
        rolePermMapper.delete(new SysRolePerm().setRoleId(v.getId()));

        // 添加新关系
        if(!CollectionUtils.isEmpty(v.getPermIds())){
            List<SysRolePerm> list = new LinkedList<>();
            for(Integer permId : v.getPermIds()){
                SysRolePerm rolePerm = new SysRolePerm();
                rolePerm.setRoleId(v.getId());
                rolePerm.setPermId(permId);
                list.add(rolePerm);
            }
            this.rolePermMapper.insertMulti(list);
        }

        this.roleMapper.update(v);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txAdd(RolePermParam v){
        // 添加数据
        v.setCreateTime(new Date());
        v.setUpdateTime(new Date());
        this.roleMapper.insert(v);

        // 角色和权限关联
        if(!CollectionUtils.isEmpty(v.getPermIds())){
            List<SysRolePerm> rolePermList = new LinkedList<>();
            for(Integer permId : v.getPermIds()){
                SysRolePerm rolePerm = new SysRolePerm();
                rolePerm.setPermId(permId);
                rolePerm.setRoleId(v.getId());
                rolePermList.add(rolePerm);
            }
            rolePermMapper.insertMulti(rolePermList);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txDelete(List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            throw new BusinessException("ids 不能为这空");
        }
        for(Integer id : ids) {
            this.roleMapper.deleteByPk(id);
            this.rolePermMapper.delete(new SysRolePerm().setRoleId(id));
        }
    }
}
