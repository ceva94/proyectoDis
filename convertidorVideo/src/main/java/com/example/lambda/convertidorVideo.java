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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.json.JSONObject;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.VideoSize;

/**
 *
 * @author Charlie
 */
public class convertidorVideo implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream in, OutputStream out, Context cntxt) throws IOException {
        String respuesta = "";
        ObjectMapper mapper = new ObjectMapper();
       	JsonNode jsonNode = mapper.readTree(in);
        JSONObject ob = new JSONObject(jsonNode.toString());
        String textoEntrada = ob.getString("data");
        byte[] bytesEntrada = Base64.decode(textoEntrada);
        String formato = ob.getString("formato");
        //Se crea archivos porque si no daba error
        File source = new File("/tmp/source");		                 
        File target = new File("/tmp/target");
        source.createNewFile();
        target.createNewFile();
        FileOutputStream fos = new FileOutputStream("/tmp/source");
        fos.write(bytesEntrada);
        fos.close();
        System.out.println("Formato");
        System.out.println(formato);
        
        AudioAttributes audio = new AudioAttributes();                                 
        audio.setBitRate(128000);                                   
        audio.setChannels(2);                                       
        audio.setSamplingRate(44100);
        
        VideoAttributes video = new VideoAttributes();
  	video.setBitRate(160000);
  	video.setFrameRate(15);
        video.setSize(new VideoSize(300, 300));
                                 
        EncodingAttributes attrs = new EncodingAttributes();

        switch(formato){
            case "mp4" :
                audio.setCodec("aac");
                video.setCodec("h264");
                attrs.setFormat("mp4");
                break;
            case "webm" :
                audio.setCodec("libvorbis");
                video.setCodec("libvpx");
                attrs.setFormat("webm");
                break;
            case "flv" :
                audio.setCodec("libmp3lame");
                video.setCodec("flv");
                attrs.setFormat("flv");
                break;
        }                                     
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
                                           
        Encoder encoder = new Encoder();  
        try{
            encoder.encode(new MultimediaObject(source), target, attrs);
        }
        catch(Exception e){
            System.out.println("Hubo error");
            System.out.println(e.getMessage());
        }
        System.out.println("se supone que transforme");
        byte[] bytesSalida = Files.readAllBytes(target.toPath());
	String salidaTexto = Base64.encodeAsString(bytesSalida);
        respuesta = salidaTexto;
        System.out.println("Inicio datos respuesta");
        System.out.println(respuesta);
        System.out.println("Fin datos respuesta");
        source.delete();
        target.delete();
        JsonNode raiz = mapper.createObjectNode();
        //String cabezera =  "{'Access-Control-Allow-Origin': '*' ,'Access-Control-Allow-Headers': 'Content-Type' , 'Access-Control-Allow-Methods': 'OPTIONS,POST,GET'}";
        //((ObjectNode) raiz).put("headers", cabezera);
        ((ObjectNode) raiz).put("respuesta", respuesta);
	String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raiz);
        out.write(jsonString.getBytes(Charset.forName("UTF-8")));
    }
    
}
