package com.nexus.nexusproject.Config; // kha pr file stored hai

import org.springframework.beans.factory.annotation.Value; // @Value used to fetch values from application.properties 
import org.springframework.context.annotation.Bean;// @creating a single bean
import org.springframework.context.annotation.Configuration; // @Configuration just to tell spring boot that this class may have any bean

import io.imagekit.sdk.ImageKit;// external object

@Configuration // bean ho sakti hai
public class ImageKitConfig {

    @Value("${imagekit.public.key}")
    private String publicKey;

    @Value("${imagekit.private.key}")
    private String privateKey;

    @Value("${imagekit.url.endpoint}")
    private String urlEndpoint;


    // application.properties se store kr lega values ek single imagekit object me
    @Bean
    public ImageKit imageKit() { // imagekit object ke name se bean bana di
        ImageKit imageKit = ImageKit.getInstance(); // imagekit object bana liya
        io.imagekit.sdk.config.Configuration config = new io.imagekit.sdk.config.Configuration( // imagekit sdk ka path hai 
                publicKey,
                privateKey,
                urlEndpoint
        );//imagekit object ke liye configuration bana di
        // mtb jaise ID CARD IMAGEKIT KE LIYE DETAILS BHAR DI
        imageKit.setConfig(config); // imagekit object ke liye configuration set kar di
        // ISKA MTB IMAGEKIT  OBJECT KO ID CARD DE DIYA AB IMAGEKIT USE KAR SAKTA HAI
        return imageKit;// imagekit object return kar diya
    }
}
