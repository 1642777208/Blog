package com.zyq.blog.demo1.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Bean(value = "testApi")
    public Docket dockerBean() {

        //指定使用Swagger2规范
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .title("ikun的接口文档")
                        .description("ikun的一些接口")
                        // .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("114514", "", ""))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("ikun")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.zyq.blog.demo1.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameterList());
        return docket;
    }
    private List<Parameter> getParameterList() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
