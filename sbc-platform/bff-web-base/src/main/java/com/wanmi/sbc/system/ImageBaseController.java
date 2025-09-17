package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 图片服务
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "ImageBaseController", description = "图片服务 API")
@RestController
@Validated
@RequestMapping("/common")
public class ImageBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ImageBaseController.class);


    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Operation(summary = "上传图片")
    @Parameters({
            @Parameter(name = "uploadFile", description = "uploadFile", required = true),
            @Parameter(name = "cateId", description = "cateId", required = true)
    })
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public BaseResponse<Object> uploadFile(@RequestParam("uploadFile")List<MultipartFile> multipartFiles, Long cateId) {

        //验证上传参数
        if (org.apache.commons.collections.CollectionUtils.isEmpty(multipartFiles)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> resourceUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (ImageUtils.checkImageSuffix(file.getOriginalFilename())) {
                try {
                    // 上传
                    YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                            .cateId(cateId)
                            .content(file.getBytes())
                            .resourceType(ResourceType.IMAGE)
                            .resourceName(file.getOriginalFilename())
                            .build()).getContext();
                    resourceUrls.add(response.getResourceUrl());
                    SystemResourceVO systemResourceVO = response.getSystemResourceVO();

                    if (Objects.nonNull(systemResourceVO)) {
                        EsSystemResourceVO esSystemResourceVO = EsSystemResourceVO.builder().build();
                        KsBeanUtil.copyPropertiesThird(systemResourceVO, esSystemResourceVO);
                        EsSystemResourceSaveRequest saveRequest = EsSystemResourceSaveRequest.builder()
                                .systemResourceVOList(Collections.singletonList(esSystemResourceVO))
                                .build();
                        //同步es
                        esSystemResourceProvider.add(saveRequest);
                    }
                } catch (Exception e) {
                    throw new SbcRuntimeException(e);
                }
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return BaseResponse.success(resourceUrls);
    }
}
