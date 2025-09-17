package com.wanmi.sbc.recommend;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.setting.api.provider.AuditProvider;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.recommend.RecommendQueryProvider;
import com.wanmi.sbc.setting.api.provider.recommend.RecommendProvider;
import com.wanmi.sbc.setting.api.request.ConfigStatusModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.recommend.*;
import com.wanmi.sbc.setting.api.response.recommend.*;
import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import java.util.Objects;

import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "种草信息表管理API", description =  "RecommendController")
@RestController
@Validated
@RequestMapping(value = "/recommend")
public class RecommendController {

    @Autowired
    private RecommendQueryProvider recommendQueryProvider;

    @Autowired
    private RecommendProvider recommendProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private AuditProvider auditProvider;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询种草信息表
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询种草信息表")
    @PostMapping("/page")
    public BaseResponse<RecommendPageResponse> getPage(@RequestBody @Valid RecommendPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        if(Objects.nonNull(pageReq.getPageSortType())){
            String sort = Objects.equals(Constants.ONE, pageReq.getPageSort()) ? SortType.ASC.toValue() : SortType.DESC.toValue();
            switch (pageReq.getPageSortType()){
                case Constants.ONE:
                    pageReq.putSort("readNum",sort);
                    break;
                case Constants.TWO:
                    pageReq.putSort("visitorNum",sort);
                    break;
                case Constants.THREE:
                    pageReq.putSort("fabulousNum",sort);
                    break;
                case Constants.FOUR:
                    pageReq.putSort("forwardNum",sort);
                    break;
                default:
                    break;
            }
        }else {
            pageReq.putSort("topTime",SortType.DESC.toValue());
            pageReq.putSort("updateTime",SortType.DESC.toValue());
        }
        return recommendQueryProvider.page(pageReq);
    }

    /**
     * 新增种草信息表
     * @param addReq
     * @return
     */
    @Operation(summary = "新增种草信息表")
    @PostMapping("/add")
    public BaseResponse<RecommendAddResponse> add(@RequestBody @Valid RecommendAddRequest addReq) {
        addReq.setUserId(commonUtil.getOperatorId());
        operateLogMqUtil
                .convertAndSend("种草","新增种草信息","新增种草信息："+addReq.getTitle());
        return recommendProvider.add(addReq);
    }

    /**
     * 置顶
     * @param request
     * @return
     */
    @Operation(summary = "置顶")
    @PutMapping("/update/top")
    public BaseResponse updateTop(@RequestBody @Valid RecommendByIdRequest request) {
        request.setUserId(commonUtil.getOperatorId());
        BaseResponse baseResponse = recommendProvider.updateTop(request);
        operateLogMqUtil
                .convertAndSend("种草","置顶种草","置顶种草："+request.getId());
        return baseResponse;
    }

    /**
     * 修改状态
     * @param request
     * @return
     */
    @Operation(summary = "修改状态")
    @PutMapping("/update/status")
    public BaseResponse updateStatus(@RequestBody @Valid RecommendByIdRequest request) {
        request.setUserId(commonUtil.getOperatorId());
        operateLogMqUtil
                .convertAndSend("种草","修改隐藏公开状态","修改隐藏公开状态："+request.getId());
        return recommendProvider.updateStatus(request);
    }

    /**
     * 删除种草
     * @param request
     * @return
     */
    @Operation(summary = "删除种草")
    @DeleteMapping("/update/del")
    public BaseResponse delById(@RequestBody @Valid RecommendByIdRequest request) {
        request.setUserId(commonUtil.getOperatorId());
        operateLogMqUtil
                .convertAndSend("种草","删除种草","删除种草："+request.getId());
        return recommendProvider.delById(request);
    }

    /**
     * 种草开关设置
     * @return
     */
    @Operation(summary = "种草开关设置")
    @GetMapping("/config")
    public BaseResponse<RecommendConfigResponse> config() {
        RecommendConfigResponse response = new RecommendConfigResponse();
        response.setConfigVO(auditQueryProvider.recommendConfig().getContext());
        return BaseResponse.success(response);
    }

    /**
     * 修改种草开关
     * @param request
     * @return
     */
    @Operation(summary = "修改种草开关")
    @PutMapping("/update/config")
    public BaseResponse updateConfig(@RequestBody @Valid ConfigStatusModifyByTypeAndKeyRequest request) {
        operateLogMqUtil
                .convertAndSend("种草","修改种草开关","修改种草开关："+request.getConfigType());
        return BaseResponse.success(auditProvider.modifyStatusByTypeAndKey(request));
    }

    /**
     * 发现页展示优先级设置
     * @return
     */
    @Operation(summary = "发现页展示优先级设置")
    @GetMapping("/find/show/type")
    public BaseResponse<RecommendConfigResponse> findShowType() {
        RecommendConfigResponse response = new RecommendConfigResponse();
        response.setConfigVO(auditQueryProvider.findShowType().getContext());
        return BaseResponse.success(response);
    }

    /**
     * 修改发现页展示优先级设置
     * @param request
     * @return
     */
    @Operation(summary = "修改发现页展示优先级设置")
    @PutMapping("/update/find/show/type")
    public BaseResponse updateFindShowType(@RequestBody @Valid ConfigStatusModifyByTypeAndKeyRequest request) {
        operateLogMqUtil
                .convertAndSend("种草","修改发现页展示优先级设置","修改发现页展示优先级设置："+request.getConfigType());
        return BaseResponse.success(auditProvider.modifyStatusByTypeAndKey(request));
    }

    /**
     * 获取某个内容的小程序码
     * @return
     */
    @RequestMapping(value = "/getQrCode/{pageCode}", method = RequestMethod.GET)
    public BaseResponse<String> getRecommendQrCode(@PathVariable String pageCode) {
        Integer recommendStatus = auditQueryProvider.config().getContext().getRecommendStatus();
        if (Objects.nonNull(recommendStatus) && recommendStatus == Constants.ZERO) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070100);
        }
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        // 小程序跳转链接
        String codeUrl = "?pageType=recommend&pageCode=".concat(pageCode);
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));
        //  存入redis，前端调用使用
        if (StringUtils.isNotBlank(code)) {
            redisUtil.setString(code, codeUrl, 15000000L);
        }
        // 组装获取小程序码参数
        request.setPage("pages/sharepage/sharepage");
        request.setScene("TF"+code);
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }
}
