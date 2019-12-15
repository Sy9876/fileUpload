package cn.sy.configuration;

import cn.sy.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PreDestroy;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "oss.enabled", havingValue = "true", matchIfMissing = true)
public class OssClientConfig {

    @Autowired
    private OssProperties ossProperties;

    @Bean
    public OSS ossClient() {
        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();

        System.out.println("ossClient creating. endpoint: " + endpoint + " accessKeyId: " + accessKeyId);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    @Bean
    public OssService ossService(OSS ossClient, OssProperties ossProperties) {
        return new OssService(ossClient, ossProperties);
    }

    @PreDestroy
    public void closeOssClient() {
//        if(this.ossClient!=null) {
            System.out.println("ossClient.shutdown");
//            this.ossClient.shutdown();
//        }
    }

}
