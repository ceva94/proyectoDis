/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.lambda;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.amazonaws.util.Base64;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Charlie
 */

public class prueba {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File file= new File("kefren.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String imagen64 = Base64.encodeAsString(bytes);
        System.out.println(imagen64);
        
        //Prueba con string base64
        byte[] data = Base64.decode(imagen64.getBytes());
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedImage bufferedInput, bufferedOutput;
        bufferedInput = ImageIO.read(bais);
        int width = bufferedInput.getWidth();
        int height = bufferedInput.getHeight();
			
        bufferedOutput = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        int px[] = new int[width * height];
        System.out.println(width);
        System.out.println(height);
        System.out.println(px[2]);
        System.out.println(px[3]);
        System.out.println(px[4]);
        System.out.println("despues");
        bufferedInput.getRGB(0, 0, width, height, px, 0, width);
        System.out.println(width);
        System.out.println(height);
        System.out.println(px[2]);
        System.out.println(px[3]);
        System.out.println(px[4]);
        bufferedOutput.setRGB(0, 0, width, height, px, 0, width);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file2 = new File("nuev.wbmp");
        
        //ImageIO.write(bufferedOutput, "gif",  file2);
        //ImageIO.write(bufferedOutput, "jpg",  file2);
        //ImageIO.write(bufferedOutput, "bmp",  file2);
        //ImageIO.write(bufferedOutput, "png",  file2);
        //ImageIO.write(bufferedOutput, "jpeg",  file2);
        //ImageIO.write(bufferedOutput, "wbmp",  file2); No funciono
        
        
	String imgBase64 = Base64.encodeAsString(baos.toByteArray());
        System.out.println(imgBase64);
         
        try{
            System.out.println("si");
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public static byte[] loadImage64(String url)throws Exception{
        File file= new File(url);
        if(file.exists()){
            int lenght = (int)file.length();
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[lenght];
            reader.read(bytes, 0, lenght);
            reader.close();
            return bytes;
        }else{
            System.out.println("No se pudo");
        return null;
        }
    }
}
