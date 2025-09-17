package com.wanmi.sbc.customer.ledgerreceiverrel.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Joiner;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.vo.LakalaDistributionBindVO;
import com.wanmi.sbc.customer.bean.vo.LakalaProviderBindVO;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.company.model.root.CompanyInfo;
import com.wanmi.sbc.customer.company.repository.CompanyInfoRepository;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.repository.CustomerDetailRepository;
import com.wanmi.sbc.customer.employee.model.root.Employee;
import com.wanmi.sbc.customer.employee.repository.EmployeeRepository;
import com.wanmi.sbc.customer.ledger.lakala.builder.LakalaParamBuilder;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgeraccount.repository.LedgerAccountRepository;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.repository.LedgerFileRepository;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.customer.ledgerreceiverrel.repository.LedgerReceiverRelRepository;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root.LedgerReceiverRelRecord;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.service.LedgerReceiverRelRecordService;
import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import com.wanmi.sbc.customer.ledgersupplier.repository.LedgerSupplierRepository;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.mq.ProducerService;
import com.wanmi.sbc.customer.repository.CustomerRepository;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.customer.store.repository.StoreRepository;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.EcApplyRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.Ec003;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaEcApplyRequest;
import com.wanmi.sbc.empower.api.response.ledger.lakala.EcApplyResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * <p>分账绑定关系业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Slf4j
@Service("LedgerReceiverRelService")
public class LedgerReceiverRelService {
	@Autowired
	private LedgerReceiverRelRepository ledgerReceiverRelRepository;

	@Autowired
	private LedgerAccountRepository ledgerAccountRepository;

	@Autowired
	private LedgerSupplierRepository ledgerSupplierRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LedgerReceiverRelRecordService ledgerReceiverRelRecordService;

	private static final Integer PAGE_SIZE = 1000;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private GeneratorService generatorService;

	@Autowired
	private LedgerProvider ledgerProvider;

	@Autowired
	private LedgerFileRepository ledgerFileRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerDetailRepository customerDetailRepository;

	@Value("${system.company.name}")
	private String SYSTEM_COMPANY_NAME;

	/**
	 * 新增分账绑定关系
	 * @author 许云鹏
	 */
	@Transactional
	public LedgerReceiverRel add(LedgerReceiverRel entity) {
		ledgerReceiverRelRepository.save(entity);
		return entity;
	}

