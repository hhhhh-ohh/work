package com.wanmi.sbc.store;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceQueryProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourcePageRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.api.response.systemresource.EsSystemRessourcePageResponse;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceQueryProvider;
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
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺素材服务
 * Created by yinxianzhi on 18/10/18.
 * 完全参考平台素材管理
 */
@Tag(name = "StoreResourceController", description = "店铺素材服务 API")
@RestController
@Validated
@RequestMapping("/store")
public class StoreResourceController {

    private static final Logger logger = LoggerFactory.getLogger(StoreResourceController.class);

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

    @Autowired
    private SystemResourceQueryProvider systemResourceQueryProvider;

    /**
     * 分页素材
     *
     * @param pageRequest 素材参数
     * @return
     */
    @Operation(summary = "分页店铺素材")
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsSystemResourceVO>> page(@RequestBody @Valid EsSystemResourcePageRequest pageRequest) {
        pageRequest.setStoreId(commonUtil.getStoreId());
        BaseResponse<EsSystemRessourcePageResponse> response = esSystemResourceQueryProvider.page(pageRequest);
        return BaseResponse.success(response.getContext().getSystemResourceVOPage());

    }

    /**
     * 上传店铺素材
     *
     * @param multipartFiles
     * @param cateId         分类id
     * @return
     */
    @Operation(summary = "上传店铺素材，resourceType-->0: 图片, 1: 视频")
    @Parameters({
            @Parameter(name = "uploadFile", description = "上传素材", required = true),
            @Parameter(name = "cateId", description = "素材分类Id", required = true),
            @Parameter(name = "resourceType", description = "素材类型", required = true)
    })
    @RequestMapping(value = "/uploadStoreResource", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam("uploadFile") List<MultipartFile> multipartFiles, Long
            cateId, ResourceType resourceType) {
        //验证上传参数
        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> resourceUrls = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (Objects.nonNull(resourceType) &&
                    resourceType.equals(ResourceType.IMAGE) &&
                    !ImageUtils.checkImageSuffix(file.getOriginalFilename())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (Objects.nonNull(resourceType) &&
                    resourceType.equals(ResourceType.VIDEO) &&
                    !ImageUtils.checkVideoSuffix(file.getOriginalFilename())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            Integer maxSort = systemResourceQueryProvider.getMaxSort().getContext().getSort();

            try {
                // 上传
                YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                        .cateId(cateId)
                        .storeId(commonUtil.getStoreId())
                        .companyInfoId(commonUtil.getCompanyInfoId())
                        .resourceType(resourceType)
                        .resourceName(file.getOriginalFilename())
                        .content(file.getBytes())
                        .sort(Nutils.defaultVal(maxSort, 0) + 1)
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
        }
        return ResponseEntity.ok(resourceUrls);
    }

    /**
     * 上传店铺素材
     *
     * @param multipartFiles
     * @param cateId         分类id
     * @return
     */
    @Operation(summary = "上传店铺素材，resourceType-->0: 图片, 1: 视频")
    @Parameters({
            @Parameter(name = "uploadFile", description = "上传素材", required = true),
            @Parameter(name = "cateId", description = "素材分类Id", required = true),
            @Parameter(name = "resourceType", description = "素材类型", required = true)
    })
    @RequestMapping(value = "/uploadStoreResourceImageForSort", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam("uploadFile") List<MultipartFile> multipartFiles,
                                             @RequestParam("imageSortStr")String imageSortStr, Long
                                                     cateId, ResourceType resourceType) {

        List<Map<String,String>> imageSortList = JSON.parseObject(imageSortStr, List.class);
        Map<String,Integer> imageSortMap = new HashMap<>();
        if(Objects.nonNull(imageSortList) && CollectionUtils.isNotEmpty(imageSortList)){
            imageSortList.forEach(imageSort->{
                if(StringUtils.isNotBlank(imageSort.get("imageName")) && StringUtils.isNotBlank(imageSort.get("sort"))){
                    imageSortMap.put(imageSort.get("imageName"),Integer.valueOf(imageSort.get("sort")));
                }
            });
        }

        //验证上传参数
        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> resourceUrls = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            if (file == null || file.getSize() == 0 || file.getOriginalFilename() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (Objects.nonNull(resourceType) &&
                    resourceType.equals(ResourceType.IMAGE) &&
                    !ImageUtils.checkImageSuffix(file.getOriginalFilename())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (Objects.nonNull(resourceType) &&
                    resourceType.equals(ResourceType.VIDEO) &&
                    !ImageUtils.checkVideoSuffix(file.getOriginalFilename())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            try {
                Integer sort = 0;
                if(Objects.nonNull(imageSortMap.get(file.getOriginalFilename()))){
                    sort = imageSortMap.get(file.getOriginalFilename());
                    Integer maxSort = systemResourceQueryProvider.getMaxSort().getContext().getSort();
                    sort = sort + maxSort;
                }
                // 上传
                YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                        .cateId(cateId)
                        .storeId(commonUtil.getStoreId())
                        .companyInfoId(commonUtil.getCompanyInfoId())
                        .resourceType(resourceType)
                        .resourceName(file.getOriginalFilename())
                        .content(file.getBytes())
                        .sort(sort)
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
        }
        return ResponseEntity.ok(resourceUrls);
    }

    /**
     * 编辑店铺素材
     */
    @Operation(summary = "编辑店铺素材")
    @RequestMapping(value = "/resource", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid SystemResourceModifyRequest
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
     * 删除店铺素材
     */
    @Operation(summary = "删除店铺素材")
    @RequestMapping(value = "/resource", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody @Valid SystemResourceDelByIdListRequest delByIdListReq) {

        SystemResourceEditResponse response = systemResourceSaveProvider.deleteByIdList(delByIdListReq).getContext();
        List<SystemResourceVO> systemResourceVOList = response.getSystemResourceVOList();
        if (CollectionUtils.isNotEmpty(systemResourceVOList)){
            if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
                List<Long> storeIds = systemResourceVOList
                        .stream()
                        .map(SystemResourceVO::getStoreId)
                        .distinct()
                        .collect(Collectors.toList());
                if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            }
        }

        //图片库同步es
        return this.addEsSystemResource(systemResourceVOList);
    }


    /**
     * 批量修改素材的分类
     */
    @GlobalTransactional
    @Operation(summary = "批量修改店铺素材的分类")
    @RequestMapping(value = "/resource/resourceCate", method = RequestMethod.PUT)
    public BaseResponse updateCate(@RequestBody @Valid SystemResourceMoveRequest
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
