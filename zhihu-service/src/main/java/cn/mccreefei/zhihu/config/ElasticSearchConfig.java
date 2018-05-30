package cn.mccreefei.zhihu.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author MccreeFei
 * @create 2018-01-22 15:13
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {
    @Resource
    private Environment env;
    @Bean
    public Client client(){
        Settings settings = Settings.builder()
                .put("cluster.name", env.getProperty("elasticsearch.cluster.name")).build();
        try {
            return new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(
                            env.getProperty("elasticsearch.host")), Integer.parseInt(env.getProperty("elasticsearch.port"))));
        } catch (Exception e) {
            log.error("ElasticSearch 生成 client失败！", e);
            return null;
        }
    }
}
