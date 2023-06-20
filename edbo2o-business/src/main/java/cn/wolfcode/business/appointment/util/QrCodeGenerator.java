package cn.wolfcode.business.appointment.util;

import cn.wolfcode.common.utils.sign.Base64;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class QrCodeGenerator {
    public static Map<String,String> getCode() {
        String qrCodeData = "http://www.baidu.com"; // 二维码包含的数据
        int size = 250; // 二维码尺寸

        // 创建一个空白的图片对象
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        // 设置背景色为白色
        Graphics2D g = image.createGraphics();
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, size, size);

        // 设置字体和字体大小
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        // 绘制二维码数据
        g.setColor(Color.BLACK);
        g.drawString(qrCodeData, (size - g.getFontMetrics().stringWidth(qrCodeData)) / 2, 230);

        // 绘制二维码图形
        g.setColor(Color.BLACK);
        for (int y = 25; y < 225; y++) {
            for (int x = 25; x < 225; x++) {
                if (isQrCode(x, y, 25, 200, 200)) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        // 将图片写入文件
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image,"png",os);
            map.put("img", Base64.encode(os.toByteArray()));
        } catch (Exception e) {
            System.out.println("生成二维码失败: " + e.getMessage());
        }
        return map;
    }

    /**
     * 判断坐标点是否在二维码中
     */
    private static boolean isQrCode(int x, int y, int margin, int qrSize, int imageSize) {
        // 左上角的点
        if (x >= margin && x < margin + 25 && y >= margin && y < margin + 25) {
            return true;
        }
        // 右上角的点
        if (x >= imageSize - margin - 25 && x < imageSize - margin && y >= margin && y < margin + 25) {
            return true;
        }
        // 左下角的点
        if (x >= margin && x < margin + 25 && y >= imageSize - margin - 25 && y < imageSize - margin) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getCode());
    }
}