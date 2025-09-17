package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import org.apache.commons.collections.CollectionUtils;
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
 * 素材服务
 * Created by yinxianzhi on 18/10/24.
 */
@Tag(name = "ResourceBaseController", description = "素材服务 API")
@RestController
@Validated
@RequestMapping("/common")
public class ResourceBaseController {

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Operation(summary = "上传素材，resourceTyp: 0图片，1视频")
    @Parameters({
            @Parameter(
                    name = "uploadFile", description = "上传素材", required = true),
            @Parameter(
                    name = "cateId", description = "素材分类Id", required = true),
            @Parameter(
                    name = "resourceType", description = "素材类型", required = true)
    })
    @RequestMapping(value = "/uploadResource", method = RequestMethod.POST)
    public BaseResponse<Object> uploadFile(@RequestParam("uploadFile") List<MultipartFile> multipartFiles, Long cateId, ResourceType resourceType) {

        //验证上传参数
        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<String> resourceUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (CommonUtil.checkImageByName(file.getOriginalFilename())) {
                try {
                    // 上传
                    YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                            .cateId(cateId)
                            .resourceType(resourceType)
                            .resourceName(file.getOriginalFilename())
                            .content(file.getBytes())
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
