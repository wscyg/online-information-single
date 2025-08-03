package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "专栏查询请求")
public class ColumnQueryRequest {
    
    @Schema(description = "分类ID")
    private Long categoryId;
    
    @Schema(description = "关键词")
    private String keyword;
    
    @Schema(description = "页码")
    private Integer page = 1;
    
    @Schema(description = "每页大小")
    private Integer size = 10;
    
    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}