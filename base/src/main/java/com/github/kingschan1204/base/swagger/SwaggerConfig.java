package com.github.kingschan1204.base.swagger;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Swagger3 配置
 *
 * <p>http://localhost/swagger-ui/index.html
 */
@Slf4j
@EnableOpenApi
@EnableWebMvc
@Configuration
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("什么系统的 接口文档")
                .description("接口文档描述")
                .contact(new Contact("作者名字", "作者主页", "作者邮箱"))
                .version("版本号")
                .build();
    }

    @Bean
    public Docket initDocket(Environment env) {
        // 设置要暴漏接口文档的配置环境
        // spring.profiles.active为 dev 或 test启用swagger
        Profiles profile = Profiles.of("dev", "test");
        boolean flag = env.acceptsProfiles(profile);
        log.info("是否开启Swagger3 : {}", flag);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(flag)
                .groupName("分组名字")
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.github.kingschan1204"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Tag.class))
//                .paths(PathSelectors.any())
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();

        /*return new Docket(DocumentationType.OAS_30)
                //配置网站的基本信息
                .apiInfo(new ApiInfoBuilder()
                        //网站标题
                        .title("项目在线接口文档")
                        //标题后面的版本号
                        .version("v1.0")
                        .description("项目接口文档")
                        //联系人信息
                        .build())
                .select()
                //指定ctrl位置  只适合都放在一个包的情况
//              .apis(RequestHandlerSelectors.basePackage("com.ishubei.k8sdemo.net.ctrl"))
                //只要加了@Api注解的都展示出来
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();*/
    }

    /**
     * 增加如下配置可解决Spring Boot 2.7.x 与Swagger 3.0.0 不兼容问题
     **/
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }

}
