package com.open.eoss.service.sys;

import com.open.eoss.db.entity.SysPerm;
import com.open.eoss.db.mapper.basic.SysPermMapper;
import com.open.eoss.exception.BusinessException;
import com.open.eoss.web.vo.EossRes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysPermService {
    @Autowired
    SysPermMapper permMapper;

    public EossRes<List<SysPerm>> list(SysPerm v){
        v.calculateOffsetLength();
        v.likeAdd(SysPerm.Property_name).likeAdd(SysPerm.Property_url).likeAdd(SysPerm.Property_urlSubmit);

        Long totalRow = permMapper.selectCount(v);
        List<SysPerm> dataList = null;
        if(totalRow > 0){
            dataList = permMapper.selectPage(v);
        }

        return EossRes.success(totalRow, dataList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txUpdate(SysPerm v){
        if(v.getId() == null){
            throw new BusinessException("id 不能为空");
        }

        SysPerm parent = permMapper.selectByPk(v.getPid());
        if(parent == null){
            throw new BusinessException("pid 无效");
        }
        // 更新path
        String path = parent.getPath() + "#" + v.getId();
        v.setPath(path);
        v.setUpdateTime(new Date());
        if(StringUtils.isBlank(v.getUrl())){
            v.setUrl(null);
        }
        if(StringUtils.isBlank(v.getUrlSubmit())){
            v.setUrlSubmit(null);
        }

        SysPerm old = permMapper.selectByPk(v.getId());
        v.setCreateTime(old.getCreateTime());
        v.setLevelNum(parent.getLevelNum() + 1);

        this.permMapper.updateWithNull(v);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txAdd(SysPerm v){
        SysPerm parent = permMapper.selectByPk(v.getPid());
        if(parent == null){
            throw new BusinessException("pid 无效");
        }

        // 添加数据
        v.setLevelNum(parent.getLevelNum() + 1);
        v.setCreateTime(new Date());
        v.setUpdateTime(new Date());
        this.permMapper.insert(v);

        // 更新path
        String path = parent.getPath() + "#" + v.getId();
        this.permMapper.update(new SysPerm().setId(v.getId()).setPath(path));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txDelete(List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            throw new BusinessException("ids 不能为这空");
        }
        for(Integer id : ids){
            this.permMapper.deleteByPk(id);
        }
    }
}
