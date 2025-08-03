package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "专栏更新请求")
public class ColumnUpdateRequest {
    
    @Schema(description = "专栏标题")
    private String title;
    
    @Schema(description = "专栏描述")
    private String description;
    
    @Schema(description = "封面图片URL")
    private String coverImage;
    
    @Schema(description = "价格")
    private Integer price;
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}