package com.wanmi.sbc.thirdgoodscate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.thirdgoodscate.ThirdGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatcateaudit.WechatCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.*;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.ThirdGoodsCateByIdResponse;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.ThirdGoodsCateListResponse;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.ThirdGoodsCatePageResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateRelDTO;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateRelVO;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@Tag(name =  "第三方类目API", description =  "ThirdGoodsCateController")
@RestController
@Validated
@RequestMapping(value = "/thirdgoodscate")
public class ThirdGoodsCateController {

    @Autowired
    private ThirdGoodsCateQueryProvider thirdGoodsCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WechatCateAuditQueryProvider wechatCateAuditQueryProvider;

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public BaseResponse<ThirdGoodsCatePageResponse> getPage(@RequestBody @Valid ThirdGoodsCatePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("cateId", "desc");
        return thirdGoodsCateQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<ThirdGoodsCateListResponse> getList(@RequestBody @Valid ThirdGoodsCateListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return thirdGoodsCateQueryProvider.list(listReq);
    }

    @Operation(summary = "查询审核失败的微信类目")
    @GetMapping("/getUnchekedWechatCate/{cateId}")
    public BaseResponse<WechatCateDTO> getUnchekedWechatCate(@PathVariable Long cateId) {
        return wechatCateAuditQueryProvider.getUnchekedWechatCate(cateId);
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{cateId}")
    public BaseResponse<ThirdGoodsCateByIdResponse> getById(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ThirdGoodsCateByIdRequest idReq = new ThirdGoodsCateByIdRequest();
        idReq.setId(cateId);
        return thirdGoodsCateQueryProvider.getById(idReq);
    }

    /**
     * 根据三方类目父id关联查询平台类目
     *
     * @return
     */
    @Operation(summary = "根据三方类目父id关联查询平台类目")
    @PostMapping("/getRelByParentId")
    public BaseResponse<List<ThirdGoodsCateRelDTO>> getAllRel(@RequestBody CateRelByParentIdRequest cateRelByParentIdRequest) {
        return thirdGoodsCateQueryProvider.getRelByParentId(cateRelByParentIdRequest);
    }

    /**
     * 查询所有三方类目并关联平台类目
     *
     * @return
     */
    @Operation(summary = "查询所有三方类目并关联平台类目")
    @PostMapping("/list/rel")
    public BaseResponse<List<ThirdGoodsCateRelVO>> listRel(@RequestBody CateRelRequest request) {
        return thirdGoodsCateQueryProvider.listRel(request);
    }

    /**
     * 查询可供审核的微信类目并关联平台类目
     *
     * @return
     */
    @Operation(summary = "查询可供审核的微信类目并关联平台类目")
    @GetMapping("/list/wechat/forAudit")
    public BaseResponse<List<WechatCateDTO>> listWechatForAudit() {
        return wechatCateAuditQueryProvider.listWechatForAudit();
    }

    /**
     * 微信类目提审
     * @param request
     * @return
     */
    @Operation(summary = "微信类目提审")
    @PostMapping("/wechat/audit")
    public BaseResponse wechatAudit(@RequestBody @Valid WechatAuditRequest request) {
        request.setOperatorId(commonUtil.getOperatorId());
        return wechatCateAuditQueryProvider.wechatAudit(request);
    }


}
