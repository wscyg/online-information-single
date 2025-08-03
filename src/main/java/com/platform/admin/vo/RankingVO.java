package com.platform.admin.vo;

import lombok.Data;

/**
 * 排行榜数据VO
 */
@Data
public class RankingVO {
    private String name;
    private Object value;
    private String extra;
}