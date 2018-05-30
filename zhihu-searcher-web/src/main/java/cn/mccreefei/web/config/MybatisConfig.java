package cn.mccreefei.web.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author MccreeFei
 * @create 2018-01-19 16:10
 */
@Configuration
@Slf4j
@MapperScan(basePackages = "cn.mccreefei.zhihu.dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {
    private Environment env;
    @Bean
    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();

        properties.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME,
                env.getProperty("spring.datasource.driver-class-name"));
        properties.setProperty(DruidDataSourceFactory.PROP_URL,
                env.getProperty("spring.datasource.url"));
        properties.setProperty(DruidDataSourceFactory.PROP_USERNAME,
                env.getProperty("spring.datasource.username"));
        properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD,
                env.getProperty("spring.datasource.password"));

        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource){
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources(env.getProperty("mybatis.mapperLocations")));
            sessionFactory.setConfigLocation(new DefaultResourceLoader()
            .getResource(env.getProperty("mybatis.configLocation")));
            return sessionFactory.getObject();
        }catch (Exception e){
            log.error("初始化sqlSessionFactory失败！", e);
            return null;
        }
    }


    @Autowired
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
