//package com.wanmi.sbc.common.configure;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
//import org.springframework.boot.actuate.health.AbstractHealthIndicator;
//import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;
//import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
//import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//import java.util.Map;
//
///**
// * @Author: songhanlin
// * @Date: Created In 下午4:49 2021/11/26
// * @Description: TODO
// */
//@Configuration
//@ConditionalOnProperty(prefix = "spring.datasource.health", name = "check", havingValue = "true", matchIfMissing = true)
//public class DataSourceHealthConfig /*extends DataSourceHealthContributorAutoConfiguration*/ {
//
////    @Value("${spring.datasource.dbcp2.validation-query:select 1}")
////    private String defaultQuery;
////
////    public DataSourceHealthConfig(Map<String, DataSource> dataSources, ObjectProvider<DataSourcePoolMetadataProvider> metadataProviders) {
////        super(dataSources, metadataProviders);
////    }
////
////    @Override
////    protected AbstractHealthIndicator createIndicator(DataSource source) {
////        DataSourceHealthIndicator indicator = (DataSourceHealthIndicator) super.createIndicator(source);
////        if (!StringUtils.hasText(indicator.getQuery())) {
////            indicator.setQuery(defaultQuery);
////        }
////        return indicator;
////    }
//    @Bean
//    DataSourcePoolMetadataProvider dataSourcePoolMetadataProvider() {
//        return dataSource -> dataSource instanceof HikariDataSource
//                // 这里如果所使用的数据源没有对应的 DataSourcePoolMetadata 实现的话也可以全部使用 NotAvailableDataSourcePoolMetadata
//                ? new HikariDataSourcePoolMetadata((HikariDataSource) dataSource)
//                : new NotAvailableDataSourcePoolMetadata();
//    }
//
//    /**
//     * 不可用的数据源池元数据.
//     */
//    private static class NotAvailableDataSourcePoolMetadata implements DataSourcePoolMetadata {
//        @Override
//        public Float getUsage() {
//            return null;
//        }
//
//        @Override
//        public Integer getActive() {
//            return null;
//        }
//
//        @Override
//        public Integer getMax() {
//            return null;
//        }
//
//        @Override
//        public Integer getMin() {
//            return null;
//        }
//
//        @Override
//        public String getValidationQuery() {
//            // 该字符串是适用于MySQL的简单查询语句,用于检查检查,其他数据库可能需要更换
//            return "select 1";
//        }
//
//        @Override
//        public Boolean getDefaultAutoCommit() {
//            return null;
//        }
//    }
//}
