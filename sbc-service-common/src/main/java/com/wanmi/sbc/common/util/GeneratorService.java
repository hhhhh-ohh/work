package com.wanmi.sbc.common.util;

import com.wanmi.sbc.common.constant.LakalaCommonConstant;
import com.wanmi.sbc.common.enums.SnowFlake;
import com.wanmi.sbc.common.enums.TradeIdType;
import com.wanmi.sbc.common.util.auth.NetworkUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 订单ID生成
 * Created by Administrator on 2017/4/18.
 */
@Service
public class GeneratorService {

    /**
     * 父订单号前缀
     */
    public static final String _PREFIX_PARENT_TRADE_ID = "PO";
    public static final String NEW_PREFIX_PARENT_TRADE_ID = TradeIdType.PARENT_TRADE_ID.toValue()+"";

    /**
     * 订单号前缀
     */
    public static final String _PREFIX_TRADE_ID = "O";
    public static final String NEW_PREFIX_TRADE_ID = TradeIdType.TRADE_ID.toValue()+"";

    /**
     * 供应商订单号前缀
     */
    public static final String _PREFIX_PROVIDER_TRADE_ID = "P";

    /**
     * 供应商第三方子订单号前缀
     */
    public static final String _PREFIX_PROVIDER_THIRD_TRADE_ID = "PS";

    /**
     * 商家订单号前缀
     */
    public static final String _PREFIX_STORE_TRADE_ID = "S";

    /**
     * 订单尾款订单号前缀
     */
    public static final String _PREFIX_TRADE_TAIL_ID = "OT";

    /**
     * 退单尾款订单号前缀
     */
    public static final String _PREFIX_RETURN_TRADE_TAIL_ID = "RT";

    public static final String _PREFIX_GOODS_ADJUST_RECORD_ID = "AP";

    /**
     * 付费会员记录id
     */
    public static final String _PREFIX_PAY_MEMBER_RECORD_ID = "PM";

    /**
     * 授信还款单号前缀
     */
    public static final String _PREFIX_CREDIT_REPAY_ID = "CR";

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_7, Locale.UK);

    private static DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_3, Locale.UK);

    private static Integer DATA_CENTER_ID = null;

    private static Integer MACHINE_ID = null;

    static {

        try {
            String ip = NetworkUtil.getLocalHostLANAddress().getHostAddress();
            if(StringUtils.isNotEmpty(ip)){
                String[] strs = ip.split("\\.");
                if (strs.length>3) {
                    DATA_CENTER_ID = Integer.valueOf(strs[2]);
                    MACHINE_ID = Integer.valueOf(strs[3]);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (DATA_CENTER_ID == null){
            DATA_CENTER_ID = RandomUtils.nextInt(0,255);
        }
        if(MACHINE_ID == null){
            MACHINE_ID = RandomUtils.nextInt(0,255);
        }
    }
    private static final SnowFlake T_SNOW_FLAKE = new SnowFlake(DATA_CENTER_ID,MACHINE_ID);
    private static final SnowFlake P_SNOW_FLAKE = new SnowFlake(DATA_CENTER_ID+1L,MACHINE_ID+1L);


    /**
     * 生成雪花算法NextId
     */
    public Long generateNextId() {
        return T_SNOW_FLAKE.nextId();
    }

    /**
     * 生成tid
     * O+ "yyyyMMddHHmmss" + random(3)
     */
    public String generateTid() {
        return TradeIdType.TRADE_ID.toValue()+""+ T_SNOW_FLAKE.nextId();
    }

    /**
     * 生成供应商tid
     * P+ "yyyyMMddHHmmss" + random(3)
     */
    public String generateProviderTid() {
        return _PREFIX_PROVIDER_TRADE_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成供应商子订单tid
     * P+ "yyyyMMddHHmmss" + random(3)
     */
    public String generateProviderThirdTid() {
        return _PREFIX_PROVIDER_THIRD_TRADE_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成供应商tid
     * P+ "yyyyMMddHHmmss" + random(3)
     */
    public String generateStoreTid() {
        return _PREFIX_STORE_TRADE_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成尾款tid(临时)
     * OT+ "yyyyMMddHHmmss" + random(3)
     */
    public String generateTailTid() {
        return _PREFIX_TRADE_TAIL_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成父订单号 po+id （用于组织批量订单合并支付，目前仅在支付与退款中使用）
     * O+ "yyyyMMddHHmmss" + random(3)
     */
    public String generatePoId() {
        return TradeIdType.PARENT_TRADE_ID.toValue()+""+ P_SNOW_FLAKE.nextId();
    }

    /**
     * 生成oid
     * OD+ "yyyyMMddHHmmss" + random(3)
     *
     * @return
     */
    public String generateOid() {
        return "OD" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成支付单号
     *
     * @return
     */
    public String generatePid() {
        return "PD" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成支付单流水号
     *
     * @return
     */
    public String generateSid() {
        return "P" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }


    /**
     * 生成退款单号
     *
     * @return
     */
    public String generateRid() {
        return "RD" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 退款单流水单号
     *
     * @return
     */
    public String generateRF() {
        return "RF" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成拼团团号
     */
    public String generateGrouponNo() {
        return "G" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }


    /**
     * 生成id 自定义前缀
     *
     * @param prefix
     * @return
     */
    public String generate(String prefix) {
        return prefix + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成财务对账明细id
     *
     * @return
     */
    public String generateRNid() {
        return "RN" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成调价记录单号
     *
     * @return
     */
    public String generateAPId() {
        return _PREFIX_GOODS_ADJUST_RECORD_ID + LocalDateTime.now().format(dateTimeFormatter3) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成还款单号
     */
    public String generateRepayOrderCode() {
        return _PREFIX_CREDIT_REPAY_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成分销员业绩记录id
     *
     * @return
     */
    public String generateDistributionPerformanceId() {
        return "DP" + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成付费记录ID
     * @return
     */
    public String generatePayingMemberRecordId(){
        return _PREFIX_PAY_MEMBER_RECORD_ID + LocalDateTime.now().format(dateTimeFormatter) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成分账操作id
     * @return
     */
    public String generateLedgerOrderNo(){
        return DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3) + RandomStringUtils.randomNumeric(8);
    }

    /**
     * @description EC003合同申请ID
     * @author  edz
     * @date: 2022/9/2 17:19
     * @return java.lang.String
     */
    public String generateEc003OrderNo(){
        return LakalaCommonConstant.EC003 + DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3) + RandomStringUtils.randomNumeric(8);
    }

}
