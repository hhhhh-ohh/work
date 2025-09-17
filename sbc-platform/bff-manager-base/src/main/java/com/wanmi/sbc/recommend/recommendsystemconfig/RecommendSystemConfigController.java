package com.wanmi.sbc.recommend.recommendsystemconfig;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig.RecommendSystemConfigProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendsystemconfig.RecommendSystemConfigQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig.*;
import com.wanmi.sbc.vas.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.bean.enums.ConfigType;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendSystemConfigVO;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;


@Tag(name =  "智能推荐配置管理API", description =  "RecommendSystemConfigController")
@RestController
@Validated
@RequestMapping(value = "/recommendsystemconfig")
public class RecommendSystemConfigController {

    @Autowired
    private RecommendSystemConfigQueryProvider recommendSystemConfigQueryProvider;

    @Autowired
    private RecommendSystemConfigProvider recommendSystemConfigProvider;

    @Autowired
    private AutoTagProvider autoTagProvider;

    @Operation(summary = "分页查询智能推荐配置")
    @PostMapping("/page")
    public BaseResponse<RecommendSystemConfigPageResponse> getPage(@RequestBody @Valid RecommendSystemConfigPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return recommendSystemConfigQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询智能推荐配置")
    @PostMapping("/list")
    public BaseResponse<RecommendSystemConfigListResponse> getList(@RequestBody @Valid RecommendSystemConfigListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return recommendSystemConfigQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询智能推荐配置")
    @GetMapping("/{id}")
    public BaseResponse<RecommendSystemConfigByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendSystemConfigByIdRequest idReq = new RecommendSystemConfigByIdRequest();
        idReq.setId(id);
        return recommendSystemConfigQueryProvider.getById(idReq);
    }

    @Operation(summary = "根据id查询智能推荐配置")
    @PostMapping("/getRecommendSystemConfig")
    public BaseResponse<RecommendSystemConfigByIdResponse> getRecommendSystemConfig(@RequestBody @Valid RecommendSystemConfigRequest request) {
        return recommendSystemConfigQueryProvider.getRecommendSystemConfig(request);
    }

    @Operation(summary = "新增智能推荐配置")
    @PostMapping("/add")
    public BaseResponse<RecommendSystemConfigAddResponse> add(@RequestBody @Valid RecommendSystemConfigAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreateTime(LocalDateTime.now());
        return recommendSystemConfigProvider.add(addReq);
    }

    @Operation(summary = "修改智能推荐配置")
    @PutMapping("/modify")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<RecommendSystemConfigModifyResponse> modify(@RequestBody @Valid RecommendSystemConfigModifyRequest modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());
        if (ConfigKey.USER_INTEREST_RECOMMEND_CONFIG.toValue().equals(modifyReq.getConfigKey())
                && ConfigType.USER_INTEREST_RECOMMEND_CONFIG.toValue().equals(modifyReq.getConfigType())
                && DefaultFlag.YES.toValue() == modifyReq.getStatus()) {
            // 基于用户兴趣推荐开启后，CRM-标签管理-偏好类标签中类目偏好、品类偏好、品牌偏好、店铺偏好会被删除重新引用
            autoTagProvider.initTagOfRecommendedStrategy();
        }
        return recommendSystemConfigProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除智能推荐配置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendSystemConfigDelByIdRequest delByIdReq = new RecommendSystemConfigDelByIdRequest();
        delByIdReq.setId(id);
        return recommendSystemConfigProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除智能推荐配置")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid RecommendSystemConfigDelByIdListRequest delByIdListReq) {
        return recommendSystemConfigProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出智能推荐配置列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        RecommendSystemConfigListRequest listReq = JSON.parseObject(decrypted, RecommendSystemConfigListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        List<RecommendSystemConfigVO> dataRecords = recommendSystemConfigQueryProvider.list(listReq).getContext().getRecommendSystemConfigVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("智能推荐配置列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            exportDataList(dataRecords, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 导出列表数据具体实现
     */
    private void exportDataList(List<RecommendSystemConfigVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("键", new SpelColumnRender<RecommendSystemConfigVO>("configKey")),
            new Column("类型", new SpelColumnRender<RecommendSystemConfigVO>("configType")),
            new Column("名称", new SpelColumnRender<RecommendSystemConfigVO>("configName")),
            new Column("备注", new SpelColumnRender<RecommendSystemConfigVO>("remark")),
            new Column("状态,0:未启用1:已启用", new SpelColumnRender<RecommendSystemConfigVO>("status")),
            new Column("配置内容，如JSON内容", new SpelColumnRender<RecommendSystemConfigVO>("context"))
        };
        excelHelper.addSheet("智能推荐配置列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
