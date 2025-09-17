package com.wanmi.sbc.report;

import com.wanmi.ares.enums.ExportStatus;
import com.wanmi.ares.provider.ExportDataServiceProvider;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author edz
 * @className ExportCenter
 * @description 数据导出
 * @date 2021/5/28 4:54 下午
 **/
@Slf4j
@Service
public class ExportCenter {

    @Autowired
    private ExportDataServiceProvider exportDataServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * @description 发送报表任务
     * @author  xuyunpeng
     * @date 2021/5/28 5:18 下午
     * @param exportDataRequest
     * @return
     */
    public void sendExport(ExportDataRequest exportDataRequest){
        exportDataRequest.setUserId(commonUtil.getOperatorId());
        exportDataRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        exportDataRequest.setStoreId(commonUtil.getStoreId());
        exportDataRequest.setExportStatus(ExportStatus.WAIT_EXPORT);
        exportDataServiceProvider.sendExportBusinessDataRequest(exportDataRequest);
    }
}
