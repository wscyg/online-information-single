package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "创作者统计VO")
public class CreatorStatsVO {
    
    @Schema(description = "专栏数")
    private Integer columnCount;
    
    @Schema(description = "内容数")
    private Integer contentCount;
    
    @Schema(description = "粉丝数")
    private Integer followersCount;
    
    @Schema(description = "总收入")
    private Long totalIncome;
    
    // Getters and Setters
    public Integer getColumnCount() { return columnCount; }
    public void setColumnCount(Integer columnCount) { this.columnCount = columnCount; }
    
    public Integer getContentCount() { return contentCount; }
    public void setContentCount(Integer contentCount) { this.contentCount = contentCount; }
    
    public Integer getFollowersCount() { return followersCount; }
    public void setFollowersCount(Integer followersCount) { this.followersCount = followersCount; }
    
    public Long getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Long totalIncome) { this.totalIncome = totalIncome; }
}