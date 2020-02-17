/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.util.Base64;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.imageio.ImageIO;
import org.json.JSONObject;

/**
 *
 * @author Charlie
 */
public class convertidorImagen implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream in, OutputStream out, Context cntxt) throws IOException {
        String respuesta = "";
        ObjectMapper mapper = new ObjectMapper();
       	JsonNode jsonNode = mapper.readTree(in);
        System.out.println("Al menos entre");
        System.out.println("antes de tratar de obtener data");
        JSONObject ob = new JSONObject(jsonNode.toString());
        String textoEntrada = ob.getString("data");
        System.out.println("x fin data");
        System.out.println(textoEntrada);
        byte[] bytesEntrada = Base64.decode(textoEntrada);
        String formato = ob.getString("formato");
        
        ByteArrayInputStream bais = new ByteArrayInputStream(bytesEntrada);
        BufferedImage bufferedInput, bufferedOutput;
        bufferedInput = ImageIO.read(bais);
        int width = bufferedInput.getWidth();
        int height = bufferedInput.getHeight();
        
        bufferedOutput = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        int px[] = new int[width * height];
        bufferedInput.getRGB(0, 0, width, height, px, 0, width);
        bufferedOutput.setRGB(0, 0, width, height, px, 0, width);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        switch(formato){
            case "gif" :
                ImageIO.write(bufferedOutput, "gif",  baos);
                break;
            case "jpg" :
                ImageIO.write(bufferedOutput, "jpg",  baos);
                break;
            case "bmp" :
                ImageIO.write(bufferedOutput, "bmp",  baos);
                break;
            case "png" :   
                ImageIO.write(bufferedOutput, "png",  baos);
                break;
            case "jpeg" :   
                ImageIO.write(bufferedOutput, "jpeg",  baos);
                break;
        }
        
        String salidaTexto = Base64.encodeAsString(baos.toByteArray());
        respuesta = salidaTexto;
        
        JsonNode raiz = mapper.createObjectNode();
        //String cabezera =  "{'Access-Control-Allow-Origin': '*' ,'Access-Control-Allow-Headers': 'Content-Type' , 'Access-Control-Allow-Methods': 'OPTIONS,POST,GET'}";
        //((ObjectNode) raiz).put("headers", cabezera);
        ((ObjectNode) raiz).put("respuesta", respuesta);
	String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raiz);
        out.write(jsonString.getBytes(Charset.forName("UTF-8")));
        System.out.println("x fin termine");
        System.out.println(respuesta);
    }
    
}
