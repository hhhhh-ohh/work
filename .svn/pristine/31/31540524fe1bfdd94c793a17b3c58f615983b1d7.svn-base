package com.wanmi.sbc.customer.ledgeraccount.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountQueryRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerVerifyContractInfoRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelPageMobileRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.DistributionApplyRecordRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.SupplierApplyRecordRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountPicResponse;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.ledger.lakala.builder.LakalaParamBuilder;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgeraccount.repository.LedgerAccountRepository;
import com.wanmi.sbc.customer.ledgererrorrecord.service.LedgerErrorRecordService;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.repository.LedgerFileRepository;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.customer.ledgerreceiverrel.repository.LedgerReceiverRelRepository;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.customer.store.service.StoreService;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.provider.ledgercontent.LedgerContentQueryProvider;
import com.wanmi.sbc.empower.api.provider.ledgermcc.LedgerMccQueryProvider;
import com.wanmi.sbc.empower.api.request.Ledger.EcApplyRequest;
import com.wanmi.sbc.empower.api.request.Ledger.VerifyContractInfoRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.Ec;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaEcApplyRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaVerifyContractInfoRequest;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentByIdRequest;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccByIdRequest;
import com.wanmi.sbc.empower.api.response.ledger.lakala.EcApplyResponse;
import com.wanmi.sbc.empower.bean.vo.LedgerContentVO;
import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * <p>清分账户业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Service("LedgerAccountService")
public class LedgerAccountService {
	@Autowired
	private LedgerAccountRepository ledgerAccountRepository;

	@Autowired
	private LedgerFileRepository ledgerFileRepository;

	@Autowired
	private LedgerProvider ledgerProvider;

	@Autowired
	private GeneratorService generatorService;

	@Autowired
	private LedgerReceiverRelRepository ledgerReceiverRelRepository;

	@Autowired
	private LedgerContentQueryProvider ledgerContentQueryProvider;

	@Autowired
	private LedgerMccQueryProvider ledgerMccQueryProvider;

	@Autowired
	private LedgerErrorRecordService ledgerErrorRecordService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private EntityManager entityManager;

	@Value("${system.company.name}")
	private String SYSTEM_COMPANY_NAME;

	/**
	 * 保存清分账户
	 * @author 许云鹏
	 */
	@Transactional
	public String save(LedgerAccount entity) {
		if (StringUtils.isBlank(entity.getBusinessId())) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		//商户开户时，校验平台接收方是否已创建
		if (LedgerAccountType.MERCHANTS.toValue() == entity.getAccountType()) {
			LedgerAccount bossAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(Constants.BOSS_DEFAULT_STORE_ID.toString(), DeleteFlag.NO);
			if (bossAccount == null || LedgerAccountState.PASS.toValue() != bossAccount.getAccountState()) {
				throw new SbcRuntimeException(CustomerErrorCodeEnum.K010053);
			}
		}

		List<String> deleteFileIds = new ArrayList<>();
		//已存在账户，则进行修改操作
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(entity.getBusinessId(), DeleteFlag.NO);
        if (Objects.isNull(account)) {
			//首次创建
			account = new LedgerAccount();
			account.setDelFlag(DeleteFlag.NO);
		} else {
			//审核中、审核成功不支持修改
			if (LedgerAccountState.CHECKING.toValue() == account.getAccountState()
					|| LedgerAccountState.PASS.toValue() == account.getAccountState()) {
				throw new SbcRuntimeException(CustomerErrorCodeEnum.K010052);
			}
			//修改账户数据时，对比图片是否变动
			deleteFileIds = getDeleteFileIds(entity, account);

			account.setEcContent(null);
			account.setAccountRejectReason(null);
			account.setContractId(null);
			account.setEcNo(null);
			account.setLedgerApplyId(null);
			account.setMerCupNo(null);
			account.setTermNo(null);
//			account.setBindContractId(null);
		}
		KsBeanUtil.copyProperties(entity, account);

		String ecUrl = null;
		if (LedgerAccountType.MERCHANTS.toValue() == account.getAccountType()) {
			account.setAccountState(LedgerAccountState.NOT_CHECK.toValue());
			account.setLedgerState(LedgerAccountState.NOT_CHECK.toValue());
			//商户获取电子合同
			ecUrl = getEcContract(account);
		} else {
			account.setAccountState(LedgerAccountState.CHECKING.toValue());
		}
		ledgerAccountRepository.save(account);
		//删除变更的文件
		if (CollectionUtils.isNotEmpty(deleteFileIds)) {
			ledgerFileRepository.deleteByIdList(deleteFileIds);
		}
		return ecUrl;
	}

