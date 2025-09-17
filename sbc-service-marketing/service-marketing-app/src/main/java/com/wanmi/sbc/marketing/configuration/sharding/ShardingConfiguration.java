//package com.wanmi.sbc.marketing.configuration.sharding;
//
//import com.google.common.base.Preconditions;
//import io.seata.rm.datasource.DataSourceProxy;
//import lombok.RequiredArgsConstructor;
//import org.apache.shardingsphere.core.yaml.swapper.MasterSlaveRuleConfigurationYamlSwapper;
//import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
//import org.apache.shardingsphere.encrypt.yaml.swapper.EncryptRuleConfigurationYamlSwapper;
//import org.apache.shardingsphere.shardingjdbc.api.EncryptDataSourceFactory;
//import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.common.SpringBootPropertiesConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.encrypt.EncryptRuleCondition;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.encrypt.SpringBootEncryptRuleConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.masterslave.MasterSlaveRuleCondition;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.ShardingRuleCondition;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
//import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
//import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
//import org.apache.shardingsphere.underlying.common.config.exception.ShardingSphereConfigurationException;
//import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.StandardEnvironment;
//import org.springframework.jndi.JndiObjectFactoryBean;
//
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.*;
//
//@Configuration
//@EnableConfigurationProperties({
//        SpringBootShardingRuleConfigurationProperties.class,
//        SpringBootMasterSlaveRuleConfigurationProperties.class, SpringBootEncryptRuleConfigurationProperties.class, SpringBootPropertiesConfigurationProperties.class})
//@ConditionalOnProperty(prefix = "spring.shardingsphere", name = "enabled", havingValue = "true", matchIfMissing = true)
//@RequiredArgsConstructor
//public class ShardingConfiguration implements EnvironmentAware {
//
//    private final SpringBootShardingRuleConfigurationProperties shardingProperties;
//
//    private final SpringBootMasterSlaveRuleConfigurationProperties masterSlaveProperties;
//
//    private final SpringBootEncryptRuleConfigurationProperties encryptProperties;
//
//    private final SpringBootPropertiesConfigurationProperties propMapProperties;
//
//    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
//
//    private static final String JNDI_NAME = "jndi-name";
//
//    /**
//     * Get encrypt data source bean.
//     *
//     * @return data source bean
//     */
//    @Bean
//    @Conditional(EncryptRuleCondition.class)
//    public DataSource encryptDataSource() throws SQLException {
//        return EncryptDataSourceFactory.createDataSource(dataSourceMap.values().iterator().next(), new EncryptRuleConfigurationYamlSwapper().swap(encryptProperties), propMapProperties.getProps());
//    }
//
//    /**
//     * Get master-slave data source bean.
//     *
//     * @return data source bean
//     * @throws SQLException SQL exception
//     */
//    @Bean
//    @Conditional(MasterSlaveRuleCondition.class)
//    public DataSource masterSlaveDataSource() throws SQLException {
//        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, new MasterSlaveRuleConfigurationYamlSwapper().swap(masterSlaveProperties), propMapProperties.getProps());
//    }
//
//    /**
//     * Get sharding data source bean.
//     *
//     * @return data source bean
//     * @throws SQLException SQL exception
//     */
//    @Bean
//    @Conditional(ShardingRuleCondition.class)
//    public DataSource shardingDataSource() throws SQLException {
//        return ShardingDataSourceFactory.createDataSource(dataSourceMap, new ShardingRuleConfigurationYamlSwapper().swap(shardingProperties), propMapProperties.getProps());
//    }
//
//    @Override
//    public final void setEnvironment(final Environment environment) {
//        String prefix = "spring.shardingsphere.datasource.";
//        for (String each : getDataSourceNames(environment, prefix)) {
//            try {
//                dataSourceMap.put(each, getDataSource(environment, prefix, each));
//            } catch (final ReflectiveOperationException ex) {
//                throw new ShardingSphereConfigurationException("Can't find datasource type!", ex);
//            } catch (final NamingException namingEx) {
//                throw new ShardingSphereConfigurationException("Can't find JNDI datasource!", namingEx);
//            }
//        }
//    }
//
//    private List<String> getDataSourceNames(final Environment environment, final String prefix) {
//        StandardEnvironment standardEnv = (StandardEnvironment) environment;
//        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
//        return null == standardEnv.getProperty(prefix + "name")
//                ? new InlineExpressionParser(standardEnv.getProperty(prefix + "names")).splitAndEvaluate() : Collections.singletonList(standardEnv.getProperty(prefix + "name"));
//    }
//
//    @SuppressWarnings("unchecked")
//    private DataSource getDataSource(final Environment environment, final String prefix, final String dataSourceName) throws ReflectiveOperationException, NamingException {
//        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dataSourceName.trim(), Map.class);
//        Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
//        Object o = dataSourceProps.get(JNDI_NAME);
//        if (!Objects.isNull(o)) {
//            return getJndiDataSource(o.toString());
//        }
//
//        return new DataSourceProxy(DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps));
//    }
//
//    private DataSource getJndiDataSource(final String jndiName) throws NamingException {
//        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//        bean.setResourceRef(true);
//        bean.setJndiName(jndiName);
//        bean.setProxyInterface(DataSource.class);
//        bean.afterPropertiesSet();
//        return (DataSource) bean.getObject();
//    }
//}
