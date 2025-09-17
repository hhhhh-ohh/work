package com.wanmi.sbc.configuration.swagger;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 11:34 2019-02-01
 * @Description: Swagger enum支持
 */
@Slf4j
public class SwaggerTokenFilterPlugin  {

    @Value("${jwt.excluded-urls}")
    private String jwtExcludedUrls;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

//    @Override
//    public Swagger mapDocumentation(Documentation from) {
//        Swagger swagger = super.mapDocumentation(from);
//        final String hideName = "Authorization";
//        if(swagger != null) {
//            List<String> urlList = Arrays.stream(jwtExcludedUrls.split(",")).map(StringUtils::trim).collect(Collectors.toList());
//            swagger.getPaths().forEach((s, path) -> {
//                if (urlList.stream().anyMatch(uri -> antPathMatcher.match(uri, StringUtils.trim(s)))) {
//                    if (Objects.nonNull(path.getGet()) && CollectionUtils.isNotEmpty(path.getGet().getParameters())) {
//                        path.getGet().setParameters(path.getGet().getParameters().stream()
//                                .filter(parameter -> !hideName.equalsIgnoreCase(parameter.getName()))
//                                .collect(Collectors.toList()));
//                    }
//                    if (Objects.nonNull(path.getPost()) && CollectionUtils.isNotEmpty(path.getPost().getParameters())) {
//                        path.getPost().setParameters(path.getPost().getParameters().stream()
//                                .filter(parameter -> !hideName.equalsIgnoreCase(parameter.getName()))
//                                .collect(Collectors.toList()));
//                    }
//                    if (Objects.nonNull(path.getPut()) && CollectionUtils.isNotEmpty(path.getPut().getParameters())) {
//                        path.getPut().setParameters(path.getPut().getParameters().stream()
//                                .filter(parameter -> !hideName.equalsIgnoreCase(parameter.getName()))
//                                .collect(Collectors.toList()));
//                    }
//                    if (Objects.nonNull(path.getDelete()) && CollectionUtils.isNotEmpty(path.getDelete().getParameters())) {
//                        path.getDelete().setParameters(path.getDelete().getParameters().stream()
//                                .filter(parameter -> !hideName.equalsIgnoreCase(parameter.getName()))
//                                .collect(Collectors.toList()));
//                    }
//                    if (Objects.nonNull(path.getPatch()) && CollectionUtils.isNotEmpty(path.getPatch().getParameters())) {
//                        path.getPatch().setParameters(path.getPatch().getParameters().stream()
//                                .filter(parameter -> !hideName.equalsIgnoreCase(parameter.getName()))
//                                .collect(Collectors.toList()));
//                    }
//                }
//            });
//        }
//        return swagger;
//    }
}