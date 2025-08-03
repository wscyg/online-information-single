package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "收入统计VO")
public class IncomeStatsVO {
    
    @Schema(description = "今日收入")
    private Long todayIncome;
    
    @Schema(description = "本月收入")
    private Long monthIncome;
    
    @Schema(description = "总收入")
    private Long totalIncome;
    
    // Getters and Setters
    public Long getTodayIncome() { return todayIncome; }
    public void setTodayIncome(Long todayIncome) { this.todayIncome = todayIncome; }
    
    public Long getMonthIncome() { return monthIncome; }
    public void setMonthIncome(Long monthIncome) { this.monthIncome = monthIncome; }
    
    public Long getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Long totalIncome) { this.totalIncome = totalIncome; }
}