package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageRequest;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 抽奖记录
 * @author xufeng
 * @date 2022/9/7 9:53 上午
 */

@Service
@Slf4j
public class DrawRecordFundsBaseService {

    @Autowired
    private DrawRecordQueryProvider drawRecordQueryProvider;

    @ReturnSensitiveWords(functionName = "f_lottery_record_return_sign_word")
    public  List<DrawRecordVO> query(Operator operator, DrawRecordPageRequest request){
        return drawRecordQueryProvider.page(request).getContext().getDrawRecordVOPage().getContent();
    }

}
