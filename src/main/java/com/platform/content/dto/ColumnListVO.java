package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "专栏列表VO")
public class ColumnListVO {
    
    @Schema(description = "专栏ID")
    private Long id;
    
    @Schema(description = "专栏标题")
    private String title;
    
    @Schema(description = "专栏描述")
    private String description;
    
    @Schema(description = "封面图片")
    private String coverImage;
    
    @Schema(description = "价格")
    private BigDecimal price;
    
    @Schema(description = "原价")
    private BigDecimal originalPrice;
    
    @Schema(description = "浏览数")
    private Long viewCount;
    
    @Schema(description = "订阅数")
    private Long subscribeCount;
    
    @Schema(description = "作者")
    private String author;
    
    @Schema(description = "专栏名称")
    private String name;
    
    @Schema(description = "章节数量")
    private Integer chapterCount;
    
    @Schema(description = "分类")
    private String category;
    
    @Schema(description = "难度级别")
    private String level;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    
    public Long getViewCount() { return viewCount; }
    public void setViewCount(Long viewCount) { this.viewCount = viewCount; }
    
    public Long getSubscribeCount() { return subscribeCount; }
    public void setSubscribeCount(Long subscribeCount) { this.subscribeCount = subscribeCount; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getChapterCount() { return chapterCount; }
    public void setChapterCount(Integer chapterCount) { this.chapterCount = chapterCount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}