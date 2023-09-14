
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.mapper.basic;

import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.open.eoss.db.entity.SysSample;

@Mapper
@Repository
public interface SysSampleMapper {

    public Long     selectCount(SysSample sysSample);
    public List<SysSample> selectPage(SysSample sysSample);
    public List<SysSample> select(SysSample sysSample);
    public SysSample       selectOne(SysSample sysSample);
    public List<SysSample> selectAll();
    public SysSample       selectByPk(Integer id);


    /** 主键或唯一索引冲突时，报错 */
    public int insert(SysSample sysSample);
    /** 主键或唯一索引冲突时，不报错，就丢弃此记录，但mysql有warning日志 */
    public int insertOrIgnore(SysSample sysSample);
    /** 主键或唯一索引冲突时，执行更新操作 */
    public int insertOrUpdate(SysSample sysSample);


    /**
    *  用1个insert插入多个value
    *  insert into table (col1,col2) values (v11,v12),(v21,v22),(v31,v32)
    *
    *  批量更新或插入多条时
    *    无事务时，失败的数据不影响正常的数据
    *    有事务时，失败的数据，会导致正常的数据也都回滚
    *  insert           into    table (col1,col2) values (v1,v2),(v3,v4)
    *  insert ignore    into    table (col1,col2) values (v1,v2),(v3,v4)
    *  insert           into    table (col1,col2) values (v1,v2),(v3,v4) on duplicate key update col1=123, col2=values(col2)
    */
    /** 1个insert多个values，主键或唯一索引冲突时，报错，所有记录回滚 */
    public int insertMulti(List<SysSample> list);
    /** 1个insert多个values，主键或唯一索引冲突时，不报错，丢弃冲突的记录，其它记录正常入库，但mysql有warning日志 */
    public int insertMultiOrIgnore(List<SysSample> list);
    /** 1个insert多个values，主键或唯一索引冲突时，执行更新操作，如果字段值为空，最终会被设置为null */
    /** 下面这两个dml语义不是太明确的，因为多个 values 时，update 的行为统一为1种，推荐使用 multiInsert 开头的方法 */
    public int insertMultiOrUpdateWithNull(List<SysSample> list);
    /** 1个insert多个values，主键或唯一索引冲突时，执行更新操作，如果字段值为空，最终会被设置为null，更新时，丢弃excludeProperties里的字段 */
    public int insertMultiOrUpdateWithNullExcludeUpdateProperty(List<SysSample> list, Collection<String> excludeProperties);


    /**
    *  multiInsert 性能比 insertMulti 差，使用时可以综合考量
    *  用多个 insert 插入多个 value，mysql 连接必须开启 allowMultiQueries=true
    *  insert into table (col1,col2) value (v11,v12);
    *  insert into table (col1,col2) value (v11,v12);
    */
    /** 多个insert，1个value，mysql连接必须开启 allowMultiQueries=true，主键或唯一索引冲突时，报错，所有记录回滚 */
    public int multiInsert(List<SysSample> list);
    public int multiInsertOrIgnore(List<SysSample> list);
    public int multiInsertOrUpdate(List<SysSample> list);
    public int multiInsertOrUpdateWithNull(List<SysSample> list);


    /** 注意，pk必须设置值，否则报错，其它字段值为null，则丢弃此字段的更新 */
    public int update(SysSample sysSample);
    /** 注意，pk必须设置值，否则报错，其它字段值为null，则mysql实际也会将该字段设置为null */
    public int updateWithNull(SysSample sysSample);
    /** 注意，pk必须设置值，否则报错，set column=column+? where id=? */
    public int updateWithInc(SysSample sysSample);
    /** 注意，pk必须设置值，否则报错，乐观锁，表必须有version字段, 否则报错 */
    public int updateWithVersion(SysSample sysSample);

    /** 注意，自定义更新条件，字段值为null，则丢弃此字段的更新 */
    public int updateWhere(SysSample sysSample);
    /** 注意，自定义更新条件，字段值为null，则mysql实际也会将该字段设置为null */
    public int updateWhereWithNull(SysSample sysSample);


    /**
    *  用多个 update 更新多个 value，mysql 连接必须开启 allowMultiQueries=true
    *  update table1 set col1=v1,col2=v2 where id=1;
    *  update table1 set col1=v1 where id=2;
    */
    /** 注意，pk必须设置值，否则报错，mysql 连接必须开启 allowMultiQueries=true */
    public int multiUpdate(List<SysSample> list);
    /** 注意，自定义更新条件，字段值为null，则丢弃此字段的更新，mysql连接必须开启 allowMultiQueries=true */
    public int multiUpdateWhere(List<SysSample> list);
    /** 注意，自定义更新条件，字段值为null，则mysql实际也会将该字段设置为null ，mysql连接必须开启 allowMultiQueries=true */
    public int multiUpdateWhereWithNull(SysSample sysSample);


    public int deleteByPk(Integer id);
    public int delete(SysSample sysSample);
    public int deleteAll();
}