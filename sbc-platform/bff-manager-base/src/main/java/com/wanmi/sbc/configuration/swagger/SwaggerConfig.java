package com.wanmi.sbc.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * swagger config
 */
@Configuration
@ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true")
public class SwaggerConfig {

    @Value("${swagger.contact.name}")
    String contactName;

    @Value("${swagger.contact.url}")
    String contactUrl;

    @Value("${swagger.contact.email}")
    String contactEmail;

    @Value("${swagger.title}")
    String title;

    @Value("${swagger.description}")
    String description;

    @Value("${swagger.version}")
    String version;

    @Value("${swagger.enable}")
    boolean enable;


    /**
     * swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
     *
     * @return
     * @Api：修饰整个类，描述Controller的作用
     * Operation：描述一个类的一个方法，或者说一个接口
     * @ApiParam：单个参数描述
     * @Schema：用对象来接收参数
     * @ApiProperty：用对象接收参数时，描述对象的一个字段
     * @ApiResponse：HTTP响应其中1个描述
     * @ApiResponses：HTTP响应整体描述
     * @ApiIgnore：使用该注解忽略这个API
     * @ApiError：发生错误返回的信息
     * @Parameter：一个请求参数
     * @Parameters：多个请求参数paramType
     */
    @Bean
    public OpenAPI openAPI(){
        // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
        Contact contact = new Contact()
                .name(contactName)  // 作者名称
                .email(contactEmail) // 作者邮箱x
                .url(contactUrl) // 介绍作者的URL地址
                .extensions(new HashMap<>());// 使用Map配置信息（如key为"name","email","url"）


        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
        Info info = new Info()
                .title(title) // Api接口文档标题（必填）
                .description(description) // Api接口文档描述
                .version(version) // Api接口版本
                .contact(contact); // 设置联系人信息

        // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer xxx.xxx.xxx
        SecurityScheme securityScheme1 = new SecurityScheme()
                .name("JWT认证")
                .scheme("bearer") //如果是Http类型，Scheme是必填
                .type(SecurityScheme.Type.HTTP)
                .description("认证描述")
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT");

        List<SecurityRequirement> securityRequirements = new ArrayList<>();

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("authScheme");

        securityRequirements.add(securityRequirement);

        // 返回信息
        return new OpenAPI()
                .info(info);
    }
}
