package cn.sy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class Config {
//
//    @Value("${sy.uploaddir}")
//    private String dir;

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        Path basedir = Paths.get(dir);
//        System.out.println("Config. set location: " + basedir);
//        factory.setLocation(basedir.toAbsolutePath().toString());
//        return factory.createMultipartConfig();
//    }
}
