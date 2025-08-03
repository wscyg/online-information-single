package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "创作者更新请求")
public class CreatorUpdateRequest {
    
    @Schema(description = "创作者昵称")
    private String nickname;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "简介")
    private String bio;
    
    // Getters and Setters
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}