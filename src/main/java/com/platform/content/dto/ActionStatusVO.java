package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户行为状态VO")
public class ActionStatusVO {
    
    @Schema(description = "是否已点赞")
    private Boolean liked;
    
    @Schema(description = "是否已收藏")
    private Boolean favorited;
    
    @Schema(description = "是否已关注")
    private Boolean followed;
    
    // Getters and Setters
    public Boolean getLiked() { return liked; }
    public void setLiked(Boolean liked) { this.liked = liked; }
    
    public Boolean getFavorited() { return favorited; }
    public void setFavorited(Boolean favorited) { this.favorited = favorited; }
    
    public Boolean getFollowed() { return followed; }
    public void setFollowed(Boolean followed) { this.followed = followed; }
}