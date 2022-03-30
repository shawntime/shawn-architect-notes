package com.shawntime.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
public class DataSourceMapperScanner {

    @Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer jsdMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.shawntime.dao.mapper");
        return mapperScannerConfigurer;
    }
}
