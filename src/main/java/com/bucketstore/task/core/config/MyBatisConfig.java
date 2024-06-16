package com.bucketstore.task.core.config;


import com.bucketstore.task.core.typehandler.OrderStatusTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.bucketstore.task.core.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // MyBatis Configuration 설정
        org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
        mybatisConfig.setMapUnderscoreToCamelCase(true);

        // Enum 타입을 처리하는 TypeHandler 등록
        sessionFactory.setTypeHandlers(new OrderStatusTypeHandler());

        sessionFactory.setConfiguration(mybatisConfig);

        return sessionFactory.getObject();
    }
}
