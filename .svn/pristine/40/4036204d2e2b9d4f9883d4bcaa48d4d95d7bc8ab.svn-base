package com.wanmi.sbc.wechatcateaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatcateaudit.WechatCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name =  "微信类目审核状态管理API", description =  "WechatCateAuditController")
@RestController
@Validated
@RequestMapping(value = "/wechatcateaudit")
public class WechatCateAuditController {

    @Autowired
    private WechatCateAuditQueryProvider wechatCateAuditQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private SellPlatformCateProvider sellPlatformCateProvider;


    @Operation(summary = "查询微信类目")
    @PostMapping("/tree")
    BaseResponse<List<WechatCateDTO>> tree(@RequestBody WechatCateAuditQueryRequest wechatAuditRequest){
        return wechatCateAuditQueryProvider.tree(wechatAuditRequest);
    }

    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<Object> uploadImg(@RequestParam("uploadFile") List<MultipartFile> multipartFiles) {

        List<String> resourceUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (ImageUtils.checkImageSuffix(file.getOriginalFilename())) {
                try {
                    String resourceUrl = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                            .resourceType(ResourceType.IMAGE)
                            .content(file.getBytes())
                            .resourceName(file.getOriginalFilename())
                            .build()).getContext().getResourceUrl();
                    resourceUrls.add(sellPlatformCateProvider.uploadImg(new SellPlatformUploadImgRequest(resourceUrl)).getContext().getTemp_img_url());
                } catch (IOException e) {
                   throw new RuntimeException(e);
                }

            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return ResponseEntity.ok(resourceUrls);
    }

}