	/**
	 * 批量保存数据
	 * @param list
	 */
	@Transactional
	public void saveAll(List<LedgerReceiverRel> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			ledgerReceiverRelRepository.saveAll(list);
		}
	}

	/**
	 * 单个删除分账绑定关系
	 * @author 许云鹏
	 */
	@Transactional
	public void deleteById(String id) {
		ledgerReceiverRelRepository.deleteById(id);
	}

	/**
	 * 批量删除分账绑定关系
	 * @author 许云鹏
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ledgerReceiverRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询分账绑定关系
	 * @author 许云鹏
	 */
	public LedgerReceiverRel getOne(String id){
		return ledgerReceiverRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO).orElse(null);
	}

	/**
	 * 分页查询分账绑定关系
	 * @author 许云鹏
	 */
	public Page<LedgerReceiverRel> page(LedgerReceiverRelQueryRequest queryReq){
		return ledgerReceiverRelRepository.findAll(
				LedgerReceiverRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询分账绑定关系
	 * @author 许云鹏
	 */
	public List<LedgerReceiverRel> list(LedgerReceiverRelQueryRequest queryReq){
		return ledgerReceiverRelRepository.findAll(LedgerReceiverRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public LedgerReceiverRelVO wrapperVo(LedgerReceiverRel ledgerReceiverRel) {
		if (ledgerReceiverRel != null){
			LedgerReceiverRelVO ledgerReceiverRelVO = KsBeanUtil.convert(ledgerReceiverRel, LedgerReceiverRelVO.class);
			return ledgerReceiverRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(LedgerReceiverRelQueryRequest queryReq) {
		return ledgerReceiverRelRepository.count(LedgerReceiverRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根基外部id查询
	 * @param applyId
	 * @return
	 */
	public LedgerReceiverRelVO  findByApplyId(String applyId){
		return wrapperVo(ledgerReceiverRelRepository.findByApplyId(applyId).orElse(null));
	}


	@Transactional
	public void updateBindState(Integer bindState, String rejectReason, LocalDateTime bindTime, String id){
		ledgerReceiverRelRepository.updateBindState(bindState, rejectReason, bindTime,id);
	}

	/**
	 * 批量保存绑定关系
	 * @param accountId
	 */
	public void batchAddByAccountId(String accountId){
		Optional<LedgerAccount> optional = ledgerAccountRepository.findByIdAndDelFlag(accountId, DeleteFlag.NO);
		if (!optional.isPresent()) {
			return;
		}
		LedgerAccount account = optional.get();
		if (LedgerAccountType.MERCHANTS.toValue() == account.getAccountType()) {
			addByMer(account);
		} else if(LedgerReceiverType.PROVIDER.toValue() == account.getReceiverType()){
			addByReceiver(account);
		}
	}

	/**
	 * 指定商户与未绑定的供应商建立关系
	 * @param account
	 */
	public void addByMer(LedgerAccount account) {
		Boolean error = Boolean.FALSE;
		LedgerSupplier supplier = ledgerSupplierRepository.findByLedgerAccountId(account.getId());
		Integer count = ledgerAccountRepository.countReceiver();
		Integer pageNum = count % PAGE_SIZE > 0 ? count / PAGE_SIZE + 1 : count / PAGE_SIZE;
		for(int i = 0; i < pageNum; i++) {
			try {
				Pageable pageable = PageRequest.of(i, PAGE_SIZE);
				List<LedgerAccount> accountList = ledgerAccountRepository.findReceiverPage(pageable);

				//筛选出未建立关系的账户
				List<String> businessIds = accountList.stream().map(LedgerAccount::getBusinessId).collect(Collectors.toList());
				List<String> existData = ledgerReceiverRelRepository.filterByReceiverId(businessIds, Long.valueOf(account.getBusinessId()));
				accountList = accountList.stream()
						.filter(a -> !existData.contains(a.getBusinessId()))
						.collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(accountList)) {
					//查询供应商数据
					List<Long> providerIds = accountList.stream()
							.map(a -> Long.valueOf(a.getBusinessId()))
							.collect(Collectors.toList());
					Map<Long, CompanyInfo> companyInfoMap = new HashMap<>();
					Map<Long, String> employeeAccountMap = new HashMap<>();
					if (CollectionUtils.isNotEmpty(providerIds)) {
						List<CompanyInfo> companyInfos = companyInfoRepository.queryByCompanyinfoIds(providerIds, DeleteFlag.NO);
						if (CollectionUtils.isNotEmpty(companyInfos)) {
							companyInfoMap = companyInfos.stream().collect(Collectors.toMap(CompanyInfo::getCompanyInfoId, Function.identity()));
						}

						List<Employee> mainEmployeeList = employeeRepository.findMainEmployeeList(providerIds, DeleteFlag.NO);
						if (CollectionUtils.isNotEmpty(mainEmployeeList)) {
							employeeAccountMap = mainEmployeeList.stream().collect(Collectors.toMap(Employee::getCompanyInfoId, Employee::getAccountName));
						}
					}

					List<LedgerReceiverRel> rels = new ArrayList<>();
					for (LedgerAccount ledgerAccount : accountList) {
						CompanyInfo companyInfo = companyInfoMap.get(Long.valueOf(ledgerAccount.getBusinessId()));
						String employeeAccount = employeeAccountMap.get(Long.valueOf(ledgerAccount.getBusinessId()));

						LedgerReceiverRel rel = new LedgerReceiverRel();
						rel.setLedgerSupplierId(supplier.getId());
						rel.setSupplierId(Long.valueOf(account.getBusinessId()));
						rel.setReceiverId(ledgerAccount.getBusinessId());
						rel.setReceiverType(ledgerAccount.getReceiverType());
						rel.setAccountState(ledgerAccount.getAccountState());
						rel.setBindState(LedgerBindState.UNBOUND.toValue());
						rel.setBindTime(LocalDateTime.now());
						rel.setDelFlag(DeleteFlag.NO);
						if (companyInfo != null) {
							rel.setReceiverName(companyInfo.getSupplierName());
							rel.setReceiverCode(companyInfo.getCompanyCode());
						}
						rel.setReceiverAccount(employeeAccount);
						rels.add(rel);
					}
					saveAll(rels);
					producerService.esAddLedgerBindInfo(rels);
				}
			} catch (Exception e) {
				log.error("批量新增分账绑定出现异常，异常信息:{}", e);
				log.error("批量新增分账绑定出现异常，清分账户id:{}", account.getId());
				error = Boolean.TRUE;
			}
		}
		if (error) {
			LedgerReceiverRelRecord record = new LedgerReceiverRelRecord();
			record.setAccountId(account.getId());
			record.setBusinessType(LedgerAccountType.MERCHANTS.toValue());
			ledgerReceiverRelRecordService.add(record);
		} else {
			ledgerReceiverRelRecordService.deleteByAccountId(account.getId());
		}
	}

	/**
	 * 指定供应商与所有商户建立绑定关系
	 * @param account
	 */
	public void addByReceiver(LedgerAccount account){
		Boolean error = Boolean.FALSE;
		Integer count = ledgerAccountRepository.countMer();
		Integer pageNum = count % PAGE_SIZE > 0 ? count / PAGE_SIZE + 1 : count / PAGE_SIZE;
		for(int i = 0; i < pageNum; i++) {
			try {
				Pageable pageable = PageRequest.of(i, PAGE_SIZE);
				List<LedgerAccount> accountList = ledgerAccountRepository.findMerPage(pageable);

				//筛选出未建立关系的账户
				List<Long> businessIds = accountList.stream().map(a -> Long.valueOf(a.getBusinessId())).collect(Collectors.toList());
				List<Long> existData = ledgerReceiverRelRepository.filterBySupplierId(businessIds, account.getBusinessId());
				accountList = accountList.stream()
						.filter(a -> !existData.contains(Long.valueOf(a.getBusinessId())))
						.collect(Collectors.toList());
				List<String> accountIds = accountList.stream().map(LedgerAccount::getId).collect(Collectors.toList());
				List<LedgerSupplier> suppliers = ledgerSupplierRepository.findByLedgerAccountIdIn(accountIds);
				Map<String, LedgerSupplier> supplierMap = new HashMap<>();
				if (CollectionUtils.isNotEmpty(suppliers)) {
					supplierMap = suppliers.stream().collect(Collectors.toMap(LedgerSupplier::getLedgerAccountId, Function.identity()));
				}
				CompanyInfo companyInfo = companyInfoRepository.findByCompanyInfoIdAndDelFlag(Long.valueOf(account.getBusinessId()), DeleteFlag.NO);
				Employee mainEmployee = employeeRepository.findMainEmployee(Long.valueOf(account.getBusinessId()), DeleteFlag.NO);

				List<LedgerReceiverRel> rels = new ArrayList<>();
				for (LedgerAccount ledgerAccount : accountList) {
					LedgerSupplier ledgerSupplier = supplierMap.get(ledgerAccount.getId());

					LedgerReceiverRel rel = new LedgerReceiverRel();
					rel.setLedgerSupplierId(ledgerSupplier.getId());
					rel.setSupplierId(Long.valueOf(ledgerAccount.getBusinessId()));
					rel.setReceiverId(account.getBusinessId());
					rel.setReceiverType(account.getReceiverType());
					rel.setAccountState(account.getAccountState());
					rel.setBindState(LedgerBindState.UNBOUND.toValue());
					rel.setBindTime(LocalDateTime.now());
					rel.setDelFlag(DeleteFlag.NO);
					if (companyInfo != null) {
						rel.setReceiverName(companyInfo.getSupplierName());
						rel.setReceiverCode(companyInfo.getCompanyCode());
					}
					if (mainEmployee != null) {
						rel.setReceiverAccount(mainEmployee.getAccountName());
					}

					rels.add(rel);
				}
				saveAll(rels);
				producerService.esAddLedgerBindInfo(rels);
			} catch (Exception e) {
				log.error("批量新增分账绑定出现异常，异常信息:{}", e);
				log.error("批量新增分账绑定出现异常，清分账户id:{}", account.getId());
				error = Boolean.TRUE;
			}
		}
		if (error) {
			LedgerReceiverRelRecord record = new LedgerReceiverRelRecord();
			record.setAccountId(account.getId());
			record.setBusinessType(LedgerAccountType.RECEIVER.toValue());
			ledgerReceiverRelRecordService.add(record);
		} else {
			ledgerReceiverRelRecordService.deleteByAccountId(account.getId());
		}
	}

	/**
	 * 修改公司名称
	 * @param companyInfoId
	 * @param name
	 */
	@Transactional
	public void updateCompanyInfo(String companyInfoId, String name) {
		ledgerReceiverRelRepository.updateName(name, companyInfoId);
		ledgerSupplierRepository.updateName(name, Long.valueOf(companyInfoId));
		//更新es
		producerService.esLedgerBindInfoUpdate(companyInfoId, name, null);
	}

	/**
	 * 修改分销员信息
	 */
	@Transactional
	public void updateDistributionName(String receiverId, String name) {
		ledgerReceiverRelRepository.updateName(name, receiverId);
		//更新es
		producerService.esLedgerBindInfoUpdate(receiverId, name, null);
	}

	/**
	 * 修改分销员信息
	 */
	@Transactional
	public void updateAccount(String receiverId, String account) {
		ledgerReceiverRelRepository.updateAccount(account, receiverId);
		//更新es
		producerService.esLedgerBindInfoUpdate(receiverId, null, account);
	}

	/**
	 * 查询商户和指定接收方中未绑定的的接收方
	 * @param supplierId
	 * @param receiverStoreIds
	 * @return
	 */
	public List<Long> findUnBindList(Long supplierId, List<Long> receiverStoreIds){
		List<Long> storeIds = new ArrayList<>();
		if (getGatewayOpen()) {
			List<Store> stores = storeRepository.queryListByIds(DeleteFlag.NO, receiverStoreIds);
			if (CollectionUtils.isNotEmpty(stores)) {
				//查询已绑定的数据
				List<String> companyInfoIds = stores.stream().map(store -> store.getCompanyInfoId().toString()).collect(Collectors.toList());
				List<String> receiverIds = ledgerReceiverRelRepository.findRelList(supplierId, companyInfoIds);
				//过滤出未绑定的店铺id
				List<Long> unBindReceiverIds = companyInfoIds.stream().filter(id -> !receiverIds.contains(id)).map(Long::parseLong).collect(Collectors.toList());
				storeIds = stores.stream().filter(store -> unBindReceiverIds.contains(store.getCompanyInfoId())).map(Store::getStoreId).collect(Collectors.toList());
			}
		}
		return storeIds;
	}

	/**
	 * 查询商户和指定接收方中未绑定的的接收方
	 * @param customerId
	 * @param storeIds
	 * @return
	 */
	public List<Long> findUnBindList(String customerId, List<Long> storeIds){
		List<Long> unBindStores = new ArrayList<>();
		if (getGatewayOpen()) {
			List<Store> stores = storeRepository.queryListByIds(DeleteFlag.NO, storeIds);
			if (CollectionUtils.isNotEmpty(stores)) {
				//查询已绑定的数据
				List<Long> companyInfoIds = stores.stream().map(store -> store.getCompanyInfoId()).collect(Collectors.toList());
				List<Long> supplerIds = ledgerReceiverRelRepository.findDistributionRelList(customerId, companyInfoIds);
				//过滤出未绑定的店铺id
				List<Long> unBindSupplierIds = companyInfoIds.stream().filter(id -> !supplerIds.contains(id)).collect(Collectors.toList());
				unBindStores = stores.stream()
						.filter(store -> unBindSupplierIds.contains(store.getCompanyInfoId())).map(Store::getStoreId)
						.collect(Collectors.toList());
			}
		}
		return unBindStores;
	}

	/**
	 * 查询拉卡拉支付开关
	 * @return
	 */
	public Boolean getGatewayOpen() {
		PayGatewayVO payGatewayVO = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGatewayVO.class);
		if (payGatewayVO == null) {
			return Boolean.FALSE;
		}
		return IsOpen.YES.equals(payGatewayVO.getIsOpen());
	}

	/**
	 * 查询接收方已绑定的店铺id
	 * @param receiverId
	 * @return
	 */
	public List<Long> findSupplierIdByReceiverId(String receiverId) {
		List<Long> supplierIds = ledgerReceiverRelRepository.findSupplierIdByReceiverId(receiverId);
		return storeRepository.findStoreByCompanyInfoIds(supplierIds);
	}

	/**
	 * 获取结算授权委托协议
	 * @param relId
	 * @return
	 */
	@Transactional
	public String applyReceiverContract(String relId, Long supplierId){
		LedgerReceiverRel rel = ledgerReceiverRelRepository.findByIdAndDelFlagAndSupplierId(relId, DeleteFlag.NO, supplierId);
		if (rel == null || !checkCanApply(rel)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		LedgerAccount supplierAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(rel.getSupplierId().toString(), DeleteFlag.NO);
		LedgerAccount receiverAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(rel.getReceiverId(), DeleteFlag.NO);
		String receiverName = LedgerReceiverType.DISTRIBUTION.toValue() == receiverAccount.getReceiverType()
				? receiverAccount.getLarName() : receiverAccount.getMerBlisName();

		Ec003 ec = LakalaParamBuilder.buildEc003(supplierAccount.getMerBlisName(), SYSTEM_COMPANY_NAME, receiverName);
		LakalaEcApplyRequest applyRequest = LakalaEcApplyRequest.builder()
				.acctName(supplierAccount.getAcctName())
				.acctNo(supplierAccount.getAcctNo())
				.mobile(supplierAccount.getMerContactMobile())
				.businessLicenseNo(supplierAccount.getMerBlis())
				.businessLicenseName(supplierAccount.getMerBlisName())
				.openningBankCode(supplierAccount.getOpenningBankCode())
				.openningBankName(supplierAccount.getOpenningBankName())
				.ecTypeCode(Constants.EC003)
				.certNo(supplierAccount.getLarIdCard())
				.certName(supplierAccount.getLarName())
				.ecContentParameters(JSONObject.toJSONString(ec))
				.orderNo(generatorService.generateEc003OrderNo())
				.build();
		BaseResponse baseResponse = ledgerProvider.ecApply(EcApplyRequest.builder()
				.lakalaEcApplyRequest(applyRequest).build());

		EcApplyResponse response = BeanUtils.beanCovert(baseResponse.getContext(), EcApplyResponse.class);
		rel.setEcUrl(response.getResultUrl());
		rel.setEcApplyId(response.getEcApplyId().toString());
		rel.setEcContentId(null);
		rel.setEcNo(null);
		rel.setBindContractId(null);
//		rel.setRejectReason(null);
		ledgerReceiverRelRepository.save(rel);
		return response.getResultUrl();
	}

	/**
	 * 检查状态
	 * @param rel
	 * @return
	 */
	public Boolean checkCanApply(LedgerReceiverRel rel){
		LedgerReceiverType type = LedgerReceiverType.fromValue(rel.getReceiverType());
		switch (type) {
			case PLATFORM:
			case PROVIDER:
				return LedgerBindState.UNBOUND.toValue() == rel.getBindState()
						|| LedgerBindState.BINDING_FAILURE.toValue() == rel.getBindState();
			case DISTRIBUTION:
				return CheckState.WAIT_CHECK.toValue() == rel.getCheckState() && LedgerBindState.UNBOUND.toValue() == rel.getBindState()
						|| CheckState.CHECKED.toValue() == rel.getCheckState() && LedgerBindState.BINDING_FAILURE.toValue() == rel.getBindState();
			default:
				return Boolean.FALSE;
		}
	}

	/**
	 * 获取协议内容
	 * @param relId
	 * @param supplierId
	 * @return
	 */
	public String findReceiverContract(String relId, Long supplierId) {
		LedgerReceiverRel rel = ledgerReceiverRelRepository.findByIdAndDelFlagAndSupplierId(relId, DeleteFlag.NO, supplierId);
		if (rel != null && StringUtils.isNotBlank(rel.getEcContentId())) {
			LedgerFile file = ledgerFileRepository.findById(rel.getEcContentId()).orElse(null);
			if (Objects.nonNull(file) && Constants.PDF.equalsIgnoreCase(file.getFileExt())) {
				return "data:application/pdf;base64," + Base64Utils.encodeToString(file.getContent());
			}
		}
		return null;
	}

	/**
	 * 检查商户
	 * @param supplierId
	 */
	public void checkBindState(Long supplierId){
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(supplierId.toString(), DeleteFlag.NO);
		if (account == null || LedgerAccountState.PASS.toValue() != account.getAccountState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010063);
		}

		LedgerReceiverRel rel = ledgerReceiverRelRepository.findBySupplierIdAndReceiverIdAndDelFlag(
				supplierId,
				Constants.BOSS_DEFAULT_STORE_ID.toString(),
				DeleteFlag.NO);
		if (rel == null || LedgerBindState.BINDING.toValue() != rel.getBindState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010063);
		}
	}

	/**
	 *	驳回 分销员分账绑定关系
	 * @param request
	 */
	@Transactional
	public void checkBIndInfo(LedgerReceiverRelCheckRequest request){
		LedgerReceiverRel rel = ledgerReceiverRelRepository
				.findByIdAndDelFlagAndSupplierId(request.getId(), DeleteFlag.NO, request.getSupplierId());
		if (rel == null || LedgerReceiverType.DISTRIBUTION.toValue() != rel.getReceiverType()) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		if (CheckState.WAIT_CHECK.toValue() != rel.getCheckState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010065);
		}
        rel.setCheckState(CheckState.NOT_PASS.toValue());
        rel.setRejectReason(request.getRejectReason());
		ledgerReceiverRelRepository.save(rel);
		//同步ES
		producerService.esAddLedgerBindInfo(Collections.singletonList(rel));
	}

	/**
	 * @description 电子合同EC003申请ID查询
	 * @author  edz
	 * @date: 2022/9/13 15:29
	 * @param ecApplyId
	 * @return com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO
	 */
	public LedgerReceiverRelVO findByEcApplyId(String ecApplyId){
		return KsBeanUtil.convert(ledgerReceiverRelRepository.findByEcApplyId(ecApplyId), LedgerReceiverRelVO.class);
	}

	/**
	 * @description 更新合同EC003相关信息
	 * @author  edz
	 * @date: 2022/9/13 17:10
	 * @param updateEC003InfoRequest
	 * @return void
	 */
	@Transactional
	public void updateEC003Info(UpdateEC003InfoRequest updateEC003InfoRequest){
		if (StringUtils.isNotBlank(updateEC003InfoRequest.getEcUrl())){
			ledgerReceiverRelRepository.updateEC003Info(updateEC003InfoRequest.getEcContentId(),
					updateEC003InfoRequest.getEcNO(), updateEC003InfoRequest.getEcUrl(),
					updateEC003InfoRequest.getEcApplyId());
		} else {
			ledgerReceiverRelRepository.updateEC003Info(updateEC003InfoRequest.getEcContentId(),
					updateEC003InfoRequest.getEcNO(), updateEC003InfoRequest.getEcApplyId());
		}

	}

	/**
	 * 根据接收方id查询数量
	 * @param receiverId
	 * @return
	 */
	public Long countReceiver(String receiverId) {
		return ledgerReceiverRelRepository.countReceiver(receiverId);
	}

	/**
	 * 分销员申请分账绑定
	 * @param supplierId
	 * @param customerId
	 */
	@Transactional
	public void distributionApply(Long supplierId, String customerId) {
		//检查商户清分账户
		LedgerAccount supplierAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(supplierId.toString(), DeleteFlag.NO);
		if (supplierAccount == null
				|| LedgerAccountState.PASS.toValue() != supplierAccount.getAccountState()
				|| LedgerState.PASS.toValue() != supplierAccount.getLedgerState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010066);
		}
		//检查分销员清分账户
		LedgerAccount receiverAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(customerId, DeleteFlag.NO);
		if (receiverAccount == null || LedgerAccountState.PASS.toValue() != receiverAccount.getAccountState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010067);
		}

		Customer customer = customerRepository.findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO);
		CustomerDetail customerDetail = customerDetailRepository.findAnyByCustomerId(customerId);
		LedgerSupplier ledgerSupplier = ledgerSupplierRepository.findByLedgerAccountId(supplierAccount.getId());
		LedgerReceiverRel oldRel = ledgerReceiverRelRepository.findBySupplierIdAndReceiverIdAndDelFlag(supplierId, customerId, DeleteFlag.NO);
		//已申请-驳回/绑定失败的才允许重新申请
		if (oldRel != null && !(CheckState.NOT_PASS.toValue() == oldRel.getCheckState()
				|| CheckState.CHECKED.toValue() == oldRel.getCheckState() && LedgerBindState.BINDING_FAILURE.toValue() == oldRel.getBindState())) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010068);
		}

		LedgerReceiverRel rel = new LedgerReceiverRel();
		if (oldRel != null) {
			rel.setId(oldRel.getId());
		}
		rel.setLedgerSupplierId(ledgerSupplier.getId());
		rel.setSupplierId(supplierId);
		rel.setReceiverId(customerId);
		rel.setReceiverName(customerDetail.getCustomerName());
		rel.setReceiverAccount(customer.getCustomerAccount());
		rel.setReceiverType(LedgerReceiverType.DISTRIBUTION.toValue());
		rel.setAccountState(receiverAccount.getAccountState());
		rel.setBindState(LedgerBindState.UNBOUND.toValue());
		rel.setBindTime(LocalDateTime.now());
		rel.setDelFlag(DeleteFlag.NO);
		rel.setCheckState(CheckState.WAIT_CHECK.toValue());
		ledgerReceiverRelRepository.save(rel);
		producerService.esAddLedgerBindInfo(Collections.singletonList(rel));
	}

	/**
	 * @description 商家和供应商绑定状态
	 * @author  edz
	 * @date: 2022/9/15 15:29
	 * @param request
	 * @return com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.customer.bean.vo.LakalaProviderBindVO>
	 */
	public MicroServicePage<LakalaProviderBindVO> queryProviderBindStatePage(ProviderBindStateQueryRequest request){

		String sql = "select a.id as id, c.supplier_name as providerName, c.company_code as providerCode, ifnull(b" +
				".bind_state, 0) as ledgerBindState " +
				"from ledger_account a" +
				"         left join ledger_receiver_rel b on a.business_id = b.receiver_id and b.del_flag = 0 and b.supplier_id = :param1 " +
				"         left join company_info c on a.business_id = c.company_info_id and c.del_flag = 0 " +
				"where a.account_type = 1" +
				"  and a.receiver_type = 1" +
				"  and a.account_state = 2" +
				"  and a.del_flag = 0 ";

		String countSql = "select count(a.id)" +
				"from ledger_account a" +
				"         left join ledger_receiver_rel b on a.business_id = b.receiver_id and b.del_flag = 0 and b.supplier_id = :param1 " +
				"         left join company_info c on a.business_id = c.company_info_id and c.del_flag = 0 " +
				"where a.account_type = 1" +
				"  and a.receiver_type = 1" +
				"  and a.account_state = 2" +
				"  and a.del_flag = 0 ";
		if (CollectionUtils.isNotEmpty(request.getLedgerAccountId())){
			String whereId = " and a.id in ('" + Joiner.on("',").join(request.getLedgerAccountId()) + "') ";
			sql = sql.concat(whereId);
			countSql = countSql.concat(whereId);
		}
		if (StringUtils.isNotBlank(request.getProviderName())){
			String whereProviderName = " and a.business_id in (select ci.company_info_id from company_info ci where ci.supplier_name like '%" + request.getProviderName() + "%') ";
			sql = sql.concat(whereProviderName);
			countSql = countSql.concat(whereProviderName);
		} else if(StringUtils.isNotBlank(request.getProviderCode())){
			String whereProviderCode = " and a.business_id in (select ci.company_info_id from company_info ci where ci.company_code like '%" + request.getProviderCode() + "%') ";
			sql = sql.concat(whereProviderCode);
			countSql = countSql.concat(whereProviderCode);
		}

		if (Objects.nonNull(request.getLedgerBindState())){
			String whereBandState = "and ifnull(b.bind_state, 0) = " + request.getLedgerBindState().toValue();
			sql = sql.concat(whereBandState);
			countSql = countSql.concat(whereBandState);
		}
		String orderBy = " order by a.create_time desc ";
		sql = sql.concat(orderBy);
		countSql = countSql.concat(orderBy);

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("param1", request.getSupplierId());
		int pageNumber = request.getPageNum();
		int pageSize = request.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(NativeQuery.class)
				.addScalar("id", StandardBasicTypes.STRING)
				.addScalar("providerName", StandardBasicTypes.STRING)
				.addScalar("providerCode", StandardBasicTypes.STRING)
				.addScalar("ledgerBindState", StandardBasicTypes.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(LakalaProviderBindVO.class));
		List <LakalaProviderBindVO> fooList = query.getResultList();
		Query queryTotal = entityManager.createNativeQuery(countSql);
		queryTotal.setParameter("param1", request.getSupplierId());
		BigInteger countResult = (BigInteger) queryTotal.getSingleResult();
		int i= countResult.intValue();
		return new MicroServicePage<>(fooList, request.getPageable(),i);
	}


	public MicroServicePage<LakalaDistributionBindVO> queryDistributionBindStatePage(DistributionBindStateQueryRequest request){

		String sql = "select a.id as id, c.customer_name as distributionName, c.customer_account as " +
				"distributionAccount, ifnull(b.bind_state, 0) as ledgerBindState " +
				"from ledger_account a" +
				"         left join ledger_receiver_rel b on a.business_id = b.receiver_id and b.del_flag = 0 and b.supplier_id = :param1 " +
				"         left join distribution_customer c on a.business_id = c.customer_id and c.del_flag = 0 " +
				"where a.account_type = 1" +
				"  and a.receiver_type = 2" +
				"  and a.account_state = 2" +
				"  and a.del_flag = 0 ";

		String countSql = "select count(a.id)" +
				"from ledger_account a" +
				"         left join ledger_receiver_rel b on a.business_id = b.receiver_id and b.del_flag = 0 and b.supplier_id = :param1 " +
				"         left join distribution_customer c on a.business_id = c.customer_id and c.del_flag = 0 " +
				"where a.account_type = 1" +
				"  and a.receiver_type = 2" +
				"  and a.account_state = 2" +
				"  and a.del_flag = 0 ";
		if (CollectionUtils.isNotEmpty(request.getLedgerAccountId())){
			String whereId = " and a.id in ('" + Joiner.on("',").join(request.getLedgerAccountId()) + "') ";
			sql = sql.concat(whereId);
			countSql = countSql.concat(whereId);
		}
		if (StringUtils.isNotBlank(request.getDistributionName())){
			String whereDistributionName = " and a.business_id in (select dc.customer_id from distribution_customer " +
					"dc where dc.customer_name like '%" + request.getDistributionName() + "%') ";
			sql = sql.concat(whereDistributionName);
			countSql = countSql.concat(whereDistributionName);
		} else if(StringUtils.isNotBlank(request.getDistributionAccount())){
			String whereDistributionAccount = " and a.business_id in (select dc.customer_id from distribution_customer dc where dc.customer_account like '%" + request.getDistributionAccount() + "%') ";
			sql = sql.concat(whereDistributionAccount);
			countSql = countSql.concat(whereDistributionAccount);
		}

		if (Objects.nonNull(request.getLedgerBindState())){
			String whereBindState = "and ifnull(b.bind_state, 0) = " + request.getLedgerBindState().toValue();
			sql = sql.concat(whereBindState);
			countSql = countSql.concat(whereBindState);
		}
		String ordderBy = " order by a.create_time desc ";
		sql = sql.concat(ordderBy);
		countSql = countSql.concat(ordderBy);

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("param1", request.getSupplierId());
		int pageNumber = request.getPageNum();
		int pageSize = request.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(NativeQuery.class)
				.addScalar("id", StandardBasicTypes.STRING)
				.addScalar("distributionName", StandardBasicTypes.STRING)
				.addScalar("distributionAccount", StandardBasicTypes.STRING)
				.addScalar("ledgerBindState", StandardBasicTypes.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(LakalaDistributionBindVO.class));
		List <LakalaDistributionBindVO> fooList = query.getResultList();
		Query queryTotal = entityManager.createNativeQuery(countSql);
		queryTotal.setParameter("param1", request.getSupplierId());
		BigInteger countResult = (BigInteger) queryTotal.getSingleResult();
		int i= countResult.intValue();
		return new MicroServicePage<>(fooList, request.getPageable(),i);
	}

	/**
	 * 保存分账合作协议文件
	 * @param request
	 */
	@Transactional
	public void saveBindContractFile(LedgerBindContractFileSaveRequest request){

		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(request.getSupplierId().toString(), DeleteFlag.NO);
		if (account == null) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010054);
		}

		String oldFileId;

		//保存文件
		LedgerFile file = new LedgerFile();
		file.setFileExt(Constants.PDF);
		file.setContent(request.getContract());
		file = ledgerFileRepository.save(file);

		if (BindContractUploadType.APPLY_MER == request.getType()) {
			oldFileId = account.getBindContractId();
			//清分账户保存文件id
			account.setBindContractId(file.getId());
			ledgerAccountRepository.save(account);
		} else {
			//分账绑定保存文件id
			LedgerReceiverRel rel = ledgerReceiverRelRepository.findByIdAndDelFlag(request.getRelId(), DeleteFlag.NO).orElse(null);
			if (rel == null) {
				throw new SbcRuntimeException(CustomerErrorCodeEnum.K010070);
			}
			oldFileId = rel.getBindContractId();
			rel.setBindContractId(file.getId());
			ledgerReceiverRelRepository.save(rel);
		}
		//删除旧文件
		if (StringUtils.isNotBlank(oldFileId)) {
			ledgerFileRepository.deleteById(oldFileId);
		}
	}
}

