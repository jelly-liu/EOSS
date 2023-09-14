package com.open.eoss.db.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * do not try to add or delete any property in this class
 */
public class PojoBase {
    /* the default value for pager */
    private Integer page;
    private Integer size;

    /* for sql: select * from dual limit offset,length */
    private Integer offset;
    private Integer length;

    /* for sql: either includeParams or excludeParams */
    private Set<String> includeParams;
    private Set<String> excludeParams;

    /* for sql: where name like xxx */
    private Set<String> likeParams;
    /* for sql: order by xxx */
    private Set<SortCondition> sortParams;
    /* for sql: set col = col + 111    or   sql set col = col + (-111) */
    private Set<IncCondition> incParams;
    /* for sql: where status in (1,2,3), the data in Map like: key=status, value=[1,2,3] */
    private Map<String, Collection<Object>> inParams;
    /* for sql: update table set column=? where name = ? and age = ? */
    private Map<String, Object> updateWhereParams;
    /* multiInsertOrUpdateWithNull，当主键冲突时，要执行update字段，用这个指定排除哪些字段*/
    private Set<String> multiInsertOrUpdateExcludeUpdateParams;


    /*--------------------------------- 对外辅助方法 ---------------------------------*/
    public void initPageAndSize(int pageNum, int size){
        if(this.page == null){
           this.page = pageNum;
        }
        if(this.size == null){
            this.size = size;
        }
    }


    /*--------------------------------- SQL辅助方法 ---------------------------------*/
    public PojoBase includeAdd(String ... propertyNames){
        if(propertyNames == null){
            return this;
        }

        for(String propertyName : propertyNames){
            if(includeParams == null) includeParams = new HashSet<>();
            includeParams.add(propertyName);
        }

        return this;
    }

    public PojoBase excludeAdd(String ... propertyNames){
        if(propertyNames == null){
            return this;
        }

        for(String propertyName : propertyNames){
            if(excludeParams == null) excludeParams = new HashSet<>();
            excludeParams.add(propertyName);
        }

        return this;
    }

    public PojoBase likeAdd(String ... propertyNames){
        if(propertyNames == null){
            return this;
        }

        for(String propertyName : propertyNames){
            if(likeParams == null) likeParams = new HashSet<>();
            likeParams.add(propertyName);
        }

        return this;
    }

    public PojoBase inAdd(String propertyName, Collection<Object> values){
        if(StringUtils.isEmpty(propertyName)){
            return this;
        }
        if(values == null || values.size() == 0){
            return this;
        }

        if(inParams == null) inParams = new HashMap();
        inParams.put(propertyName, values);
        return this;
    }

    public PojoBase orderByAdd(String propertyName, Sort sort){
        if(propertyName == null){
            return null;
        }
        if(sort == null){
            return null;
        }

        String colName = convertPropertyToColumn(propertyName);
        if(colName == null) return null;

        SortCondition condition = new SortCondition(colName, sort);
        if(sortParams == null) sortParams = new HashSet<>();
        sortParams.add(condition);
        return this;
    }

    public PojoBase incAdd(String property, Integer value){
        if(property == null){
            return null;
        }

        if(value == null){
            return null;
        }

        String colName = convertPropertyToColumn(property);
        IncCondition condition = new IncCondition(colName, value);

        if(incParams == null) incParams = new HashSet<>();
        incParams.add(condition);
        return this;
    }

    public PojoBase updateWhereAdd(String propertyName, Object value){
        if(StringUtils.isEmpty(propertyName)){
            return null;
        }

        if(updateWhereParams == null) updateWhereParams = new HashMap<>();
        updateWhereParams.put(propertyName, value);
        return this;
    }

    public PojoBase multiInsertOrUpdateExcludeUpdateParamsAdd(String ... propertyNames){
        if(propertyNames == null){
            return this;
        }

        for(String propertyName : propertyNames){
            if(multiInsertOrUpdateExcludeUpdateParams == null) multiInsertOrUpdateExcludeUpdateParams = new HashSet<>();
            multiInsertOrUpdateExcludeUpdateParams.add(propertyName);
        }

        return this;
    }


    /*--------------------------------- 辅助方法 ---------------------------------*/
    public void calculateOffsetLength(){
        this.offset = (page - 1) * size;
        this.length = size;
    }

    private String convertPropertyToColumn(String property){
        String[] ary = StringUtils.splitByCharacterTypeCamelCase(property);
        return StringUtils.join(ary, "_");
    }



    /*--------------------------------- 辅助类 ---------------------------------*/
    /* 排序方式 */
    public enum Sort{
        ASC, DESC
    }
    public static final class SortCondition{
        private String colName;
        private Sort sort;

        public String getColName() {
            return colName;
        }

        public void setColName(String colName) {
            this.colName = colName;
        }

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        /**
         * @param colName, table.column name
         * @param sort
         */
        public SortCondition(String colName, Sort sort) {
            this.colName = colName;
            this.sort = sort;
        }

        @Override
        public int hashCode() {
            return colName.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            SortCondition sortCondition = (SortCondition) obj;
            return StringUtils.equals(colName, sortCondition.colName);
        }
    }

    public static final class IncCondition {
        private String colName;
        private Integer value;

        public String getColName() {
            return colName;
        }

        public void setColName(String colName) {
            this.colName = colName;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public IncCondition(String colName, Integer value) {
            this.colName = colName;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return colName.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            IncCondition condition = (IncCondition) obj;
            return StringUtils.equals(colName, condition.colName);
        }
    }

    /*--------------------------------- getter and setter ---------------------------------*/
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOffset() {
        return offset;
    }

    public PojoBase setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public PojoBase setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Map<String, Collection<Object>> getInParams() {
        return inParams;
    }

    public void setInParams(Map<String, Collection<Object>> inParams) {
        this.inParams = inParams;
    }

    public Set<String> getLikeParams() {
        return likeParams;
    }

    public PojoBase setLikeParams(Set<String> likeParams) {
        this.likeParams = likeParams;
        return this;
    }

    public Set<SortCondition> getSortParams() {
        return sortParams;
    }

    public PojoBase setSortParams(Set<SortCondition> sortParams) {
        this.sortParams = sortParams;
        return this;
    }

    public Set<IncCondition> getIncParams() {
        return incParams;
    }

    public void setIncParams(Set<IncCondition> incParams) {
        this.incParams = incParams;
    }

    public Set<String> getIncludeParams() {
        return includeParams;
    }

    public void setIncludeParams(Set<String> includeParams) {
        this.includeParams = includeParams;
    }

    public Set<String> getExcludeParams() {
        return excludeParams;
    }

    public void setExcludeParams(Set<String> excludeParams) {
        this.excludeParams = excludeParams;
    }

    public Map<String, Object> getUpdateWhereParams() {
        return updateWhereParams;
    }

    public void setUpdateWhereParams(Map<String, Object> updateWhereParams) {
        this.updateWhereParams = updateWhereParams;
    }

    public Set<String> getMultiInsertOrUpdateExcludeUpdateParams() {
        return multiInsertOrUpdateExcludeUpdateParams;
    }

    public void setMultiInsertOrUpdateExcludeUpdateParams(Set<String> multiInsertOrUpdateExcludeUpdateParams) {
        this.multiInsertOrUpdateExcludeUpdateParams = multiInsertOrUpdateExcludeUpdateParams;
    }
}
