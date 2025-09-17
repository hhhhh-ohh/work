package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagProvider;
import com.wanmi.sbc.crm.api.request.autotag.AutoTagPageRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AutoTagJobHandler {

    @Autowired
    private AutoTagProvider autoTagProvider;

    static final String DATE_RANG = "1";
    static final String IDS = "2";

    @XxlJob(value = "autoTagJobHandler")
    public void execute() throws Exception {
        String s = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(s)){
            if (s.contains("&")) {
                String[] arr = s.split("&");
                if (arr.length < Constants.TWO) {
                    Resp(s);
                    XxlJobHelper.handleFail();
                    return;
                }
                String type = arr[0];
                if (DATE_RANG.equals(type)) {
                    String dateArr = arr[1];
                    if (!dateArr.contains("~")) {
                        Resp(s);
                        XxlJobHelper.handleFail();
                        return;
                    }
                    String[] dateRang = dateArr.split("~");
                    if (dateRang.length < Constants.TWO) {
                        Resp(s);
                        XxlJobHelper.handleFail();
                        return;
                    }
                    AutoTagPageRequest request = new AutoTagPageRequest();
                    LocalDateTime begin =
                            LocalDate.parse(dateRang[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MIN);
                    request.setCreateTimeBegin(begin);
                    request.setCreateTimeEnd(LocalDate.parse(dateRang[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MAX));
                    request.setDelFlag(DeleteFlag.NO);
                    request.setSortColumn("createTime");
                    request.setSortRole(SortType.DESC.toValue());
                    request.setPageSize(100);
                    try {
                        autoTagProvider.getSql(request);
                    } catch (Exception e){
                        XxlJobHelper.handleFail("CRM服务报错");
                    }

                } else if (IDS.equals(type)){
                    String idsArr = arr[1];
                    if (StringUtils.isBlank(idsArr)) {
                        Resp(s);
                        XxlJobHelper.handleFail();
                        return;
                    }
                    String[] ids = idsArr.split(",");
                    List<Long> idList = new ArrayList<>(ids.length);
                    for (String id : ids){
                        idList.add(Long.valueOf(id));
                    }
                    AutoTagPageRequest request = new AutoTagPageRequest();
                    request.setDelFlag(DeleteFlag.NO);
                    request.setIdList(idList);
                    try {
                        autoTagProvider.getSql(request);
                    } catch (Exception e){
                        XxlJobHelper.handleFail("CRM服务报错");
                    }
                }
            } else {
                Resp(s);
                XxlJobHelper.handleFail();
            }
        } else {
            Resp(s);
            XxlJobHelper.handleFail();
        }
    }

    private void Resp(String s) {
        XxlJobHelper.log("接受到的参数：{}", s);
        XxlJobHelper.log("正确参数格式-时间范围：1&2020-12-15～2020-12-16(注意~号是英文的)");
        XxlJobHelper.log("正确参数格式-当天：1&2020-12-15～2020-12-15(注意~号是英文的)");
        XxlJobHelper.log("正确参数格式-指定ID：2&1,2,3,4,5(注意,号是英文的)");
    }
}
