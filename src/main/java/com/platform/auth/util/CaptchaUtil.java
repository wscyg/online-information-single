package com.platform.auth.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 图片验证码工具类
 */
@Component
public class CaptchaUtil {
    
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    
    // 验证码字符池（去除容易混淆的字符）
    private static final String CODE_CHARS = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxy";
    
    private static final Random RANDOM = new Random();
    
    /**
     * 生成验证码图片
     * @return CaptchaResult 包含验证码文本和Base64图片
     */
    public CaptchaResult generateCaptcha() {
        // 创建画布
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // 生成验证码文本
        StringBuilder codeText = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length()));
            codeText.append(c);
        }
        
        // 绘制验证码字符
        drawCodeText(g, codeText.toString());
        
        // 添加干扰线
        drawInterferenceLines(g);
        
        // 添加噪点
        drawNoisePoints(g);
        
        g.dispose();
        
        // 转换为Base64
        String base64Image = imageToBase64(image);
        
        return new CaptchaResult(codeText.toString(), base64Image);
    }
    
    /**
     * 绘制验证码文本
     */
    private void drawCodeText(Graphics2D g, String codeText) {
        // 字体列表
        String[] fontNames = {"Arial", "Times New Roman", "Comic Sans MS", "Courier New"};
        
        for (int i = 0; i < codeText.length(); i++) {
            // 随机字体
            String fontName = fontNames[RANDOM.nextInt(fontNames.length)];
            
            // 随机字体大小
            int fontSize = 20 + RANDOM.nextInt(8);
            Font font = new Font(fontName, Font.BOLD, fontSize);
            g.setFont(font);
            
            // 随机颜色
            g.setColor(new Color(
                20 + RANDOM.nextInt(100),
                20 + RANDOM.nextInt(100), 
                20 + RANDOM.nextInt(100)
            ));
            
            // 计算字符位置
            int x = 15 + i * 22 + RANDOM.nextInt(8);
            int y = 25 + RANDOM.nextInt(8);
            
            // 随机旋转角度
            double angle = (RANDOM.nextDouble() - 0.5) * 0.4;
            g.rotate(angle, x, y);
            
            // 绘制字符
            g.drawString(String.valueOf(codeText.charAt(i)), x, y);
            
            // 恢复旋转
            g.rotate(-angle, x, y);
        }
    }
    
    /**
     * 绘制干扰线
     */
    private void drawInterferenceLines(Graphics2D g) {
        g.setStroke(new BasicStroke(1.5f));
        
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(
                100 + RANDOM.nextInt(100),
                100 + RANDOM.nextInt(100),
                100 + RANDOM.nextInt(100)
            ));
            
            int x1 = RANDOM.nextInt(WIDTH);
            int y1 = RANDOM.nextInt(HEIGHT);
            int x2 = RANDOM.nextInt(WIDTH);
            int y2 = RANDOM.nextInt(HEIGHT);
            
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    /**
     * 绘制噪点
     */
    private void drawNoisePoints(Graphics2D g) {
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(
                150 + RANDOM.nextInt(50),
                150 + RANDOM.nextInt(50),
                150 + RANDOM.nextInt(50)
            ));
            
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);
            
            g.fillOval(x, y, 2, 2);
        }
    }
    
    /**
     * 将图片转换为Base64字符串
     */
    private String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] bytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("生成验证码图片失败", e);
        }
    }
    
    /**
     * 验证码结果类
     */
    public static class CaptchaResult {
        private final String code;
        private final String image;
        
        public CaptchaResult(String code, String image) {
            this.code = code;
            this.image = image;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getImage() {
            return image;
        }
    }
}