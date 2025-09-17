package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceQueryProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourcePageRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.api.response.systemresource.EsSystemRessourcePageResponse;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceSaveProvider;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceModifyRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceMoveRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceEditResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceModifyResponse;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 店铺图片服务
 * Created by bail on 17/11/20.
 * 完全参考平台图片管理
 */
@Tag(name = "StoreImageController", description = "店铺图片服务 API")
@RestController
@Validated
@RequestMapping("/store")
public class StoreImageController {

    private static final Logger logger = LoggerFactory.getLogger(StoreImageController.class);

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SystemResourceSaveProvider systemResourceSaveProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Autowired
    private EsSystemResourceQueryProvider esSystemResourceQueryProvider;


    /**
     * 分页店铺图片
     * @param pageRequest 店铺图片参数
     * @return
     */
    @Operation(summary = "分页店铺图片")
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public BaseResponse page(@RequestBody @Valid EsSystemResourcePageRequest pageRequest) {
        pageRequest.setResourceType(ResourceType.IMAGE);
        pageRequest.setStoreId(commonUtil.getStoreId());
        BaseResponse<EsSystemRessourcePageResponse> response = esSystemResourceQueryProvider.page(pageRequest);
        return BaseResponse.success(response.getContext().getSystemResourceVOPage());
    }

    /**
     * 上传店铺图片
     * @param multipartFiles
     * @param cateId         分类id
     * @return
     */
    @Operation(summary = "上传店铺图片")
    @RequestMapping(value = "/uploadStoreImage", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam("uploadFile") List<MultipartFile> multipartFiles, Long cateId) {
        //验证上传参数
        if (CollectionUtils.isEmpty(multipartFiles)) {
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
                            .storeId(commonUtil.getStoreId())
                            .companyInfoId(commonUtil.getCompanyInfoId())
                            .resourceType(ResourceType.IMAGE)
                            .resourceName(file.getOriginalFilename())
                            .content(file.getBytes())
                            .build()).getContext();
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
                    resourceUrls.add(response.getResourceUrl());
                } catch (Exception e) {
                    logger.error("uploadStoreResource error: {}", e.getMessage());
                    return ResponseEntity.ok(BaseResponse.FAILED());
                }
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return ResponseEntity.ok(resourceUrls);
    }

    /**
     * 编辑店铺图片
     */
    @Operation(summary = "编辑店铺图片")
    @RequestMapping(value = "/image", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody SystemResourceModifyRequest
                                     modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        BaseResponse<SystemResourceModifyResponse> modify = systemResourceSaveProvider.modify(modifyReq);
        SystemResourceVO systemResourceVO = modify.getContext().getSystemResourceVO();
        if (Objects.nonNull(systemResourceVO)) {
            this.addEsSystemResource(Collections.singletonList(systemResourceVO));
        }
        return modify;
    }

    /**
     * 删除店铺图片
     */
    @Operation(summary = "删除店铺图片")
    @RequestMapping(value = "/image", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody SystemResourceDelByIdListRequest delByIdListReq) {
        SystemResourceEditResponse response = systemResourceSaveProvider.deleteByIdList(delByIdListReq).getContext();
        List<SystemResourceVO> systemResourceVOList = response.getSystemResourceVOList();
        //图片库同步es
        return this.addEsSystemResource(systemResourceVOList);
    }

    /**
     * 批量修改店铺图片的分类
     */
    @Operation(summary = "批量修改店铺图片的分类")
    @RequestMapping(value = "/image/imageCate", method = RequestMethod.PUT)
    public BaseResponse updateCate(@RequestBody SystemResourceMoveRequest
                                           moveRequest) {
        moveRequest.setStoreId(commonUtil.getStoreId());
        SystemResourceEditResponse editResponse = systemResourceSaveProvider.move(moveRequest).getContext();
        List<SystemResourceVO> systemResourceVOList = editResponse.getSystemResourceVOList();
        //同步es
        return this.addEsSystemResource(systemResourceVOList);
    }

    /**
     * 图片库同步es
     *
     * @param systemResourceVOList
     * @return
     */
    private BaseResponse addEsSystemResource(List<SystemResourceVO> systemResourceVOList) {
        if (CollectionUtils.isNotEmpty(systemResourceVOList)) {
            List<EsSystemResourceVO> esSystemResourceVOList = KsBeanUtil.convert(systemResourceVOList,
                    EsSystemResourceVO.class);
            EsSystemResourceSaveRequest saveRequest = EsSystemResourceSaveRequest.builder()
                    .systemResourceVOList(esSystemResourceVOList)
                    .build();
            esSystemResourceProvider.add(saveRequest);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
