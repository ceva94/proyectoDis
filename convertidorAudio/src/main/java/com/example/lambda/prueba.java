/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.lambda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;

/**
 *
 * @author Charlie
 */

public class prueba {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File fileEntrada = new File("sonidoPrueba.mp3");
                         
        File target = new File("objetivo.ogg");
        target.createNewFile();
                                           
        AudioAttributes audio = new AudioAttributes();
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
                                              
        EncodingAttributes attrs = new EncodingAttributes();

        //audio.setCodec("pcm_s16le");
	//attrs.setFormat("wav");
        
        //audio.setCodec("libmp3lame");
	//attrs.setFormat("mp3");
        
        //audio.setCodec("libvorbis");
	//attrs.setFormat("ogg");	
        
        //audio.setCodec("libfaac"); No funciono
	//attrs.setFormat("adts");
        
        
        attrs.setAudioAttributes(audio);                            

        //Encode                                                    
        Encoder encoder = new Encoder();  
        try{
            encoder.encode(new MultimediaObject(fileEntrada), target, attrs);
            System.out.println("si");
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
