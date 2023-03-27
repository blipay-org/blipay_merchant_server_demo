package com.blipay.merchant.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfiguration {
    @Value("classpath:sql/schema.sql")
    private Resource sqlScriptSchema;

    @Value("classpath:sql/data.sql")
    private Resource sqlScriptData;


//    @Bean
//    public DataSource getDataSource(){
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.h2.Driver");
//        dataSourceBuilder.url("jdbc:h2:file:./data;AUTO_SERVER=TRUE;AUTO_RECONNECT=TRUE;MODE=MySQL");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("root");
//        dataSourceBuilder.type(HikariDataSource.class);
//        return dataSourceBuilder.build();
//    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource){
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    private DatabasePopulator databasePopulator(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(sqlScriptSchema);
//        populator.addScript(sqlScriptData);

        return populator;
    }

}
