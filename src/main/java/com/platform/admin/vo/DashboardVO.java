package com.platform.admin.vo;

import lombok.Data;

/**
 * 仪表盘数据VO
 */
@Data
public class DashboardVO {
    private Long totalUsers;
    private Long totalColumns;
    private Long totalOrders;
    private Double totalRevenue;
    private Long todayUsers;
    private Long todayOrders;
    private Double todayRevenue;
}