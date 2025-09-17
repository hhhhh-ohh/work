package com.wanmi.sbc.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration.RecommendPositionConfigurationProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "推荐坑位设置管理API", description =  "RecommendPositionConfigurationController")
@RestController
@Validated
@RequestMapping(value = "/recommend/position/configuration")
public class RecommendPositionConfigurationController {

    @Autowired
    private RecommendPositionConfigurationQueryProvider recommendPositionConfigurationQueryProvider;

    @Autowired
    private RecommendPositionConfigurationProvider recommendPositionConfigurationProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询推荐坑位设置")
    @PostMapping("/page")
    public BaseResponse<RecommendPositionConfigurationPageResponse> getPage(@RequestBody @Valid RecommendPositionConfigurationPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return recommendPositionConfigurationQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询推荐坑位设置")
    @PostMapping("/list")
    public BaseResponse<RecommendPositionConfigurationListResponse> getList(@RequestBody @Valid RecommendPositionConfigurationListRequest listReq) {
        return recommendPositionConfigurationQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询推荐坑位设置")
    @GetMapping("/{id}")
    public BaseResponse<RecommendPositionConfigurationByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendPositionConfigurationByIdRequest idReq = new RecommendPositionConfigurationByIdRequest();
        idReq.setId(id);
        return recommendPositionConfigurationQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增推荐坑位设置")
    @PostMapping("/add")
    public BaseResponse<RecommendPositionConfigurationAddResponse> add(@RequestBody @Valid RecommendPositionConfigurationAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return recommendPositionConfigurationProvider.add(addReq);
    }

    @Operation(summary = "修改推荐坑位设置")
    @PutMapping("/modify")
    public BaseResponse<RecommendPositionConfigurationModifyResponse> modify(@RequestBody @Valid RecommendPositionConfigurationModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return recommendPositionConfigurationProvider.modify(modifyReq);
    }

    @Operation(summary = "修改推荐坑位开关设置")
    @PutMapping("/modifyIsOpen")
    @MultiSubmit
    public BaseResponse<RecommendPositionConfigurationModifyResponse> modifyIsOpen(@RequestBody @Valid RecommendPositionConfigurationModifyIsOpenRequest request) {
        return recommendPositionConfigurationProvider.modifyIsOpen(request);
    }

    @Operation(summary = "根据id删除推荐坑位设置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendPositionConfigurationDelByIdRequest delByIdReq = new RecommendPositionConfigurationDelByIdRequest();
        delByIdReq.setId(id);
        return recommendPositionConfigurationProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除推荐坑位设置")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid RecommendPositionConfigurationDelByIdListRequest delByIdListReq) {
        return recommendPositionConfigurationProvider.deleteByIdList(delByIdListReq);
    }
}
