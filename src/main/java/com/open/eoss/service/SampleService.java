package com.open.eoss.service;

import com.open.eoss.db.entity.PojoBase;
import com.open.eoss.db.entity.SysSample;
import com.open.eoss.db.entity.SysUserRole;
import com.open.eoss.db.mapper.basic.SysSampleMapper;
import com.open.eoss.db.mapper.basic.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class SampleService {
    @Autowired
    SysSampleMapper sampleMapper;
    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void txTest() {
        SysSample update = null;

        update = new SysSample();
        update.setInfo("{\"type\":\"india\"}");
        update.inAdd(SysSample.Property_id, Arrays.asList(1,2)).updateWhereAdd(SysSample.Property_name, "tom");
        sampleMapper.update(update);
    }

    public void select(){
        SysSample query = null;
        SysSample one = null;
        List<SysSample> multi = null;

        // 根据主键查询
        one = sampleMapper.selectByPk(1);

        // 根据Pojo查询，多个条件
        query = new SysSample();
        query.setName("tom").setWeight(100);
        one = sampleMapper.selectOne(query);

        // 根据where in查询，in优先，有 in 就不会执行 =
        query = new SysSample();
        query.inAdd(SysSample.Property_id, Arrays.asList(1,2));
        query.setName("tom");
        multi = sampleMapper.select(query);

        // 根据like查询，like优先，有 like 就不会执行 =
        query = new SysSample();
        query.likeAdd(SysSample.Property_name);
        query.setName("tom");
        multi = sampleMapper.select(query);

        // 排序
        query = new SysSample();
        query.orderByAdd(SysSample.Property_name, PojoBase.Sort.ASC);
        query.setWeight(100);
        multi = sampleMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(){
        SysSample update = null;

        // -------------------- 根据主键进行更新 --------------------//
        // 没有值的字段，丢弃
        update = new SysSample();
        update.setId(1);
        update.setWeight(300);
        sampleMapper.update(update);

        // 没有值的字段，最终会设置为null
        update = sampleMapper.selectByPk(1);
        update.setInfo(null);
        sampleMapper.updateWithNull(update);

        // 根据主键，设置 weight = weight + 100
        update = new SysSample();
        update.setId(1);
        update.incAdd(SysSample.Property_weight, 100);
        sampleMapper.updateWithInc(update);

        // 乐观锁，表必须有version字段
        update = new SysSample();
        update.setId(1);
        update.setVersion(2);
        sampleMapper.updateWithVersion(update);

        // -------------------- 根据自字义条件，更新 --------------------//

        // 根据自字义条件，更新
        update = new SysSample();
        update.setWeight(300);
        update.updateWhereAdd(SysSample.Property_id, 1);
        sampleMapper.updateWhere(update);

        // 根据自字义条件，更新
        update = new SysSample();
        update.setWeight(300);
        update.updateWhereAdd(SysSample.Property_id, 1);
        update.updateWhereAdd(SysSample.Property_name, "tom");
        sampleMapper.updateWhere(update);

        // 根据自字义条件，更新，in优先，有 in 就不会执行 =
        update.setInfo("{\"type\":\"india\"}");
        update.inAdd(SysSample.Property_id, Arrays.asList(1,2));
        sampleMapper.updateWhere(update);

        // 根据自字义条件，更新，in优先，有 in 就不会执行 =
        update.setInfo("{\"type\":\"india\"}");
        update.inAdd(SysSample.Property_id, Arrays.asList(1,2));
        update.updateWhereAdd(SysSample.Property_name, "tom");
        sampleMapper.updateWhere(update);

        // 根据自字义条件，更新为null
        update = sampleMapper.selectByPk(1);
        update.setInfo(null);
        update.updateWhereAdd(SysSample.Property_id, 1);
        update.updateWhereAdd(SysSample.Property_name, "tom");
        sampleMapper.updateWhereWithNull(update);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(){
        SysSample param = null;

        // 根据主键删除
        sampleMapper.deleteByPk(1);

        // 删除此表所有数据
        sampleMapper.deleteAll();

        // 根据自定义条件，删除
        param = new SysSample();
        param.setId(1);
        param.setName("tom");
        sampleMapper.delete(param);

        // 根据自定义条件，删除，in优先，有 in 就不会执行 =
        param = new SysSample();
        param.inAdd(SysSample.Property_id, Arrays.asList(1,2));
        param.setName("tom");
        sampleMapper.delete(param);
    }

    public void testWhere(){
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(20);
        List<SysUserRole> list = userRoleMapper.select(userRole);
        int i = 0;
    }
}
