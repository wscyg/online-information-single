package com.platform.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "申请成为创作者请求")
public class CreatorApplyRequest {

    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "创作者类型", required = true)
    @NotBlank(message = "创作者类型不能为空")
    private String type;

    @Schema(description = "真实姓名", required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(description = "身份证号", required = true)
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "营业执照号")
    private String businessLicense;

    @Schema(description = "联系电话", required = true)
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @Schema(description = "联系邮箱")
    private String contactEmail;

    @Schema(description = "擅长领域")
    private String field;

    @Schema(description = "标签")
    private String tags;
}