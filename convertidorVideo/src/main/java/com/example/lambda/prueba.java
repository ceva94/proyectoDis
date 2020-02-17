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
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.VideoSize;

/**
 *
 * @author Charlie
 */

public class prueba {

    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File fileEntrada = new File("grinch.mp4");
        	                 
        File target = new File("objetivo.webm");
        target.createNewFile();
                                      
        AudioAttributes audio = new AudioAttributes();
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        
        VideoAttributes video = new VideoAttributes();
  	video.setBitRate(160000);
  	video.setFrameRate(15);
        video.setSize(new VideoSize(300, 300));
                                            
        EncodingAttributes attrs = new EncodingAttributes();

        //audio.setCodec("libvorbis");
	//video.setCodec("libvpx");
	//attrs.setFormat("webm");
        
        //audio.setCodec("libmp3lame");
        //video.setCodec("flv");
	//attrs.setFormat("flv");
        
        //audio.setCodec("aac");
        //video.setCodec("h264");
        //attrs.setFormat("mp4");
        
        //audio.setCodec("libfaac"); NO FUNCIONO
        //video.setCodec("mpeg4");
	//attrs.setFormat("3gp");
        
        
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
                                                  
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
