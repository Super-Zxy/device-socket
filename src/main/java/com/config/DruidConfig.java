package com.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

@Configuration
@MapperScan(basePackages = {"com.dao"})
public class DruidConfig {

    private final Logger log = LoggerFactory.getLogger(DruidConfig.class);

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(Environment env) throws Exception {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();

        Properties tableDbRuleProp = new Properties();
        //加载table-db-rule至缓存中
        InputStream tableDbRuleIn =  new BufferedInputStream(getClass().getResourceAsStream("/table-db-rule.properties"));
        tableDbRuleProp.load(new InputStreamReader(tableDbRuleIn, "UTF-8"));
        tableDbRuleProp.load(tableDbRuleIn);
        Set<String> tableDbRuleSet = tableDbRuleProp.stringPropertyNames();
        for(String tableName:tableDbRuleSet)
        {
            DataSourceConstant.tableDbRuleMap.put(tableName,tableDbRuleProp.getProperty(tableName));
        }

        //读取配置文件db-route
        InputStream in =  new BufferedInputStream(getClass().getResourceAsStream("/db-route.properties"));
        Properties prop = new Properties();
        prop.load(new InputStreamReader(in, "UTF-8"));
        prop.load(in);
        Set<String> dbRouteSet = prop.stringPropertyNames();

        //反向记录加载过的数据源，防止多次加载
        Map<String,Object> dbSourceBackMap=new HashMap<>();
        for(String dbRoute:dbRouteSet)
        {
            if(!dbSourceBackMap.containsKey(prop.getProperty(dbRoute)))
            {
                DruidDataSource druidDataSource=build(env,"spring.datasource.druid."+prop.getProperty(dbRoute)+".");
                druidDataSource.init();
                dataSourceMap.put(dbRoute, druidDataSource);
                if("lampblackDB".equals(prop.getProperty(dbRoute)))
                {
                    // 将 lampblackDB 数据源作为默认指定的数据源
                    dynamicDataSource.setDefaultDataSource(druidDataSource);
                }
                dbSourceBackMap.put(prop.getProperty(dbRoute),druidDataSource);
            }else{
                dataSourceMap.put(dbRoute, dbSourceBackMap.get(prop.getProperty(dbRoute)));
            }
        }
        // 将数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(Environment env) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource(env));
        sessionFactory.setTypeAliasesPackage("com.dao");    // 扫描Model
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));    // 扫描映射文件
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-sqlconfig.xml"));
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(Environment env) throws Exception {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource(env));
    }

    private DruidDataSource build(Environment env, String prefix) {
        DruidDataSource ds = new DruidDataSource();
        try {
            String profiles = env.getProperty("spring.profiles.active");
            ds.setName(env.getProperty(prefix + "name"));
            ds.setUrl(env.getProperty(prefix + "url"));
            ds.setDriverClassName(env.getProperty(prefix + "driver-class-name", ""));
            ds.setUsername(env.getProperty(prefix + "username"));
            ds.setPassword(env.getProperty(prefix + "password"));
            ds.setInitialSize(env.getProperty(prefix + "initial-size", Integer.class));
            ds.setMinIdle(env.getProperty(prefix + "min-idle", Integer.class));
            ds.setMaxActive(env.getProperty(prefix + "max-active", Integer.class));
            ds.setMaxWait(env.getProperty(prefix + "max-wait", Integer.class));
            ds.setTimeBetweenEvictionRunsMillis(env.getProperty(prefix + "time-between-eviction-runs-millis", Integer.class));
            ds.setMinEvictableIdleTimeMillis(env.getProperty(prefix + "min-evictable-idle-time-millis", Integer.class));
            ds.setValidationQuery(env.getProperty(prefix + "validation-query"));
            ds.setValidationQueryTimeout(env.getProperty(prefix + "validation-query-timeout", Integer.class));
            ds.setTestWhileIdle(env.getProperty(prefix + "test-While-Idle", Boolean.class));
            ds.setTestOnBorrow(env.getProperty(prefix + "test-on-borrow", Boolean.class));
            ds.setTestOnReturn(env.getProperty(prefix + "test-on-return", Boolean.class));
            ds.setPoolPreparedStatements(env.getProperty(prefix + "pool-prepared-statements", Boolean.class));
            ds.setMaxPoolPreparedStatementPerConnectionSize(env.getProperty(prefix + "max-pool-prepared-statement-per-connection-size", Integer.class));
            ds.setFilters(env.getProperty(prefix + "filters"));
            ds.setUseGlobalDataSourceStat(env.getProperty(prefix + "use-global-data-source-stat", Boolean.class));

        } catch (Exception e) {
            log.error("注册数据源失败",e);
        }

        return ds;
    }

    /**
     * 下面是配置druid的监控 配置
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //控制台管理用户，加入下面2行 进入druid后台就需要登录
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid ");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true); //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setMergeSql(true); //SQL合并配置
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis的缺省值为3000，也就是3秒。
        return statFilter;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);
        return wallFilter;
    }
}
