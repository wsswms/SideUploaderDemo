package dev.wsswms.sideuploader;

import dev.wsswms.sideuploader.property.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
// 注册使用的beans
@EnableConfigurationProperties({
        RootProperties.class,
})
public class SideUploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SideUploaderApplication.class, args);
    }

}

