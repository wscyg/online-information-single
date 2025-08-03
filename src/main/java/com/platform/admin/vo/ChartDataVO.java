package com.platform.admin.vo;

import lombok.Data;
import java.util.List;

/**
 * 图表数据VO
 */
@Data
public class ChartDataVO {
    private List<String> labels;
    private List<Object> data;
}