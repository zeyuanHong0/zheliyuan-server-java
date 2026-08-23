package org.zly.blogbackjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.zly.blogbackjava.mapper")
public class BlogBackJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackJavaApplication.class, args);
    }

}
