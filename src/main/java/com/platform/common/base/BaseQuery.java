package com.platform.common.base;

import lombok.Data;

@Data
public class BaseQuery {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private Boolean asc = false;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
    
    public Integer getPageNum() {
        return pageNum == null || pageNum < 1 ? 1 : pageNum;
    }
    
    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}