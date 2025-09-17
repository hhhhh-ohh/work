package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.customersignrecord.CustomerSignRecordQueryProvider;
import com.wanmi.sbc.customer.api.provider.customersignrecord.CustomerSignRecordSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customersignrecord.*;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customersignrecord.CustomerSignRecordAddResponse;
import com.wanmi.sbc.customer.api.response.customersignrecord.CustomerSignRecordInitInfoResponse;
import com.wanmi.sbc.customer.api.response.customersignrecord.CustomerSignRecordListResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerSignRecordVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.CommonUtil;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
import java.util.Objects;


@Tag(name =  "用户签到记录管理API", description =  "CustomerSignRecordController")
@RestController
@Validated
@RequestMapping(value = "/customer/signrecord")
public class CustomerSignRecordController {

    @Autowired
    private CustomerSignRecordQueryProvider customerSignRecordQueryProvider;

    @Autowired
    private CustomerSignRecordSaveProvider customerSignRecordSaveProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Operation(summary = "查询用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public BaseResponse<CustomerSignRecordInitInfoResponse> getCustomerInfo() {
        String customerId = commonUtil.getOperatorId();
        BaseResponse<CustomerGetByIdResponse> customerById = customerQueryProvider.getCustomerById(
                new CustomerGetByIdRequest(customerId));
        CustomerSignRecordInitInfoResponse response = new CustomerSignRecordInitInfoResponse();
        response.setCustomerVO(customerById.getContext());
        //判断后台签到获取积分设置是否开启
        ConfigQueryRequest pointsRequest = new ConfigQueryRequest();
        pointsRequest.setConfigType(ConfigType.POINTS_BASIC_RULE_SIGN_IN.toValue());
        pointsRequest.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO pointsConfig = systemConfigQueryProvider.findByConfigTypeAndDelFlag(pointsRequest).getContext().getConfig();
        if(pointsConfig != null && pointsConfig.getStatus() == 1 ) {
            response.setPointFlag(Boolean.TRUE);
            response.setSignPoint(this.getValue(pointsConfig.getContext()));
        } else {
            response.setPointFlag(Boolean.FALSE);
        }
        //判断后台签到获取成长值设置是否开启
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.GROWTH_VALUE_BASIC_RULE_SIGN_IN.toValue());
        request.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO growthConfig = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
        if(growthConfig != null && growthConfig.getStatus() == 1) {
            response.setGrowthFlag(Boolean.TRUE);
            response.setGrowthValue(this.getValue(growthConfig.getContext()));
        }else {
            response.setGrowthFlag(Boolean.FALSE);
        }
        //
        //判断今天是否签到
        CustomerSignRecordVO todayRecord = customerSignRecordQueryProvider.getRecordByDays(CustomerSignRecordGetByDaysRequest.builder().
                customerId(customerId).days(0L).build()).getContext().getCustomerSignRecordVO();
        //确认昨天是否签到
        CustomerSignRecordVO yesterdayRecord = customerSignRecordQueryProvider.getRecordByDays(CustomerSignRecordGetByDaysRequest.builder().
                customerId(customerId).days(1L).build()).getContext().getCustomerSignRecordVO();
        if(Objects.isNull(yesterdayRecord)) {
            if(Objects.isNull(todayRecord)) {
                //连续签到置为0
                response.getCustomerVO().setSignContinuousDays(0);
            }else {
                response.getCustomerVO().setSignContinuousDays(1);
            }
        }
        if(Objects.isNull(todayRecord)) {
            response.setSignFlag(Boolean.FALSE);
        } else {
            response.setSignFlag(Boolean.TRUE);
        }
        CustomerVO customerVO = response.getCustomerVO();
        if (Objects.nonNull(customerVO)){
            customerVO.setCustomerPassword(null);
            customerVO.setCustomerPayPassword(null);
            customerVO.setCustomerSaltVal(null);
            customerVO.getCustomerDetail().setCustomerAddress(null);
            customerVO.getCustomerDetail().setContactPhone(null);
        }
        response.setCustomerVO(customerVO);
        return BaseResponse.success(response);
    }

    @Operation(summary = "列表查询当月用户签到记录")
    @RequestMapping(value = "/listByMonth", method = RequestMethod.GET)
    public BaseResponse<CustomerSignRecordListResponse> getListByThisMonth() {
        CustomerSignRecordListRequest listReq = new CustomerSignRecordListRequest();
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setCustomerId(commonUtil.getOperatorId());
        BaseResponse<CustomerSignRecordListResponse> response = customerSignRecordQueryProvider.listByMonth(listReq);
        return response;
    }

    @Operation(summary = "列表查询用户签到记录", hidden = true)
    @PostMapping("/list")
    public BaseResponse<CustomerSignRecordListResponse> getList(@RequestBody @Valid CustomerSignRecordListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setCustomerId(commonUtil.getOperatorId());
        listReq.putSort("signRecord", "desc");
        return customerSignRecordQueryProvider.list(listReq);
    }

    @Operation(summary = "新增用户签到记录")
    @RequestMapping(value = "/add/{signTerminal}", method = RequestMethod.GET)
    public BaseResponse<CustomerSignRecordAddResponse> add(@PathVariable(value = "signTerminal") String signTerminal) {
        //加锁 防并发处理
        String checkDate = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd");
        String lockKey = CacheKeyConstant.CUSTOMER_SIGN_ADD_LOCK.concat(checkDate).concat(commonUtil.getOperatorId());
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        BaseResponse<CustomerSignRecordAddResponse> response;
        try {
            //判断今天是否已经签到完成
            CustomerSignRecordVO toDayRecord = customerSignRecordQueryProvider.getRecordByDays(CustomerSignRecordGetByDaysRequest.builder().
                    customerId(commonUtil.getOperatorId()).days(0L).build()).getContext().getCustomerSignRecordVO();
            if(!Objects.isNull(toDayRecord)) {
                return BaseResponse.FAILED();

            }
            //添加用户签到记录
            CustomerSignRecordAddRequest addRecordReq = new CustomerSignRecordAddRequest();
            addRecordReq.setCustomerId(commonUtil.getOperatorId());
            addRecordReq.setSignRecord(LocalDateTime.now());
            addRecordReq.setDelFlag(DeleteFlag.NO);
            addRecordReq.setSignIp(commonUtil.getOperator().getIp());
            if(TerminalSource.MINIPROGRAM == commonUtil.getTerminal()){
                signTerminal = "minipro";
            }
            addRecordReq.setSignTerminal(signTerminal);
            response = customerSignRecordSaveProvider.
                    add(addRecordReq);
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return response;
    }

    @Operation(summary = "根据id删除用户签到记录", hidden = true)
    @DeleteMapping("/{signRecordId}")
    public BaseResponse deleteById(@PathVariable String signRecordId) {
        if (signRecordId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerSignRecordDelByIdRequest delByIdReq = new CustomerSignRecordDelByIdRequest();
        delByIdReq.setSignRecordId(signRecordId);
        return customerSignRecordSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除用户签到记录", hidden = true)
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid CustomerSignRecordDelByIdListRequest delByIdListReq) {
        return customerSignRecordSaveProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出用户签到记录列表", hidden = true)
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        CustomerSignRecordListRequest listReq = JSON.parseObject(decrypted, CustomerSignRecordListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("signRecordId", "desc");
        List<CustomerSignRecordVO> dataRecords = customerSignRecordQueryProvider.list(listReq).getContext().getCustomerSignRecordVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("用户签到记录列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            exportDataList(dataRecords, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 积分/成长值转换
     * @param value
     * @return
     */
    private Long getValue(String value){
        if(StringUtils.isNotBlank(value)){
            return JSONObject.parseObject(value).getLong("value");
        }else{
            return null;
        }

    }

    /**
     * 导出列表数据具体实现
     */
    private void exportDataList(List<CustomerSignRecordVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("用户id", new SpelColumnRender<CustomerSignRecordVO>("customerId")),
            new Column("签到日期记录", new SpelColumnRender<CustomerSignRecordVO>("signRecord"))
        };
        excelHelper.addSheet("用户签到记录列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
