package com.eyslce.wx.mp.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driveClassName;

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", username);
        reg.addInitParameter("loginPassword", password);
        reg.addInitParameter("logSlowSql", "true");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driveClassName);
        //初始化时建立物理连接的个数。
        //初始化发生在显示调用init方法，或者第一次getConnection时
        dataSource.setInitialSize(1);
        //定义最大连接池数量
        dataSource.setMaxActive(20);
        //最小连接池数量
        dataSource.setMinIdle(0);
        //获取连接时最大等待时间，单位毫秒。
        //配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
        //如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        dataSource.setMaxWait(60000);
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。
        //在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。5.5及以上版本有PSCache，建议开启。
        dataSource.setPoolPreparedStatements(false);
        //要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
        //在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(100);
        //用来检测连接是否有效的sql，要求是一个查询语句。
        //如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
        dataSource.setValidationQuery("select 1");
        //申请连接时执行validationQuery检测连接是否有效，会降低性能。
        dataSource.setTestOnBorrow(false);
        //归还连接时执行validationQuery检测连接是否有效，会降低性能
        dataSource.setTestOnReturn(false);
        //建议配置为true，不影响性能，并且保证安全性。
        // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，
        // 执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true);
        try {
            //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
            // 监控统计用的filter:stat;日志用的filter:log4j;防御sql注入的filter:wall
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
