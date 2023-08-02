package com.zyq.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


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
                        .title("ひかり的接口文档")
                        .description("ひかり的一些接口")
                        .termsOfServiceUrl("http://www.hkrblog.cn/")
                        .contact(new Contact("ひかり", "https://github.com/1642777208", "1642777208@qq.com"))
                        .version("2.0")
                        .build())
                //分组名称
                .groupName("博客Api文档")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.zyq.controller"))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(getParameterList());
        return docket;
    }
/*    private List<Parameter> getParameterList() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }*/
}
