package kr.ac.sesac.springboot.webproject.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "kr.ac.sesac.springboot.webproject.mapper")
public class MyBatisConfig {

}
