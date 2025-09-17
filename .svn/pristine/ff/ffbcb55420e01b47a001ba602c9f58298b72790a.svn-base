package com.wanmi.sbc.system;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceQueryProvider;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunConfigByIdRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunConfigListRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunConfigModifyRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunConfigListResponse;
import com.wanmi.sbc.setting.api.response.yunservice.YunConfigResponse;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 素材服务
 * Created by yinxianzhi on 18/10/15.
 */
@Tag(name = "ResourceServerController", description = "素材服务")
@RestController
@Validated
public class ResourceServerController {

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Autowired
    private SystemResourceQueryProvider systemResourceQueryProvider;

    @Operation(summary = "上传素材")
    @Parameters({
            @Parameter(name = "uploadFile", description = "文件", required =
                    true),
            @Parameter(name = "cateId", description = "分类id", required =
                    true),
            @Parameter( name = "resourceType", description = "上传类型", required = true)
    })
    @RequestMapping(value = "/uploadResource", method = RequestMethod.POST)
    @GlobalTransactional
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
                YunUploadResourceResponse response =
                        yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                                .cateId(cateId)
                                .resourceType(resourceType)
                                .content(file.getBytes())
                                .resourceName(file.getOriginalFilename())
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
                throw new SbcRuntimeException(e);
            }
        }
        return ResponseEntity.ok(resourceUrls);
    }

    @Operation(summary = "上传素材")
    @Parameters({
            @Parameter(name = "uploadFile", description = "文件", required =
                    true),
            @Parameter(name = "cateId", description = "分类id", required =
                    true),
            @Parameter( name = "resourceType", description = "上传类型", required = true)
    })
    @RequestMapping(value = "/uploadStoreResourceImageForSort", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<Object> uploadFile(@RequestParam("uploadFile") List<MultipartFile> multipartFiles,
                                             @RequestParam("imageSortStr")String imageSortStr, Long
                                                     cateId, ResourceType resourceType) {
        List<JSONObject> imageSortList = JSONArray.parseArray(imageSortStr, JSONObject.class);
        Map<String,Integer> imageSortMap = new HashMap<>();
        if(Objects.nonNull(imageSortList) && CollectionUtils.isNotEmpty(imageSortList)){
            imageSortList.forEach(imageSort->{
                if(StringUtils.isNotBlank(imageSort.getString("imageName")) && StringUtils.isNotBlank(imageSort.getString("sort"))){
                    imageSortMap.put(imageSort.getString("imageName"),Integer.valueOf(imageSort.getString("sort")));
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
                YunUploadResourceResponse response =
                        yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                                .cateId(cateId)
                                .resourceType(resourceType)
                                .content(file.getBytes())
                                .resourceName(file.getOriginalFilename())
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
                throw new SbcRuntimeException(e);
            }
        }
        return ResponseEntity.ok(resourceUrls);
    }

    /**
     * 商品详情富文本编辑器ueditor需要用到的文件上传方法，返回格式与普通的有区别
     *
     * @param uploadFile
     * @param cateId
     * @return
     */
    @Operation(summary = "商品详情富文本编辑器ueditor需要用到的文件上传方法，返回格式与普通的有区别")
    @Parameters({
            @Parameter(name = "uploadFile", description = "文件", required =
                    true),
            @Parameter(name = "cateId", description = "分类id", required =
                    true),
    })
    @RequestMapping(value = "/uploadImage4UEditor", method = RequestMethod.POST)
    public String uploadFile4UEditor(@RequestParam("uploadFile") MultipartFile uploadFile, Long cateId) {
        //验证上传参数
        if (null == uploadFile || uploadFile.getSize() == 0 || uploadFile.getOriginalFilename() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        String fileName = uploadFile.getOriginalFilename();
        if (ImageUtils.checkImageSuffix(fileName)) {
            try {
                // 上传
                YunUploadResourceResponse response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                        .cateId(cateId)
                        .resourceType(ResourceType.IMAGE)
                        .content(uploadFile.getBytes())
                        .resourceName(fileName)
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

                return "{original:'" + fileName + "',name:'" + fileName + "',url:'"
                        + response.getResourceUrl() + "',size:" + uploadFile.getSize() + ",state:'SUCCESS'}";
            } catch (Exception e) {
                throw new SbcRuntimeException(e);
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 查询素材服务器
     *
     * @return 素材服务器
     */
    @Operation(summary = "查询素材服务器")
    @RequestMapping(value = "/system/resourceServers", method = RequestMethod.GET)
    public BaseResponse<YunConfigListResponse> page() {
        YunConfigListResponse yunConfigListResponse = yunServiceProvider.list(YunConfigListRequest.builder()
                .configKey(ConfigKey.RESOURCESERVER.toString())
                .delFlag(DeleteFlag.NO)
                .build()).getContext();
        return BaseResponse.success(yunConfigListResponse);
    }

    /**
     * 获取素材服务器详情信息
     *
     * @param resourceServerId 编号
     * @return 配置详情
     */
    @Operation(summary = "获取素材服务器详情信息")
    @Parameter(name = "resourceServerId", description = "素材编号",
            required = true)
    @RequestMapping(value = "/system/resourceServer/{resourceServerId}", method = RequestMethod.GET)
    public BaseResponse<YunConfigResponse> list(@PathVariable Long resourceServerId) {
        YunConfigResponse yunConfigResponse = yunServiceProvider.getById(YunConfigByIdRequest.builder()
                .id(resourceServerId)
                .build()).getContext();
        return BaseResponse.success(yunConfigResponse);
    }

    /**
     * 编辑素材服务器
     */
    @Operation(summary = "编辑素材服务器")
    @RequestMapping(value = "/system/resourceServer", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody YunConfigModifyRequest request) {
        if (request.getId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        yunServiceProvider.modify(request);
        operateLogMQUtil.convertAndSend("设置", "编辑素材服务器接口", "编辑素材服务器接口");
        return BaseResponse.SUCCESSFUL();
    }

}