	/**
	 * 比较文件是否发生变更，如果变更，将旧文件删除
	 * @param newAccount
	 * @param oldAccount
	 * @return
	 */
	public List<String> getDeleteFileIds(LedgerAccount newAccount, LedgerAccount oldAccount){
		List<String> deleteFileIds = new ArrayList<>();
		if (!newAccount.getIdCardFrontPic().equals(oldAccount.getIdCardFrontPic())) {
			deleteFileIds.add(oldAccount.getIdCardFrontPic());
		}
		if (!newAccount.getIdCardBackPic().equals(oldAccount.getIdCardBackPic())) {
			deleteFileIds.add(oldAccount.getIdCardBackPic());
		}
		if (!newAccount.getBankCardPic().equals(oldAccount.getBankCardPic())) {
			deleteFileIds.add(oldAccount.getBankCardPic());
		}
		//非分销员
		if (!(LedgerAccountType.RECEIVER.toValue() == oldAccount.getAccountType()
				&& LedgerReceiverType.DISTRIBUTION.toValue() == oldAccount.getReceiverType())
				&& !newAccount.getBusinessPic().equals(oldAccount.getBusinessPic())) {
			deleteFileIds.add(oldAccount.getBusinessPic());
		}
		//商户
		if (LedgerAccountType.MERCHANTS.toValue() == oldAccount.getAccountType()) {
			if (!newAccount.getMerchantPic().equals(oldAccount.getMerchantPic())) {
				deleteFileIds.add(oldAccount.getMerchantPic());
			}
			if (!newAccount.getShopinnerPic().equals(oldAccount.getShopinnerPic())) {
				deleteFileIds.add(oldAccount.getShopinnerPic());
			}
		}
		return deleteFileIds;
	}

	/**
	 * 商户进件校验
	 */
	public void checkData(LedgerVerifyContractInfoRequest request) {
		LakalaVerifyContractInfoRequest infoRequest = LakalaVerifyContractInfoRequest.builder()
				.acctIdcard(request.getLarIdCard())
				.acctNo(request.getAcctNo())
				.larIdcard(request.getLarIdCard())
				.merBizName(request.getMerBlisName())
				.merRegName(request.getMerBlisName())
				.merBlis(request.getMerBlis())
				.orderNo(generatorService.generateLedgerOrderNo())
				.build();
		ledgerProvider.verifyContractInfo(VerifyContractInfoRequest.builder().lakalaVerifyContractInfoRequest(infoRequest).build());
	}

