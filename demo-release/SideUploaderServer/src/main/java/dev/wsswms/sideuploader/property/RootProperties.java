package dev.wsswms.sideuploader.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: yin
 * @className: RootProperties
 * @packageName: dev.wsswms.sideuploader.property
 * @description:
 * @data: 2020/5/8 13:20
 **/
@Data
@ConfigurationProperties(prefix = "root")
public class RootProperties {
    private String rootDir;
}
