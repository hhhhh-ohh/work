package com.wanmi.sbc.grouponsharerecord;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.goods.api.provider.grouponsharerecord.GrouponShareRecordProvider;
import com.wanmi.sbc.goods.api.provider.grouponsharerecord.GrouponShareRecordQueryProvider;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.*;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.*;
import com.wanmi.sbc.goods.bean.vo.GrouponShareRecordVO;
import com.wanmi.sbc.order.api.provider.groupon.GrouponProvider;
import com.wanmi.sbc.util.CommonUtil;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/7 15:27
 * @Description：拼团分享访问记录管理
 */
@Tag(name =  "拼团分享访问记录管理API", description =  "GrouponShareRecordController")
@RestController
@Validated
@RequestMapping(value = "/groupon/share/record")
public class GrouponShareRecordController {

    @Autowired
    private GrouponShareRecordQueryProvider grouponShareRecordQueryProvider;

    @Autowired
    private GrouponShareRecordProvider grouponShareRecordProvider;

    @Autowired
    private GrouponProvider grouponProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询拼团分享访问记录")
    @PostMapping("/page")
    public BaseResponse<GrouponShareRecordPageResponse> getPage(@RequestBody @Valid GrouponShareRecordPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return grouponShareRecordQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询拼团分享访问记录")
    @PostMapping("/list")
    public BaseResponse<GrouponShareRecordListResponse> getList(@RequestBody @Valid GrouponShareRecordListRequest listReq) {
        return grouponShareRecordQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询拼团分享访问记录")
    @GetMapping("/{id}")
    public BaseResponse<GrouponShareRecordByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GrouponShareRecordByIdRequest idReq = new GrouponShareRecordByIdRequest();
        idReq.setId(id);
        return grouponShareRecordQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增拼团分享记录")
    @PostMapping("/add/share")
    public BaseResponse<GrouponShareRecordAddResponse> addShare(@RequestBody @Valid GrouponShareRecordAddRequest addReq) {
        addReq.setCustomerId(commonUtil.getOperatorId());
        addReq.setTerminalSource(commonUtil.getTerminal());
        addReq.setType(0);
        if (StringUtils.isBlank(addReq.getGrouponActivityId())){
            return BaseResponse.FAILED();
        }
        if (StringUtils.isBlank(addReq.getCustomerId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return grouponShareRecordProvider.add(addReq);
    }

    @Operation(summary = "新增拼团访问记录")
    @PostMapping("/add/visit")
    public BaseResponse<GrouponShareRecordAddResponse> addVisit(@RequestBody @Valid GrouponShareRecordAddRequest addReq) {
        addReq.setCustomerId(commonUtil.getOperatorId());
        addReq.setTerminalSource(commonUtil.getTerminal());
        addReq.setType(1);
        if (StringUtils.isBlank(addReq.getGrouponActivityId())){
            return BaseResponse.FAILED();
        }
        return grouponShareRecordProvider.add(addReq);
    }

    @Operation(summary = "修改拼团分享访问记录")
    @PutMapping("/modify")
    public BaseResponse<GrouponShareRecordModifyResponse> modify(@RequestBody @Valid GrouponShareRecordModifyRequest modifyReq) {
        return grouponShareRecordProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除拼团分享访问记录")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GrouponShareRecordDelByIdRequest delByIdReq = new GrouponShareRecordDelByIdRequest();
        delByIdReq.setId(id);
        return grouponShareRecordProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除拼团分享访问记录")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid GrouponShareRecordDelByIdListRequest delByIdListReq) {
        return grouponShareRecordProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出拼团分享访问记录列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        GrouponShareRecordListRequest listReq = JSON.parseObject(decrypted, GrouponShareRecordListRequest.class);
        List<GrouponShareRecordVO> dataRecords = grouponShareRecordQueryProvider.list(listReq).getContext().getGrouponShareRecordVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("拼团分享访问记录列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<GrouponShareRecordVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("拼团活动ID", new SpelColumnRender<GrouponShareRecordVO>("grouponActivityId")),
            new Column("会员Id", new SpelColumnRender<GrouponShareRecordVO>("customerId")),
            new Column("SPU id", new SpelColumnRender<GrouponShareRecordVO>("goodsId")),
            new Column("SKU id", new SpelColumnRender<GrouponShareRecordVO>("goodsInfoId")),
            new Column("店铺ID", new SpelColumnRender<GrouponShareRecordVO>("storeId")),
            new Column("公司信息ID", new SpelColumnRender<GrouponShareRecordVO>("companyInfoId")),
            new Column("终端：1 H5，2pc，3APP，4小程序", new SpelColumnRender<GrouponShareRecordVO>("terminalSource")),
            new Column("分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片", new SpelColumnRender<GrouponShareRecordVO>("shareChannel")),
            new Column("分享人，通过分享链接访问的时候", new SpelColumnRender<GrouponShareRecordVO>("shareCustomerId")),
            new Column("0分享拼团，1通过分享链接访问拼团", new SpelColumnRender<GrouponShareRecordVO>("type"))
        };
        excelHelper.addSheet("拼团分享访问记录列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