	/**
	 * 获取电子合同
	 * @return
	 */
	private String getEcContract(LedgerAccount account) {
		String ecUrl = "";

		LedgerAccount bossAccount = ledgerAccountRepository.findByBusinessIdAndDelFlag(Constants.BOSS_DEFAULT_STORE_ID.toString(), DeleteFlag.NO);
		LedgerContentVO ledgerContentVO = ledgerContentQueryProvider.getById(LedgerContentByIdRequest.builder()
				.contentId(Long.valueOf(account.getMerBusiContent())).build())
				.getContext().getLedgerContentVO();

		Ec ec = LakalaParamBuilder.buildEc(account, bossAccount.getMerBlisName(), ledgerContentVO.getContentName(), SYSTEM_COMPANY_NAME);

		LakalaEcApplyRequest applyRequest = LakalaEcApplyRequest.builder()
				.acctName(account.getAcctName())
				.acctNo(account.getAcctNo())
				.mobile(account.getMerContactMobile())
				.businessLicenseNo(account.getMerBlis())
				.businessLicenseName(account.getMerBlisName())
				.openningBankCode(account.getOpenningBankCode())
				.openningBankName(account.getOpenningBankName())
				.certNo(account.getLarIdCard())
				.certName(account.getLarName())
				.ecContentParameters(JSONObject.toJSONString(ec))
				.orderNo(generatorService.generateLedgerOrderNo())
				.build();
		BaseResponse baseResponse = ledgerProvider.ecApply(EcApplyRequest.builder()
				.lakalaEcApplyRequest(applyRequest).build());

		if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
			EcApplyResponse response = BeanUtils.beanCovert(baseResponse.getContext(), EcApplyResponse.class);
			ecUrl = response.getResultUrl();
			account.setEcUrl(ecUrl);
			account.setEcApplyId(response.getEcApplyId().toString());
			return ecUrl;
		} else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000000, baseResponse.getMessage());
		}
	}

	/**
	 * 单个查询清分账户
	 * @author 许云鹏
	 */
	public LedgerAccount getOne(String id){
		return ledgerAccountRepository.findByIdAndDelFlag(id, DeleteFlag.NO).orElse(null);
	}

	/**
	 * 根据业务id查询账户
	 * @param businessId
	 * @return
	 */
	public LedgerAccountVO findByBusiness(String businessId, Boolean setFileFlag) {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		if (account == null) {
			return null;
		}
		LedgerAccountVO ledgerAccountVO = wrapperVo(account);
		if (Boolean.TRUE.equals(setFileFlag)) {
			List<String> fileIds = Lists.newArrayList(
					account.getBusinessPic(),
					account.getMerchantPic(),
					account.getShopinnerPic(),
					account.getBankCardPic(),
					account.getIdCardBackPic(),
					account.getIdCardFrontPic()
			).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
			Map<String, byte[]> fileContent = getFileContent(fileIds);
			ledgerAccountVO.setBusinessPicContent(fileContent.get(account.getBusinessPic()));
			ledgerAccountVO.setMerchantPicContent(fileContent.get(account.getMerchantPic()));
			ledgerAccountVO.setShopinnerPicContent(fileContent.get(account.getShopinnerPic()));
			ledgerAccountVO.setBankCardPicContent(fileContent.get(account.getBankCardPic()));
			ledgerAccountVO.setIdCardBackPicContent(fileContent.get(account.getIdCardBackPic()));
			ledgerAccountVO.setIdCardFrontPicContent(fileContent.get(account.getIdCardFrontPic()));
		}
		if (LedgerAccountType.MERCHANTS.toValue() == account.getAccountType()) {
			LedgerReceiverRel receiverRel = ledgerReceiverRelRepository
					.findBySupplierIdAndReceiverIdAndDelFlag(Long.parseLong(account.getBusinessId()),
							Constants.BOSS_DEFAULT_STORE_ID.toString(), DeleteFlag.NO);
			if (receiverRel != null) {
				ledgerAccountVO.setBossBindState(receiverRel.getBindState());
				ledgerAccountVO.setRelId(receiverRel.getId());
				ledgerAccountVO.setBindRejectReason(receiverRel.getRejectReason());
			}
			//查询mcc名称
			LedgerMccVO ledgerMccVO = ledgerMccQueryProvider.getById(LedgerMccByIdRequest.builder()
							.mccId(Long.valueOf(account.getMccCode())).build())
					.getContext().getLedgerMccVO();
			if (ledgerMccVO != null) {
				ledgerAccountVO.setMccCodeName(ledgerMccVO.getMccCate().concat("-").concat(ledgerMccVO.getSupplierCateName()));
			}
			//查询经营内容
			LedgerContentVO ledgerContentVO = ledgerContentQueryProvider
					.getById(LedgerContentByIdRequest.builder()
							.contentId(Long.valueOf(account.getMerBusiContent())).build())
					.getContext().getLedgerContentVO();
			if (ledgerContentVO != null ) {
				ledgerAccountVO.setMerBusiContentName(ledgerContentVO.getContentName());
			}
		}
		return ledgerAccountVO;
	}

	/**
	 * 查询账户图片
	 * @param businessId
	 * @return
	 */
	public LedgerAccountPicResponse getAccountPic(String businessId){
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		if (account == null) {
			return null;
		}
		LedgerAccountPicResponse response = new LedgerAccountPicResponse();
		List<String> fileIds = Lists.newArrayList(
				account.getBusinessPic(),
				account.getMerchantPic(),
				account.getShopinnerPic(),
				account.getBankCardPic(),
				account.getIdCardBackPic(),
				account.getIdCardFrontPic()
				).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
		Map<String, byte[]> fileContent = getFileContent(fileIds);
		response.setBusinessPicContent(fileContent.get(account.getBusinessPic()));
		response.setMerchantPicContent(fileContent.get(account.getMerchantPic()));
		response.setShopinnerPicContent(fileContent.get(account.getShopinnerPic()));
		response.setBankCardPicContent(fileContent.get(account.getBankCardPic()));
		response.setIdCardBackPicContent(fileContent.get(account.getIdCardBackPic()));
		response.setIdCardFrontPicContent(fileContent.get(account.getIdCardFrontPic()));
		return response;
	}
	/**
	 * 获取文件内容
	 * @param fileId
	 * @return
	 */
	public byte[] getFileContent(String fileId) {
		if (StringUtils.isNotBlank(fileId)) {
			LedgerFile file = ledgerFileRepository.findById(fileId).orElse(null);
			if (Objects.nonNull(file)) {
				return file.getContent();
			}
		}
		return new byte[0];
	}

	/**
	 * 批量获取文件内容
	 * @param fileIds
	 * @return
	 */
	public Map<String, byte[]> getFileContent(List<String> fileIds) {
		List<LedgerFile> files = ledgerFileRepository.findByIdIn(fileIds);
		return files.stream().collect(Collectors.toMap(LedgerFile::getId, LedgerFile::getContent));
	}

	/**
	 * 获取合同内容
	 * @param businessId
	 * @return
	 */
	public String getSupplierContract(String businessId) {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		if (account != null && StringUtils.isNotBlank(account.getEcContent())) {
			LedgerFile file = ledgerFileRepository.findById(account.getEcContent()).orElse(null);
			if (Objects.nonNull(file) && Constants.PDF.equalsIgnoreCase(file.getFileExt())) {
				return "data:application/pdf;base64," + Base64Utils.encodeToString(file.getContent());
			}
		}
		return null;
	}

	/**
	 * 修改合同文件
	 * @param file
	 */
	@Transactional
	public void modifyContractFile(LedgerFile file){
		ledgerFileRepository.save(file);
	}
	
	/**
	 * 分页查询清分账户
	 * @author 许云鹏
	 */
	public Page<LedgerAccount> page(LedgerAccountQueryRequest queryReq){
		return ledgerAccountRepository.findAll(
				LedgerAccountWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询清分账户
	 * @author 许云鹏
	 */
	public List<LedgerAccount> list(LedgerAccountQueryRequest queryReq){
		return ledgerAccountRepository.findAll(LedgerAccountWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public LedgerAccountVO wrapperVo(LedgerAccount ledgerAccount) {
		if (ledgerAccount != null){
			LedgerAccountVO ledgerAccountVO = KsBeanUtil.convert(ledgerAccount, LedgerAccountVO.class);
			return ledgerAccountVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(LedgerAccountQueryRequest queryReq) {
		return ledgerAccountRepository.count(LedgerAccountWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 校验boss账户是否已开通
	 */
	public void checkBossAccount() {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(Constants.BOSS_DEFAULT_STORE_ID.toString(), DeleteFlag.NO);
		if (LedgerAccountState.PASS.toValue() != account.getAccountState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010053);
		}
	}

	/**
	 * 修改账户信息
	 * @param modifyInfo
	 */
	@Transactional
	public void modifyAccountInfo(LedgerAccountModifyRequest modifyInfo) {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(modifyInfo.getBusinessId(), DeleteFlag.NO);
		KsBeanUtil.copyProperties(modifyInfo, account);
		ledgerAccountRepository.save(account);
	}

	/**
	 * 根据EcApplyId 更新ecContent
	 * @param ecContent
	 * @param businessId
	 */
	@Transactional
	public void updateEcContentByBusinessId(String ecContent,String ecNo,String businessId){
		ledgerAccountRepository.updateEcContentByBusinessId(ecContent,ecNo,businessId);
	}


	/**
	 * 根据contractId 更新Account
	 * @param accountState
	 * @param thirdMemNo
	 * @param merCupNo
	 * @param termNo
	 * @param contractId
	 */
	@Transactional
	public void updateAccountByContractId(Integer accountState, String thirdMemNo, String merCupNo, String termNo,
										  String ledgerApplyId, String contractId, String accountRejectReason,
										  LocalDateTime passDateTime, String bankTermNo, String unionTermNo, String quickTermNo){
		if (accountState == Constants.TWO) {
			ledgerAccountRepository.updateAccountByContractId(accountState,thirdMemNo,merCupNo,termNo,ledgerApplyId,
					contractId, passDateTime, bankTermNo, unionTermNo, quickTermNo);
		} else {
			ledgerAccountRepository.updateAccountByContractId(accountState,accountRejectReason,contractId);
		}

	}

	/**
	 * 根据进件id查询
	 * @param contractId
	 * @return
	 */
	public LedgerAccount findByContractId(String contractId) {
		return ledgerAccountRepository.findByContractId(contractId);
	}

	/**
	 *  根据ledgerApplyId 更新Account
	 * @param ledgerState
	 * @param businessId
	 */
	@Transactional
	public void updateAccountById(Integer ledgerState, String businessId, String ledgerRejectReason){
		ledgerAccountRepository.updateAccountById(ledgerState,businessId, ledgerRejectReason);
	}

	/**
	 * 根据分账受理id查询账户信息
	 * @param ledgerApplyId
	 * @return
	 */
	public LedgerAccount findByLedgerApplyId(String ledgerApplyId) {
		return ledgerAccountRepository.findByLedgerApplyId(ledgerApplyId);
	}


	/**
	 * 根据电子合同申请id查询清分账户
	 * @param ledgerApplyId
	 * @return
	 */
	public LedgerAccount findByEcApplyId(String ledgerApplyId) {
		return ledgerAccountRepository.findByEcApplyId(ledgerApplyId);
	}

	/**
	 * 检查分销员账户状态
	 * @param businessId
	 */
	public Integer checkDistributionAccountState(String businessId) {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		if (account == null) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010056);
		} else if (LedgerAccountState.CHECKING.toValue() == account.getAccountState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010057);
		} else if (LedgerAccountState.NOT_PASS.toValue() == account.getAccountState()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010061);
		}
		return account.getAccountState();
 	}

	 /**
	  * @description 根据业务id查询账户基本信息
	  * @author  edz
	  * @date: 2022/7/19 20:03
	  * @param businessId
	  * @return com.wanmi.sbc.customer.bean.vo.LedgerAccountVO
	  */
	public LedgerAccountVO findByBusiness(String businessId) {
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		return wrapperVo(account);
	}

	public Map<String, LedgerAccountVO> findByBusiness(List<String> businessIds) {
		List<LedgerAccount> accounts = ledgerAccountRepository.findByBusinessIdInAndDelFlag(businessIds, DeleteFlag.NO);
		List<LedgerAccountVO> accountVOS = KsBeanUtil.convert(accounts, LedgerAccountVO.class);
		if (accountVOS == null) return new HashMap<>();
		return accountVOS.stream().collect(Collectors.toMap(LedgerAccountVO::getBusinessId,
				Function.identity()));
	}

	/**
	 * 校验商户协议
	 * @param businessId
	 */
	@Transactional
	 public void acceptContract(String businessId){
		LedgerAccount account = ledgerAccountRepository.findByBusinessIdAndDelFlag(businessId, DeleteFlag.NO);
		if (account == null) {
		 throw new SbcRuntimeException(CustomerErrorCodeEnum.K010054);
		}
		//特约商户协议
		byte[] fileContent = getFileContent(account.getEcContent());
		if (ArrayUtils.isEmpty(fileContent)) {
		 throw new SbcRuntimeException(CustomerErrorCodeEnum.K010062);
		}

		//平台分账合作协议
		byte[] bindFile = getFileContent(account.getBindContractId());
		if(ArrayUtils.isEmpty(bindFile)) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010072);
		}
		account.setAccountState(LedgerAccountState.CHECKING.toValue());
		ledgerAccountRepository.save(account);
	 }

	/**
	 * 回调补偿查询异常数据，作补偿用
	 */
	public LedgerCallbackVO findForCallback() {
		List<String> ecApplyIdList = ledgerAccountRepository.findEcApplyIdListForCallBack();
		List<String> ec003ApplyIdList = ledgerAccountRepository.findEc003ApplyIdListForCallBack();
		List<String> contractIdList = ledgerAccountRepository.findContractIdListForCallBack();
		List<String> merInnerNoList = ledgerAccountRepository.findMerInnerNoListForCallBack();
		List<String> bindApplyIdList= ledgerReceiverRelRepository.findApplyIdForCallBack();
		return LedgerCallbackVO.builder()
				.ecApplyIdList(ecApplyIdList)
				.ec003ApplyIdList(ec003ApplyIdList)
				.contractIdList(contractIdList)
				.merInnerNoList(merInnerNoList)
				.bindApplyIdList(bindApplyIdList)
				.build();
	}

	/**
	 * 申请账户后，修改信息
	 * @param request
	 * @param account
	 */
	@Transactional
	public void updateForCreateAccount(LedgerAccountModifyRequest request, LedgerAccountVO account){
		this.modifyAccountInfo(request);
		//如果是补偿触发，则修改记录状态
		ledgerErrorRecordService.modifyState(account.getBusinessId(), LedgerFunctionType.CREATE_ACCOUNT, LedgerErrorState.SUCCESS);
		//供应商更新店铺状态
		if (LedgerAccountType.RECEIVER.toValue() == account.getAccountType()
				&& LedgerReceiverType.PROVIDER.toValue() == account.getReceiverType()) {
			storeService.updateLaKaLaState(Long.valueOf(account.getBusinessId()));
		}
	}

	/**
	 * @description 商家进件记录
	 * @author  edz
	 * @date: 2022/9/7 16:57
	 * @param request
	 * @return com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.customer.bean.vo.SupplierApplyRecordVO>
	 */
	public MicroServicePage<SupplierApplyRecordVO> supplierApplyRecordPage(SupplierApplyRecordRequest request){

		String queryCol = "select a.company_code as companyCode, a.supplier_name as supplierName, b.account_name as " +
				"accountName, if(c.id is null, 0, c.account_state) as state, account_reject_reason as accountRejectReason," +
				"ledger_state as ledgerState, ledger_reject_reason as ledgerRejectReason, business_id as supplierId," +
				"mer_cup_no as merCupNo," +
				" ifnull(date_format(c.pass_time, '%Y-%m-%d %H:%i:%S'), '-') as passTime ";
		String CountCol = "select count(a.company_info_id) ";
		String form = " from company_info a left join employee b on a.company_info_id = b" +
				".company_info_id left join ledger_account c on concat(a.company_info_id, '') = c.business_id and c.del_flag = 0 " +
				" where a.del_flag = 0 " +
				"  and b.is_master_account = 1 " +
				"  and b.del_flag = 0 " +
				"  and b.account_state = 0 ";
		String orderBy = " order by c.pass_time desc";
		StringBuilder querySql = new StringBuilder(queryCol);
		StringBuilder CountSql = new StringBuilder(CountCol);
		querySql.append(form);
		CountSql.append(form);
		if (StoreType.SUPPLIER.equals(request.getStoreType())){
			querySql.append(" and a.store_type = 1");
			CountSql.append(" and a.store_type = 1");
		} else if (StoreType.PROVIDER.equals(request.getStoreType())){
			querySql.append(" and a.store_type = 0");
			CountSql.append(" and a.store_type = 0");
		}
		if (StringUtils.isNotBlank(request.getSupplierName())){
			querySql.append(" and a.supplier_name like ").append("'%").append(request.getSupplierName()).append("%'");
			CountSql.append(" and a.supplier_name like ").append("'%").append(request.getSupplierName()).append("%'");
		}
		if (StringUtils.isNotBlank(request.getCompanyCode())){
			querySql.append(" and a.company_code like ").append("'%").append(request.getCompanyCode()).append("%'");
			CountSql.append(" and a.company_code like ").append("'%").append(request.getCompanyCode()).append("%'");
		}
		if (Objects.nonNull(request.getApplyState())){
			querySql.append(" and if(c.id is null, 0, c.account_state) = ").append(request.getApplyState().toValue());
			CountSql.append(" and if(c.id is null, 0, c.account_state) = ").append(request.getApplyState().toValue());
		}
		querySql.append(orderBy);
		Query query = entityManager.createNativeQuery(querySql.toString());
		int pageNumber = request.getPageNum();
		int pageSize = request.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(NativeQuery.class)
				.addScalar("companyCode", StandardBasicTypes.STRING)
				.addScalar("supplierName", StandardBasicTypes.STRING)
				.addScalar("accountName", StandardBasicTypes.STRING)
				.addScalar("state", StandardBasicTypes.INTEGER)
				.addScalar("passTime", StandardBasicTypes.STRING)
				.addScalar("supplierId", StandardBasicTypes.LONG)
				.addScalar("accountRejectReason", StandardBasicTypes.STRING)
				.addScalar("ledgerState", StandardBasicTypes.INTEGER)
				.addScalar("ledgerRejectReason", StandardBasicTypes.STRING)
				.addScalar("merCupNo", StandardBasicTypes.STRING)
				.setResultTransformer(Transformers.aliasToBean(SupplierApplyRecordVO.class));
		List <SupplierApplyRecordVO> fooList = query.getResultList();
		//补充分账绑定状态
		if(CollectionUtils.isNotEmpty(fooList)){
			List<Long> longs = fooList.stream().map(SupplierApplyRecordVO::getSupplierId).collect(Collectors.toList());
			List<LedgerReceiverRel> ledgerReceiverRels = ledgerReceiverRelRepository.findBySupplierIdInAndReceiverIdAndDelFlag(longs, Constants.BOSS_DEFAULT_STORE_ID.toString(), DeleteFlag.NO);
			if(CollectionUtils.isNotEmpty(ledgerReceiverRels)){
				Map<Long, List<LedgerReceiverRel>> map = ledgerReceiverRels.stream().collect(Collectors.groupingBy(LedgerReceiverRel::getSupplierId));
				fooList.forEach(supplierApplyRecordVO -> {
					List<LedgerReceiverRel> list = map.get(supplierApplyRecordVO.getSupplierId());
					if(CollectionUtils.isNotEmpty(list)){
						supplierApplyRecordVO.setReceiverState(list.get(0).getBindState());
						supplierApplyRecordVO.setReceiverRejectReason(list.get(0).getRejectReason());
					}
				});
			}
		}
		Query queryTotal = entityManager.createNativeQuery(CountSql.toString());
		Long countResult = Long.parseLong(queryTotal.getSingleResult().toString()) ;
		return new MicroServicePage<>(fooList, request.getPageable(),countResult);
	}

	/**
	 * @description 分销员进件记录
	 * @author  edz
	 * @date: 2022/9/7 17:28
	 * @param request
	 * @return com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.customer.bean.vo.DistributionApplyRecordVO>
	 */
	public MicroServicePage<DistributionApplyRecordVO> distributionApplyRecordPage(DistributionApplyRecordRequest request){

		String queryCol = "select c.customer_account as customerAccount, a.customer_name as customerName, d" +
				".distributor_level_name as " +
				"distributorLevelName, if(b.id is null, 0, b.account_state) as state, " +
				" ifnull(date_format(b.pass_time, '%Y-%m-%d %H:%i:%S'), '-') as passTime ";
		String CountCol = "select count(a.customer_id) ";
		String form = " from distribution_customer a left join `sbc-customer`.ledger_account b on a.customer_id = b" +
				".business_id and b.del_flag = 0 left join `sbc-customer`.customer c on a.customer_id = c.customer_id " +
				"left join `sbc-customer`.distributor_level d on a.distributor_level_id = d.distributor_level_id " +
				"where a.del_flag = 0 and a.forbidden_flag = 0 and a.distributor_flag = 1 and c.del_flag = 0 and c.check_state = 1" +
				" and c.log_out_status = 0";
		String orderBy = " order by b.pass_time desc";
		StringBuilder querySql = new StringBuilder(queryCol);
		StringBuilder CountSql = new StringBuilder(CountCol);
		querySql.append(form);
		CountSql.append(form);

		if (StringUtils.isNotBlank(request.getDistributionAccount())){
			querySql.append(" and c.customer_account like ").append("'%").append(request.getDistributionAccount()).append("%'");
			CountSql.append(" and c.customer_account like ").append("'%").append(request.getDistributionAccount()).append("%'");
		}
		if (StringUtils.isNotBlank(request.getDistributionLevelId())){
			querySql.append(" and a.distributor_level_id = '").append(request.getDistributionLevelId()).append("'");
			CountSql.append(" and a.distributor_level_id = '").append(request.getDistributionLevelId()).append("'");
		}
		if (Objects.nonNull(request.getApplyState())){
			querySql.append(" and if(b.id is null, 0, b.account_state)  = ").append(request.getApplyState().toValue());
			CountSql.append(" and if(b.id is null, 0, b.account_state)  = ").append(request.getApplyState().toValue());
		}

		if (StringUtils.isNotBlank(request.getDistributionName())){
			querySql.append(" and a.customer_name like ").append("'%").append(request.getDistributionName()).append("%'");
			CountSql.append(" and a.customer_name like ").append("'%").append(request.getDistributionName()).append("%'");
		}
		querySql.append(orderBy);
		Query query = entityManager.createNativeQuery(querySql.toString());
		int pageNumber = request.getPageNum();
		int pageSize = request.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(NativeQuery.class)
				.addScalar("customerAccount", StandardBasicTypes.STRING)
				.addScalar("customerName", StandardBasicTypes.STRING)
				.addScalar("distributorLevelName", StandardBasicTypes.STRING)
				.addScalar("state", StandardBasicTypes.INTEGER)
				.addScalar("passTime", StandardBasicTypes.STRING)
				.setResultTransformer(Transformers.aliasToBean(DistributionApplyRecordVO.class));
		List <DistributionApplyRecordVO> fooList = query.getResultList();
		Query queryTotal = entityManager.createNativeQuery(CountSql.toString());
		Long countResult = Long.parseLong(queryTotal.getSingleResult().toString());
		return new MicroServicePage<>(fooList, request.getPageable(),countResult);
	}

	/**
	 * 分销员分账绑定分页列表
	 * @param request
	 * @return
	 */
	public MicroServicePage<LedgerRelMobileVO> pageMobile(LedgerReceiverRelPageMobileRequest request) {
		if (StringUtils.isBlank(request.getCustomerId())) {
			throw new RuntimeException(CommonErrorCodeEnum.K000009.getCode());
		}
		String queryCol = "select s.company_info_id supplierId, s.store_logo storeLogo, " +
				"s.store_name storeName, s.company_type companyType, r.check_state checkState, " +
				"r.bind_state bindState, r.reject_reason rejectReason";
		String CountCol = "select count(l.business_id)";
        String form =
                " from ledger_account l"
                        + " JOIN store s on l.business_id = s.company_info_id"
                        + " LEFT JOIN (select * from ledger_receiver_rel where receiver_type = 2"
						+ " and del_flag = 0 and receiver_id = '" + request.getCustomerId() + "') r"
                        + " on l.business_id = r.supplier_id"
                        + " where l.account_type = 0 and l.del_flag = 0 and l.account_state = 2 and l.ledger_state = 2 and s.lakala_state = 1";
		String orderBy = " order by l.create_time desc";
		StringBuilder querySql = new StringBuilder(queryCol);
		StringBuilder CountSql = new StringBuilder(CountCol);
		querySql.append(form);
		CountSql.append(form);

		if (StringUtils.isNotBlank(request.getStoreName())) {
			querySql.append(" and s.store_name like ").append("'%").append(request.getStoreName()).append("%'");
			CountSql.append(" and s.store_name like ").append("'%").append(request.getCustomerId()).append("%'");
		}

		if (request.getStatus() != null) {
			switch (request.getStatus()) {
				case 1:
					querySql.append(" and check_state = 0");
					CountSql.append(" and check_state = 0");
					break;
				case 2:
					querySql.append(" and check_state = 2");
					CountSql.append(" and check_state = 2");
					break;
				case 3:
					querySql.append(" and check_state = 1 and bind_state = 1");
					CountSql.append(" and check_state = 1 and bind_state = 1");
					break;
				case 4:
					querySql.append(" and check_state = 1 and bind_state = 2");
					CountSql.append(" and check_state = 1 and bind_state = 2");
					break;
				case 5:
					querySql.append(" and check_state = 1 and bind_state = 3");
					CountSql.append(" and check_state = 1 and bind_state = 3");
					break;
				default:
			}
		}

		querySql.append(orderBy);
		Query query = entityManager.createNativeQuery(querySql.toString());
		int pageNumber = request.getPageNum();
		int pageSize = request.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(NativeQuery.class)
				.addScalar("supplierId", StandardBasicTypes.LONG)
				.addScalar("storeLogo", StandardBasicTypes.STRING)
				.addScalar("storeName", StandardBasicTypes.STRING)
				.addScalar("companyType", StandardBasicTypes.INTEGER)
				.addScalar("checkState", StandardBasicTypes.INTEGER)
				.addScalar("bindState", StandardBasicTypes.INTEGER)
				.addScalar("rejectReason", StandardBasicTypes.STRING)
				.setResultTransformer(Transformers.aliasToBean(LedgerRelMobileVO.class));
		List <LedgerRelMobileVO> fooList = query.getResultList();
		Query queryTotal = entityManager.createNativeQuery(CountSql.toString());
		Long countResult = Long.parseLong(queryTotal.getSingleResult().toString());
		return new MicroServicePage<>(fooList, request.getPageable(),countResult);
	}

	@Transactional
	public void updateB2bAddStateById(Integer b2bAddState, String businessId, String b2bAddApplyId){
		ledgerAccountRepository.updateB2bAddStateById(b2bAddState,businessId, b2bAddApplyId);
	}

	/**
	 * 根据b2bAddApplyId查询账户
	 * @param b2bAddApplyId
	 * @return
	 */
	public LedgerAccount findByB2bAddApplyId(String b2bAddApplyId) {
		return ledgerAccountRepository.findByB2bAddApplyId(b2bAddApplyId);
	}
}

