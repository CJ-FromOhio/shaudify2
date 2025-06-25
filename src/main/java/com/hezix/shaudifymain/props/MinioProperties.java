package com.hezix.shaudifymain.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String songBucket;
    private String imageBucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
