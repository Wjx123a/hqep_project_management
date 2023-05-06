//package com.hqep.dataSharingPlatform.sjfwgj.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Date;
//
//
///**
// * @类名: swaggerConfig
// * @功能描述 swagger 功能配置类
// * @作者信息 Wang_XD
// * @创建时间 2019/8/18
// */
//@Configuration
//@EnableWebMvc
//@EnableSwagger2
//@ComponentScan(basePackages = {"com.hqep"})
//public class swaggerConfig {
//    public static final String SWAGGER_SCAN_BASE_PACKAGE = "package com.hqep.dataSharingPlatform.sjfwgj.action";
//    public static final String VERSION = "1.0.0";
//
//    ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Swagger API 项目管理接口测试")
//                .description("项目管理接口测试")
//                .license("Apache 2.0")
//                .licenseUrl("")
//                .termsOfServiceUrl("")
//                .version(VERSION)
//                .build();
//    }
//
//    @Bean
//    public Docket api() {
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .enable(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
//                .paths(PathSelectors.any())
//                .build()
//                .directModelSubstitute(org.joda.time.LocalDate.class, Date.class)
//                .directModelSubstitute(org.joda.time.DateTime.class, Date.class)
//                .apiInfo(apiInfo());
//        return docket;
//    }
//}
//
