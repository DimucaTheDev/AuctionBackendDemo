package com.example.ebat.auth;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

@Service
public class AvatarService {

    public byte[] generateIdenticon(String input, int size) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());

            Color mainColor = new Color(hash[0] & 0xFF, hash[1] & 0xFF, hash[2] & 0xFF);
            Color bgColor = new Color(240, 240, 240);

            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(bgColor);
            g.fillRect(0, 0, size, size);

            int cellSize = size / 5;
            for (int x = 0; x < 5; x++) {
                int column = x < 3 ? x : 4 - x;
                for (int y = 0; y < 5; y++) {
                    if ((hash[column + y] >> (column)) % 2 == 0) {
                        g.setColor(mainColor);
                        g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    }
                }
            }
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating avatar", e);
        }
    }
}