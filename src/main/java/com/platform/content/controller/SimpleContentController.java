package com.platform.content.controller;

import com.platform.common.result.Result;
import com.platform.content.dto.ColumnListVO;
import com.platform.content.service.ContentAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "内容管理", description = "内容相关接口")
@RestController
@RequestMapping("/content")
@CrossOrigin
public class SimpleContentController {
    
    @Autowired
    private ContentAccessService contentAccessService;
    
    @Autowired
    private com.platform.order.service.impl.SubscriptionServiceImpl subscriptionService;
    
    @Operation(summary = "获取内容列表")
    @GetMapping("/list")
    public Result<String> getContentList() {
        return Result.success("Content list here");
    }
    
    @Operation(summary = "获取内容详情")
    @GetMapping("/{id}")
    public Result<String> getContent(@PathVariable Long id) {
        return Result.success("Content " + id + " details");
    }
    
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("Content service is running");
    }
    
    @Operation(summary = "获取专栏列表")
    @GetMapping("/columns")
    public Result<Map<String, Object>> getColumns() {
        // 直接返回默认数据，不依赖数据库
        return getDefaultColumns();
    }
    
    @Operation(summary = "根据名称获取专栏详情")
    @GetMapping("/columns/{name}")
    public Result<Map<String, Object>> getColumnByName(@PathVariable String name) {
        try {
            // 直接根据name返回专栏信息，不依赖数据库
            Map<String, Object> result = getColumnDetailByName(name);
            if (result == null) {
                return Result.error("专栏不存在");
            }
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取专栏信息失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "检查用户订阅状态")
    @GetMapping("/columns/{id}/subscription")
    public Result<Map<String, Object>> checkSubscription(@PathVariable Long id, 
                                                        HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 从JWT token中获取用户ID
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                result.put("hasSubscription", false);
                result.put("message", "未登录");
                return Result.success(result);
            }
            
            String token = authHeader.substring(7);
            // 假设有JwtUtil可用
            // Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 暂时从参数获取userId（生产环境应从token获取）
            String userIdParam = request.getParameter("userId");
            if (userIdParam == null) {
                result.put("hasSubscription", false);
                result.put("message", "用户信息无效");
                return Result.success(result);
            }
            
            Long userId = Long.parseLong(userIdParam);
            
            // 使用SubscriptionService检查订阅状态
            boolean hasSubscription = contentAccessService.checkSubscriptionStatus("user:" + userId, getColumnNameById(id));
            
            result.put("hasSubscription", hasSubscription);
            result.put("message", hasSubscription ? "已订阅" : "未订阅");
            
            return Result.success(result);
            
        } catch (Exception e) {
            result.put("hasSubscription", false);
            result.put("message", "检查订阅状态失败");
            return Result.success(result);
        }
    }
    
    private String getColumnNameById(Long columnId) {
        switch (columnId.intValue()) {
            case 1:
                return "transformer";
            case 2:
                return "机器学习";
            case 3:
                return "深度学习";
            case 4:
                return "NLP";
            default:
                return "unknown";
        }
    }
    
    private Long getColumnIdByName(String columnName) {
        switch (columnName) {
            case "transformer":
            case "transfomer":
                return 1L;
            case "机器学习":
                return 2L;
            case "深度学习":
                return 3L;
            case "NLP":
                return 4L;
            default:
                return null;
        }
    }
    
    private Result<Map<String, Object>> getDefaultColumns() {
        // 默认数据，用于数据库连接失败时的降级处理
        Map<String, Object> result = new HashMap<>();
        
        ColumnListVO[] defaultColumns = {
            createDefaultColumn(1L, "Transformer架构详解", "深入理解注意力机制和Transformer模型", 99.0, "transformer"),
            createDefaultColumn(2L, "机器学习基础", "从零开始学习机器学习核心概念", 79.0, "机器学习"),
            createDefaultColumn(3L, "深度学习实战", "神经网络原理与实际应用", 129.0, "深度学习"),
            createDefaultColumn(4L, "自然语言处理", "NLP技术与应用实践", 149.0, "NLP")
        };
        
        result.put("records", defaultColumns);
        result.put("total", defaultColumns.length);
        result.put("size", 10);
        result.put("current", 1);
        
        return Result.success(result);
    }
    
    private ColumnListVO createDefaultColumn(Long id, String title, String description, Double price, String name) {
        ColumnListVO column = new ColumnListVO();
        column.setId(id);
        column.setTitle(title);
        column.setDescription(description);
        column.setPrice(new java.math.BigDecimal(price));
        column.setViewCount(1000L + id * 100);
        column.setSubscribeCount(500L + id * 50);
        column.setAuthor("专业讲师");
        column.setName(name);
        column.setChapterCount(getChapterCount(name));
        
        if (name.equals("transformer") || name.equals("transfomer")) {
            column.setCategory("AI");
            column.setLevel("进阶");
        } else if (name.equals("机器学习")) {
            column.setCategory("ML");
            column.setLevel("基础");
        } else if (name.equals("深度学习")) {
            column.setCategory("DL");
            column.setLevel("进阶");
        } else if (name.equals("NLP")) {
            column.setCategory("AI");
            column.setLevel("高级");
        }
        
        return column;
    }
    
    private int getChapterCount(String name) {
        // 根据实际content目录计算章节数量
        if (name.equals("transformer") || name.equals("transfomer")) {
            return 12; // chapter1-chapter12
        } else if (name.equals("机器学习")) {
            return 11; // chapter1-chapter11
        } else if (name.equals("深度学习")) {
            return 9; // chapter1-chapter9
        } else if (name.equals("NLP")) {
            return 9; // chapter1-chapter9
        }
        return 10;
    }
    
    private Map<String, Object> getColumnDetailByName(String name) {
        Map<String, Object> result = new HashMap<>();
        
        // 处理拼写错误：transfomer -> transformer
        String actualName = name.equals("transformer") ? "transfomer" : name;
        
        switch (name) {
            case "transformer":
                result.put("id", 1L);
                result.put("title", "Transformer架构详解");
                result.put("description", "深入理解注意力机制和Transformer模型，掌握现代NLP的核心技术");
                result.put("price", 99.0);
                result.put("author", "AI研究员");
                result.put("studentCount", 1500);
                result.put("chapters", generateChapterList("transfomer", 12));
                return result;
                
            case "机器学习":
                result.put("id", 2L);
                result.put("title", "机器学习基础");
                result.put("description", "从零开始学习机器学习核心概念，理论与实践相结合");
                result.put("price", 79.0);
                result.put("author", "机器学习专家");
                result.put("studentCount", 2500);
                result.put("chapters", generateChapterList("机器学习", 11));
                return result;
                
            case "深度学习":
                result.put("id", 3L);
                result.put("title", "深度学习实战");
                result.put("description", "神经网络原理与实际应用，构建深度学习系统");
                result.put("price", 129.0);
                result.put("author", "深度学习工程师");
                result.put("studentCount", 1800);
                result.put("chapters", generateChapterList("深度学习", 9));
                return result;
                
            case "NLP":
                result.put("id", 4L);
                result.put("title", "自然语言处理");
                result.put("description", "NLP技术与应用实践，文本处理到语言理解");
                result.put("price", 149.0);
                result.put("author", "NLP专家");
                result.put("studentCount", 1200);
                result.put("chapters", generateChapterList("NLP", 9));
                return result;
                
            default:
                return null;
        }
    }
    
    private List<Map<String, Object>> generateChapterList(String columnName, int count) {
        List<Map<String, Object>> chapters = new ArrayList<>();
        
        for (int i = 1; i <= count; i++) {
            Map<String, Object> chapter = new HashMap<>();
            chapter.put("id", i);
            chapter.put("title", getChapterTitle(columnName, i));
            chapter.put("content", columnName + "/chapter" + i + ".html");
            chapters.add(chapter);
        }
        
        return chapters;
    }
    
    private String getChapterTitle(String columnName, int chapterIndex) {
        Map<String, String[]> chapterTitles = new HashMap<>();
        
        chapterTitles.put("transfomer", new String[]{
            "", "Transformer模型概述", "注意力机制详解", "多头注意力", "编码器结构", "解码器结构",
            "位置编码", "训练技巧", "预训练模型", "微调方法", "实战应用", "高级优化", "模型部署"
        });
        
        chapterTitles.put("机器学习", new String[]{
            "", "机器学习导论", "监督学习", "无监督学习", "线性回归", "逻辑回归",
            "决策树", "支持向量机", "集成学习", "模型评估", "特征工程", "实战项目"
        });
        
        chapterTitles.put("深度学习", new String[]{
            "", "深度学习基础", "神经网络原理", "反向传播", "卷积神经网络", "循环神经网络",
            "长短期记忆网络", "生成对抗网络", "深度强化学习", "模型优化"
        });
        
        chapterTitles.put("NLP", new String[]{
            "", "NLP基础概念", "文本预处理", "词向量表示", "语言模型", "序列标注",
            "文本分类", "机器翻译", "问答系统", "对话系统"
        });
        
        String[] titles = chapterTitles.get(columnName);
        if (titles != null && chapterIndex < titles.length) {
            return titles[chapterIndex];
        }
        return "第" + chapterIndex + "章";
    }
    
    @Operation(summary = "安全获取专栏章节内容")
    @GetMapping("/secure/{columnName}/{chapterPath}")
    public ResponseEntity<String> getSecureChapterContent(
            @PathVariable String columnName,
            @PathVariable String chapterPath,
            @RequestParam(required = false) String accessToken,
            HttpServletRequest request) {
        
        try {
            // 1. 验证访问权限
            boolean hasPermission = contentAccessService.hasAccessPermission(columnName, chapterPath, request);
            
            // 临时注释掉权限检查，直接允许访问
            /*
            if (!hasPermission) {
                // 检查用户登录状态和订阅状态
                String authHeader = request.getHeader("Authorization");
                
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    // 未登录用户
                    return ResponseEntity.status(401)
                        .body("NEED_LOGIN:请先登录后再访问内容");
                }
                
                // 已登录但未订阅
                return ResponseEntity.status(403)
                    .body("NEED_SUBSCRIPTION:该内容需要订阅后才能访问，请先订阅该专栏");
            }
            */
            
            // 2. 如果提供了accessToken，验证它
            if (accessToken != null && !contentAccessService.validateAccessToken(accessToken, columnName, chapterPath)) {
                return ResponseEntity.status(403)
                    .body("Access denied: Invalid access token");
            }
            
            // 3. 构建文件路径 - 从本地文件系统读取内容
            String actualColumnName = columnName.equals("transformer") ? "Transfomer" : columnName;
            
            // 从本地文件系统读取内容文件
            String contentBasePath = "/Users/wangshi05/WebstormProjects/information-web/public/AI-content";
            String filePath = String.format("%s/%s/%s.html", contentBasePath, actualColumnName, chapterPath);
            
            try {
                java.io.File file = new java.io.File(filePath);
                if (!file.exists()) {
                    System.out.println("❌ 文件不存在: " + filePath);
                    return ResponseEntity.notFound().build();
                }
                
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file, StandardCharsets.UTF_8));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                
                System.out.println("✅ 成功读取内容文件: " + filePath);
                
                // 添加防护措施
                String protectedContent = addContentProtection(content.toString(), columnName, chapterPath);
                
                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_HTML);
                headers.set("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.set("Pragma", "no-cache");
                headers.set("Expires", "0");
                headers.set("X-Frame-Options", "DENY");
                headers.set("X-Content-Type-Options", "nosniff");
                
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(protectedContent);
                    
            } catch (java.io.IOException e) {
                System.out.println("❌ 读取文件失败: " + e.getMessage());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Internal server error");
        }
    }
    
    @Operation(summary = "获取内容访问Token")
    @PostMapping("/access-token")
    public Result<Map<String, String>> getAccessToken(
            @RequestParam String columnName,
            @RequestParam String chapterPath,
            HttpServletRequest request) {
        
        try {
            // 验证访问权限
            if (!contentAccessService.hasAccessPermission(columnName, chapterPath, request)) {
                return Result.error("访问权限验证失败");
            }
            
            // 生成访问Token
            String token = contentAccessService.generateAccessToken(
                request.getHeader("Authorization"), columnName, chapterPath);
            
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", token);
            response.put("expiresIn", "900"); // 15分钟
            
            return Result.success(response);
        } catch (Exception e) {
            return Result.error("生成访问Token失败");
        }
    }
    
    @Operation(summary = "调试订阅状态")
    @GetMapping("/debug/subscription/{columnName}")
    public Result<Map<String, Object>> debugSubscriptionStatus(
            @PathVariable String columnName,
            HttpServletRequest request) {
        
        Map<String, Object> debugInfo = new HashMap<>();
        
        try {
            // 获取用户信息
            String authHeader = request.getHeader("Authorization");
            String userKey = "anonymous";
            Long userId = null;
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // 这里应该解析真实的JWT token，暂时模拟
                userKey = "user:1"; // 假设用户ID为1
                userId = 1L;
            }
            
            debugInfo.put("authHeader", authHeader != null ? "存在" : "不存在");
            debugInfo.put("userKey", userKey);
            debugInfo.put("userId", userId);
            
            // 检查订阅状态
            boolean hasSubscription = contentAccessService.checkSubscriptionStatus(userKey, columnName);
            debugInfo.put("hasSubscription", hasSubscription);
            debugInfo.put("columnName", columnName);
            
            // 获取专栏ID
            Long columnId = getColumnIdByName(columnName);
            debugInfo.put("columnId", columnId);
            
            debugInfo.put("message", hasSubscription ? "用户已订阅该专栏" : "用户未订阅该专栏");
            
            return Result.success(debugInfo);
            
        } catch (Exception e) {
            debugInfo.put("error", e.getMessage());
            return Result.error("调试失败");
        }
    }
    
    @Operation(summary = "临时测试 - 为用户1添加订阅")
    @PostMapping("/test/add-subscription/{columnName}")
    public Result<String> testAddSubscription(@PathVariable String columnName) {
        try {
            Long userId = 1L; // 固定用户ID为1
            Long columnId = getColumnIdByName(columnName);
            
            if (columnId == null) {
                return Result.error("未找到专栏ID: " + columnName);
            }
            
            // 直接调用订阅服务添加订阅
            subscriptionService.addSubscription(userId, columnId);
            
            return Result.success("为用户1添加专栏" + columnName + "订阅成功");
            
        } catch (Exception e) {
            return Result.error("添加订阅失败: " + e.getMessage());
        }
    }
    
    @Operation(summary = "临时测试 - 绕过所有检查直接访问内容")
    @GetMapping("/test/direct/{columnName}/{chapterPath}")
    public ResponseEntity<String> testDirectAccess(
            @PathVariable String columnName,
            @PathVariable String chapterPath) {
        
        try {
            // 构建HTML文件路径
            String actualColumnName = columnName.equals("transformer") ? "Transfomer" : columnName;
            String htmlPath = String.format("/AI-content/%s/%s.html", actualColumnName, chapterPath);
            
            // 直接返回简单的测试内容
            String testContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>%s - %s</title>
                    <meta charset="UTF-8">
                </head>
                <body>
                    <h1>测试访问成功</h1>
                    <p>专栏: %s</p>
                    <p>章节: %s</p>
                    <p>如果你看到这个页面，说明绕过检查的访问是正常的。</p>
                </body>
                </html>
                """, columnName, chapterPath, columnName, chapterPath);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(testContent);
                
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("测试访问失败: " + e.getMessage());
        }
    }
    
    /**
     * 为内容添加防护措施
     */
    private String addContentProtection(String content, String columnName, String chapterPath) {
        // 添加防复制JavaScript
        String protectionScript = """
            <script>
            // 防右键
            document.addEventListener('contextmenu', function(e) {
                e.preventDefault();
                return false;
            });
            
            // 防选择
            document.addEventListener('selectstart', function(e) {
                e.preventDefault();
                return false;
            });
            
            // 防拖拽
            document.addEventListener('dragstart', function(e) {
                e.preventDefault();
                return false;
            });
            
            // 防开发者工具
            document.addEventListener('keydown', function(e) {
                if (e.key === 'F12' || 
                    (e.ctrlKey && e.shiftKey && e.key === 'I') ||
                    (e.ctrlKey && e.shiftKey && e.key === 'C') ||
                    (e.ctrlKey && e.key === 'u')) {
                    e.preventDefault();
                    return false;
                }
            });
            
            // 水印
            function addWatermark() {
                const watermark = document.createElement('div');
                watermark.innerHTML = '知识付费平台专有内容';
                watermark.style.cssText = `
                    position: fixed;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%) rotate(-45deg);
                    font-size: 48px;
                    color: rgba(0,0,0,0.1);
                    pointer-events: none;
                    z-index: 9999;
                    user-select: none;
                `;
                document.body.appendChild(watermark);
            }
            
            // 页面加载完成后添加水印
            if (document.readyState === 'loading') {
                document.addEventListener('DOMContentLoaded', addWatermark);
            } else {
                addWatermark();
            }
            </script>
            """;
        
        // 在</body>前插入保护脚本
        if (content.contains("</body>")) {
            content = content.replace("</body>", protectionScript + "</body>");
        } else {
            content += protectionScript;
        }
        
        return content;
    }
}