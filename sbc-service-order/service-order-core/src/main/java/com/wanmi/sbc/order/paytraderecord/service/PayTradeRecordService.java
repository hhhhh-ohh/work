package com.wanmi.sbc.order.paytraderecord.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdsRequest;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.enums.TradeType;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordDeleteAndSaveRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordCreditStatisticsResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.bean.vo.PayTradeRecordVO;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import com.wanmi.sbc.order.payingmemberpayrecord.repository.PayingMemberPayRecordRepository;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.repository.TradeRecordRepository;
import com.wanmi.sbc.order.util.GeneratorUtils;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PayTradeRecordService {

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private PayingMemberPayRecordRepository payingMemberPayRecordRepository;

    @Autowired
    private PayTimeSeriesService payTimeSeriesService;

    /**
     * 获取交易记录
     *
     * @param id 交易记录主键
     * @return
     */
    public PayTradeRecord queryTradeRecord(String id) {
        return  tradeRecordRepository.getOne(id);
    }

    /**
     * 获取交易记录
     *
     * @param chargeId 交易对象id
     * @return
     */
    public PayTradeRecord queryByChargeId(String chargeId) {
        return tradeRecordRepository.findByChargeId(chargeId);
    }

    /**
     * 获取交易记录，订单查询场景下父订单号也作为匹配项，用于合并支付查询
     *
     * @param businessId 业务id
     * @return
     */
    public PayTradeRecord queryByBusinessId(String businessId) {
        return tradeRecordRepository.findByBusinessId(businessId);
    }

    /***
     * 根据业务ID（订单号或退单号）
     * 返回<businessId:tradeNo> 格式Map
     * 如果参数为空或返回值为空，返回空Map，不会抛出异常
     * @param businessIdList    业务ID集合
     * @return                  业务ID:业务流水号 Map
     */
    public Map<String, String> queryTradeNoMapByBusinessIds(List<String> businessIdList){
        // 0.校验参数
        if(WmCollectionUtils.isEmpty(businessIdList)){
            return Maps.newHashMap();
        }
        // 1.查询数据
        List<PayTradeRecord> tradeRecordList = tradeRecordRepository.findByBusinessIdList(businessIdList);
        // 2.返回组织结果集<businessId:tradeNo>
        return WmCollectionUtils.notEmpty2Map(tradeRecordList, PayTradeRecord::getBusinessId, PayTradeRecord::getTradeNo);
    }


    /**
     * 根据业务id获取交易记录统计
     *
     * @param businessId 业务id
     * @return
     */
    public long countByBusinessId(String businessId) {
        return tradeRecordRepository.countByBusinessId(businessId);
    }


    public PayTradeRecord findTopByBusinessIdAndStatus(String businessId, TradeStatus status){
        return tradeRecordRepository.findTopByBusinessIdAndStatus(businessId,status);
    }

    public int deleteByBusinessId(String businessId){
        return tradeRecordRepository.deleteByBusinessId(businessId);
    }


    public int deleteByPayNo(String payNo){
        return tradeRecordRepository.deleteByPayNo(payNo);
    }

    public BigDecimal sumCreditUsedAmount(){
        return tradeRecordRepository.sumCreditUsedAmount();
    }


    public BigDecimal sumCreditHasRepaidAmount(){
        return tradeRecordRepository.sumCreditHasRepaidAmount();
    }

    /**
     * 根据业务ID批量查询
     * @param businessIds    业务ID集合
     * @return
     */
    public List<PayTradeRecordVO> queryByBusinessIds(List<String> businessIds) {
        List<PayTradeRecordVO> recordList = KsBeanUtil.convert(tradeRecordRepository.findByBusinessIdIn(businessIds), PayTradeRecordVO.class);
        List<PayChannelItemVO> payChannelItemVOList  = paySettingQueryProvider.findPayChannelItemListByIds(ChannelItemByIdsRequest.builder()
                .channelItemIds(recordList.parallelStream().map(PayTradeRecordVO::getChannelItemId).collect(Collectors.toList()))
                .build()).getContext().getPayChannelItemVOList();
        return recordList.parallelStream().map(payTradeRecordVO -> {
            payChannelItemVOList.forEach(payChannelItemVO -> {
                if(payTradeRecordVO.getChannelItemId().equals(payChannelItemVO.getId())) {
                    payTradeRecordVO.setPayChannelItemVo(payChannelItemVO);
                    payChannelItemVO.setGateway(payChannelItemVO.getGateway());
                }
            });
            return payTradeRecordVO;
        }).collect(Collectors.toList());
    }


    /**
     * 根据订单号查询支付结果
     *
     * @param tid 业务订单号
     * @return 支付结果
     */
    @Transactional
    public TradeStatus queryPayResult(String tid) {
        PayTradeRecord record = queryByBusinessId(tid);
        if (!Objects.isNull(record)) {
            //如果重复支付，判断状态
            if (record.getStatus() == TradeStatus.SUCCEED) {
                return TradeStatus.SUCCEED;
            } else if (record.getStatus() == TradeStatus.PROCESSING && !Objects.isNull(record.getChargeId())) {
                //未支付状态，跟踪支付结果
                return TradeStatus.PROCESSING;
            }
        }
        return null;
    }

    /**
     * 根据退单与相关订单号号查询退单退款状态
     *
     * @param rid 业务退单号
     * @param tid 业务订单号
     * @return null-无退款记录 | TradeStatus-退款状态
     */
    @Transactional
    public TradeStatus queryRefundResult(String rid, String tid) {
        PayTradeRecord refundRecord = queryByBusinessId(rid);
        if (Objects.nonNull(refundRecord)) {
            if (refundRecord.getStatus() == TradeStatus.SUCCEED) {
                return TradeStatus.SUCCEED;
            } else if (refundRecord.getStatus() == TradeStatus.PROCESSING && Objects.nonNull(refundRecord.getChargeId())) {
                // 处理中退单，跟踪状态
                return TradeStatus.FAILURE;
            }
        }
        return null;
    }


    /**
     * @description 从交易记录中统计授信支付和还款的总数
     * @author  chenli
     * @date 2021/4/22 15:27
     * @return
     **/
    public PayTradeRecordCreditStatisticsResponse getPayTradeRecordCreditStatistics() {
        // 所有账户的授信已使用额度（当前周期）
        BigDecimal creditUsedAmount = sumCreditUsedAmount();
        // 所有账户的授信已还款额度（当前周期）
        BigDecimal creditHasRepaidAmount = sumCreditHasRepaidAmount();

        return PayTradeRecordCreditStatisticsResponse.builder()
                .creditUsedAmount(Objects.nonNull(creditUsedAmount) ? creditUsedAmount : BigDecimal.ZERO)
                .creditHasRepaidAmount(Objects.nonNull(creditHasRepaidAmount) ? creditHasRepaidAmount : BigDecimal.ZERO)
                .build();
    }


    /**
     * 同步回调 添加 数据
     *
     * @param request
     * @param record
     */
    public void addPayRecordForCallBack(PayTradeRecordRequest request, PayTradeRecord record) {
        record.setTradeNo(request.getTradeNo());
        if (request.getResult_code().equals(WXPayConstants.SUCCESS)) {
            record.setStatus(TradeStatus.SUCCEED);
        } else {
            record.setStatus(TradeStatus.FAILURE);
        }
        record.setCallbackTime(record.getCallbackTime() == null ? LocalDateTime.now() : record.getCallbackTime());
        record.setPracticalPrice(request.getPracticalPrice());
        record.setFinishTime(LocalDateTime.now());
        record.setTradeNo(request.getTradeNo());
        record.setPayNo(request.getPayNo());
        tradeRecordRepository.save(record);
    }

    public void addPayRecordForCallBackByTimeSeries(PayTimeSeries payTimeSeries) {
        PayTradeRecord payTradeRecord = tradeRecordRepository.findByBusinessId(payTimeSeries.getBusinessId());
        if(payTradeRecord==null){
            payTradeRecord = new PayTradeRecord();
            payTradeRecord.setId(GeneratorUtils.generatePT());
            payTradeRecord.setTradeType(TradeType.PAY);

        }
        payTradeRecord.setBusinessId( payTimeSeries.getBusinessId() );
        payTradeRecord.setChargeId( payTimeSeries.getChargeId() );
        payTradeRecord.setApplyPrice( payTimeSeries.getApplyPrice() );
        payTradeRecord.setPracticalPrice( payTimeSeries.getPracticalPrice() );
        payTradeRecord.setStatus( payTimeSeries.getStatus() );
        payTradeRecord.setChannelItemId( payTimeSeries.getChannelItemId() );
        payTradeRecord.setCreateTime( payTimeSeries.getCreateTime() );
        payTradeRecord.setCallbackTime( payTimeSeries.getCallbackTime() );
        payTradeRecord.setClientIp( payTimeSeries.getClientIp() );
        payTradeRecord.setTradeNo( payTimeSeries.getTradeNo() );
        payTradeRecord.setPayNo( payTimeSeries.getPayNo() );
        payTradeRecord.setFinishTime(LocalDateTime.now());
        tradeRecordRepository.save(payTradeRecord);
        //更新流水号
        payTimeSeriesService.modify(payTimeSeries);
    }

    /**
     * 同步回调 添加 数据
     *
     * @param request
     * @param record
     */
    public void addPayRecordForCallBack(PayTradeRecordRequest request, PayingMemberPayRecord record) {
        if (request.getResult_code().equals(WXPayConstants.SUCCESS)) {
            record.setStatus(TradeStatus.SUCCEED.toValue());
        } else {
            record.setStatus(TradeStatus.FAILURE.toValue());
        }
        record.setCallbackTime(record.getCallbackTime() == null ? LocalDateTime.now() : record.getCallbackTime());
        record.setPracticalPrice(request.getPracticalPrice());
        record.setFinishTime(LocalDateTime.now());
        payingMemberPayRecordRepository.save(record);
    }


    /**
     * 添加交易记录
     *
     * @param recordRequest
     */
    @Transactional
    public void addPayTradeRecord(PayTradeRecordRequest recordRequest) {
        PayTradeRecord record = new PayTradeRecord();
        KsBeanUtil.copyPropertiesThird(recordRequest, record);
        record.setId(GeneratorUtils.generatePT());
        if (recordRequest.getResult_code().equals(WXPayConstants.SUCCESS)) {
            record.setStatus(TradeStatus.SUCCEED);
        } else {
            record.setStatus(TradeStatus.FAILURE);
        }
        record.setTradeType(TradeType.PAY);
        record.setFinishTime(LocalDateTime.now());
        record.setCreateTime(LocalDateTime.now());
        deleteByBusinessId(recordRequest.getBusinessId());
        tradeRecordRepository.saveAndFlush(record);
    }

    /**
     * @description 新增支付单
     * @author  songhanlin
     * @date: 2021/6/7 下午8:18
     * @param request 支付单内容
     **/
    @Transactional
    public void queryAndSave(PayTradeRecordRequest request) {
        String businessId = request.getBusinessId();
        if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordRepository.findByBusinessId(businessId);
            if (Objects.nonNull(payingMemberPayRecord) && payingMemberPayRecord.getStatus() == TradeStatus.SUCCEED.toValue()) {
                //如果重复支付，判断状态，已成功状态则做异常提示
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            } else {
                if (payingMemberPayRecord == null) {
                    payingMemberPayRecord = new PayingMemberPayRecord();
                    payingMemberPayRecord.setId(GeneratorUtils.generatePT());
                }
                payingMemberPayRecord.setApplyPrice(request.getApplyPrice());
                payingMemberPayRecord.setBusinessId(request.getBusinessId());
                payingMemberPayRecord.setChannelItemId(request.getChannelItemId().intValue());
                payingMemberPayRecord.setCreateTime(LocalDateTime.now());
                payingMemberPayRecord.setStatus(TradeStatus.PROCESSING.toValue());
                payingMemberPayRecordRepository.saveAndFlush(payingMemberPayRecord);
            }
        } else {
            //是否重复支付
            PayTradeRecord record = tradeRecordRepository.findByBusinessId(request.getBusinessId());

            if (Objects.nonNull(record) && record.getStatus() == TradeStatus.SUCCEED) {
                //如果重复支付，判断状态，已成功状态则做异常提示
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            } else {
                if (record == null) {
                    record = new PayTradeRecord();
                    record.setId(GeneratorUtils.generatePT());
                }
                record.setApplyPrice(request.getApplyPrice());
                record.setBusinessId(request.getBusinessId());
                record.setClientIp(request.getClientIp());
                record.setChannelItemId(request.getChannelItemId());
                record.setTradeType(TradeType.PAY);
                record.setCreateTime(LocalDateTime.now());
                record.setStatus(TradeStatus.PROCESSING);
                record.setPayNo(request.getPayNo());
                tradeRecordRepository.saveAndFlush(record);
            }
        }
    }

    /**
     * @description 新增支付单
     * @author  songhanlin
     * @date: 2021/6/7 下午8:18
     * @param request 支付单内容
     **/
    @Transactional
    public PayTradeRecordResponse deleteAndSave(PayTradeRecordDeleteAndSaveRequest request) {
        PayTradeRecord record = new PayTradeRecord();
        record.setId(GeneratorUtils.generatePT());
        record.setApplyPrice(request.getApplyPrice());
        record.setBusinessId(request.getBusinessId());
        record.setClientIp(request.getClientIp());
        record.setChannelItemId(request.getChannelItemId());
        record.setTradeType(TradeType.REFUND);
        record.setStatus(TradeStatus.PROCESSING);
        record.setCreateTime(LocalDateTime.now());
        record.setPayNo(request.getPayNo());
        // 删除失败或未成功获取到退款对象的记录
        tradeRecordRepository.deleteByBusinessId(request.getBusinessId());
        record = tradeRecordRepository.saveAndFlush(record);

        return KsBeanUtil.convert(record, PayTradeRecordResponse.class);
    }

    /**
     * @description 新增支付单
     * @author  songhanlin
     * @date: 2021/6/7 下午8:18
     * @param request 支付单内容
     **/
    @Transactional
    public void saveAndFlush(PayTradeRecordRequest request) {
        String businessId = request.getBusinessId();
        if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordRepository.findByBusinessId(businessId);
            if (Objects.nonNull(payingMemberPayRecord)) {
                if(Objects.nonNull(request.getBusinessId())) {
                    payingMemberPayRecord.setBusinessId(request.getBusinessId());
                }
                if(Objects.nonNull(request.getChargeId())) {
                    payingMemberPayRecord.setChargeId(request.getChargeId());
                }
                if(Objects.nonNull(request.getApplyPrice())) {
                    payingMemberPayRecord.setApplyPrice(request.getApplyPrice());
                }
                if(Objects.nonNull(request.getPracticalPrice())) {
                    payingMemberPayRecord.setPracticalPrice(request.getPracticalPrice());
                }
                if(Objects.nonNull(request.getChannelItemId())) {
                    payingMemberPayRecord.setChannelItemId(request.getChannelItemId().intValue());
                }
                if(Objects.nonNull(request.getFinishTime())) {
                    payingMemberPayRecord.setFinishTime(request.getFinishTime());
                }
                if(Objects.nonNull(request.getCallbackTime())) {
                    payingMemberPayRecord.setCallbackTime(request.getCallbackTime());
                }
                if(Objects.nonNull(request.getStatus())) {
                    payingMemberPayRecord.setStatus(request.getStatus().toValue());
                }

            } else {
                payingMemberPayRecord = new PayingMemberPayRecord();
                payingMemberPayRecord.setId(GeneratorUtils.generatePT());
                payingMemberPayRecord.setApplyPrice(request.getApplyPrice());
                payingMemberPayRecord.setBusinessId(request.getBusinessId());
                payingMemberPayRecord.setChargeId(request.getChargeId());
                payingMemberPayRecord.setChannelItemId(request.getChannelItemId().intValue());
                payingMemberPayRecord.setCreateTime(LocalDateTime.now());
                payingMemberPayRecord.setStatus(TradeStatus.PROCESSING.toValue());
            }
            payingMemberPayRecordRepository.saveAndFlush(payingMemberPayRecord);
        } else {
            //是否重复支付
            PayTradeRecord record = tradeRecordRepository.findByBusinessId(businessId);
            if (Objects.nonNull(record)) {
                if(Objects.nonNull(request.getBusinessId())) {
                    record.setBusinessId(request.getBusinessId());
                }
                if(Objects.nonNull(request.getChargeId())) {
                    record.setChargeId(request.getChargeId());
                }
                if(Objects.nonNull(request.getApplyPrice())) {
                    record.setApplyPrice(request.getApplyPrice());
                }
                if(Objects.nonNull(request.getPracticalPrice())) {
                    record.setPracticalPrice(request.getPracticalPrice());
                }
                if(Objects.nonNull(request.getChannelItemId())) {
                    record.setChannelItemId(request.getChannelItemId());
                }
                if(Objects.nonNull(request.getFinishTime())) {
                    record.setFinishTime(request.getFinishTime());
                }
                if(Objects.nonNull(request.getCallbackTime())) {
                    record.setCallbackTime(request.getCallbackTime());
                }
                if(Objects.nonNull(request.getClientIp())) {
                    record.setClientIp(request.getClientIp());
                }
                if(Objects.nonNull(request.getTradeNo())) {
                    record.setTradeNo(request.getTradeNo());
                }
                if(Objects.nonNull(request.getStatus())) {
                    record.setStatus(request.getStatus());
                }
                if(Objects.nonNull(request.getTradeType())) {
                    record.setTradeType(request.getTradeType());
                }
                if(Objects.nonNull(request.getPayNo())) {
                    record.setPayNo(request.getPayNo());
                }
            } else {
                record = new PayTradeRecord();
                record.setId(GeneratorUtils.generatePT());
                record.setApplyPrice(request.getApplyPrice());
                record.setBusinessId(request.getBusinessId());
                record.setChargeId(request.getChargeId());
                record.setClientIp(request.getClientIp());
                record.setChannelItemId(request.getChannelItemId());
                record.setTradeType(TradeType.PAY);
                record.setCreateTime(LocalDateTime.now());
                record.setStatus(TradeStatus.PROCESSING);
                record.setPayNo(request.getPayNo());
            }
            tradeRecordRepository.saveAndFlush(record);
        }
    }


    public void save(PayTradeRecordRequest request) {
        PayTradeRecord record = KsBeanUtil.convert(request,PayTradeRecord.class);
        record.setId(GeneratorUtils.generatePT());
        tradeRecordRepository.save(record);
    }

    /**
     * 同步回调 添加 数据
     *
     * @param request
     * @param record
     */
    public void addLklCasherPayRecordForCallBack(PayTradeRecordRequest request, PayTradeRecord record) {
        record.setTradeNo(request.getTradeNo());
        if (request.getResult_code().equals(Constants.STR_2)) {
            record.setStatus(TradeStatus.SUCCEED);
        } else {
            record.setStatus(TradeStatus.FAILURE);
        }
        record.setCallbackTime(record.getCallbackTime() == null ? LocalDateTime.now() : record.getCallbackTime());
        record.setPracticalPrice(request.getPracticalPrice());
        record.setApplyPrice(request.getPracticalPrice());
        record.setFinishTime(LocalDateTime.now());
        record.setTradeNo(request.getTradeNo());
        record.setCreateTime(LocalDateTime.now());
        tradeRecordRepository.save(record);
    }

    public List<PayTradeRecord> findBusinessIdByTransNoList(List<String> transNoList) {
        return tradeRecordRepository.findBusinessIdByTransNoList(transNoList);
    }
}
