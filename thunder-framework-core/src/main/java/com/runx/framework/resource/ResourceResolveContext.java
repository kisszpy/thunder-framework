package com.runx.framework.resource;

import com.runx.framework.contants.ApplicationResourceType;
import com.runx.framework.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

import static com.runx.framework.contants.Constants.*;

/**
 * @author: kisszpy
 * @date: 2020/3/11
 */
@Slf4j
public class ResourceResolveContext {

    private ResourceResolve resourceResolve;

    private ClassLoader classLoader;

    private static Enum resourceType = ApplicationResourceType.PROPERTIES;

    public static Enum getResourceType() {
        return resourceType;
    }

    public void setResourceResolve(ResourceResolve resourceResolve) {
        this.resourceResolve = resourceResolve;
    }

    public ResourceResolveContext(ClassLoader classLoader) {
        this.classLoader = classLoader;
        selectResource();
    }

    /**
     * 选择处理器,选择资源类型
     */
    private Enum selectResource() {
        InputStream inputStream = classLoader.getResourceAsStream(DEFAULT_RESOURCE_FILE_PREFIX + PROPERTIES_EXT);
        if (inputStream != null) {
            log.info("find resource file application.properties");
            return ApplicationResourceType.PROPERTIES;
        }else {
            inputStream = classLoader.getResourceAsStream(DEFAULT_RESOURCE_FILE_PREFIX + YAML_EXT[0]);
            if (inputStream != null){
                log.info("find resource file application.yml");
                return ApplicationResourceType.YAML;
            }
            if (inputStream != null) {
                log.info("find resource file application.yaml");
                return ApplicationResourceType.YAML;
            }
        }
        log.warn("can't find application.properties");
        return ApplicationResourceType.PROPERTIES;
    }

    public void resolve() {
        resourceResolve.resolve();
    }

}
