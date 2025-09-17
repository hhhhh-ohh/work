package com.wanmi.sbc.empower.apppush.service.umeng;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.empower.apppush.service.umeng.bean.*;
import com.wanmi.sbc.empower.bean.constant.AppPushErrorCode;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class UmengPushClient {
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
//	protected CloseableHttpClient client = new DefaultHttpClient();
	protected CloseableHttpClient client = HttpClientBuilder.create().build();

	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// device_token上传接口
	protected static final String uploadPath = "/upload";
	
	// push发送接口
	protected static final String postPath = "/api/send";

	// 发送状态查询
	protected static final String queryPath = "/api/status";

	// 任务撤销接口
	protected static final String cancelPath = "/api/cancel";


	/**
	 * 友盟安卓端推送接口
	 * @param androidAppKey
	 * @param androidAppMasterSecret
	 * @param pushEntry
	 * @param fileId
	 * @return
	 */
	public PushResultEntry sendAndroidFilecast(String androidAppKey, String androidAppMasterSecret,
												PushEntry pushEntry, String fileId) {
		if (StringUtils.isBlank(fileId)){
			return null;
		}

		log.debug("sendAndroidFilecast.androidAppKey:{}" ,androidAppKey);
		log.debug("sendAndroidFilecast.androidAppMasterSecret:{}" ,androidAppMasterSecret);
		try {
			AndroidFilecast filecast = new AndroidFilecast(androidAppKey, androidAppMasterSecret);
			filecast.setFileId(fileId);
			// 消息类型
			filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			// 通知栏文字
			filecast.setTicker(pushEntry.getTicker());
			// 通知标题
			filecast.setTitle(pushEntry.getTitle());
			// 通知文字描述
			filecast.setText(pushEntry.getText());
			// http/https图片链接
			filecast.setImg(pushEntry.getImage());
			// 路由地址
			//			filecast.goCustomAfterOpen("");
			filecast.goCustomAfterOpen(pushEntry.getRouter());
			// out_biz_no 不知道是否有效。待测试 https://developer.umeng.com/docs/66632/detail/68343
			filecast.setOutBizNo(pushEntry.getOutBizNo());
			filecast.setMipush(Boolean.TRUE);
			filecast.setMiactivity("com.wanmi.s2bstore.umeng.PushActivity");
			// 发送消息描述，建议填写。
			filecast.setDescription("ANDROID消息推送");
			// 定时发送
			if (Objects.nonNull(pushEntry.getSendTime())){
				filecast.setStartTime(pushEntry.getSendTime().format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_1)));
			}
			return this.send(filecast);
		} catch (Exception e) {
			log.error("UmengPushClient.sendAndroidFilecast_参数组成异常", e);
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060019);
		}
	}

	/**
	 * 友盟IOS端推送接口
	 * @param iosAppKey
	 * @param iosAppMasterSecret
	 * @param pushEntry
	 * @param fileId
	 * @return
	 */
	public PushResultEntry sendIOSFilecast(String iosAppKey, String iosAppMasterSecret, PushEntry pushEntry,
											String fileId) {
		if (StringUtils.isBlank(fileId)){
			return null;
		}

		log.debug("sendAndroidFilecast.iosAppKey:{}" ,iosAppKey);
		log.debug("sendAndroidFilecast.iosAppMasterSecret:{}" ,iosAppMasterSecret);
		try {
			IOSFilecast filecast = new IOSFilecast(iosAppKey, iosAppMasterSecret);
			filecast.setFileId(fileId);
			JSONObject alertJson = new JSONObject();
			alertJson.put("title", pushEntry.getTitle());
			alertJson.put("body", pushEntry.getText());
			filecast.setAlert(alertJson);
			filecast.setBadge(0);
			filecast.setSound("default");
			filecast.setOutBizNo(pushEntry.getOutBizNo());
			// 发送消息描述，建议填写。
			filecast.setDescription("IOS消息推送");
			// 自定义参数
			filecast.setCustomizedField("router", pushEntry.getRouter());
			// 定时发送
			if (Objects.nonNull(pushEntry.getSendTime())) {
				filecast.setStartTime(pushEntry.getSendTime().format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_1)));
			}
			return this.send(filecast);
		} catch (Exception e) {
			log.error("UmengPushClient.sendAndroidFilecast_参数组成异常", e);
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060019);
		}
	}


	public PushResultEntry send(UmengNotification msg) {
		log.info("PushClient.send_调用友盟推送接口");
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		try {
			msg.setPredefinedKeyValue("timestamp", timestamp);
		} catch (Exception e) {
			log.error("PushClient.send_参数组成异常", e);
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060019);
		}
		String url = host + postPath;
        String postBody = msg.getPostBody();
		JSONObject json = this.execute(url, postBody, msg.getAppMasterSecret());
		String ret = json.getString("ret");
		PushResultEntry resultEntry = JSONObject.parseObject(json.getString("data"), PushResultEntry.class) ;
		resultEntry.setRet(ret);
		resultEntry.setSuccess("SUCCESS".equals(ret));
		return resultEntry;
    }

    private JSONObject execute(String url, String postBody, String masterSecret) {
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + masterSecret).getBytes(UTF_8));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, UTF_8);
		post.setEntity(se);
		// Send the post request and get the response
		log.info("PushClient.execute_url:{}", url);
		log.info("PushClient.execute_postBody:{}", postBody);
		try (CloseableHttpResponse response = client.execute(post)) {
			int status = response.getStatusLine().getStatusCode();
			if (status == Constants.NUM_200) {
				log.debug("PushClient.execute_请求成功");
				String strResult = EntityUtils.toString(response.getEntity(), UTF_8);
				log.info("PushClient.execute_成功返回json::{}", strResult);
				return JSONObject.parseObject(strResult);
			} else {
				log.error("PushClient.execute_请求失败异常,code::{}", status);
				log.error("PushClient.execute_请求失败异常,entry::{}", EntityUtils.toString(response.getEntity(), UTF_8));
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060016);
			}
		} catch (IOException e) {
			log.error("PushClient.execute_请求IO异常", e);
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060017);
		}
	}

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey, String appMasterSecret, List<String> tokenList) {
		if (CollectionUtils.isEmpty(tokenList)){
			return null;
		}
		log.debug("PushClient.uploadContents_调用友盟文件上传接口");
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", String.join("\n", tokenList));
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		JSONObject json = this.execute(url, postBody, appMasterSecret);
		String ret = json.getString("ret");
		JSONObject data = json.getJSONObject("data");
		if (!ret.equals(Constants.SUCCESS)) {
			log.error("PushClient.uploadContents_友盟文件上传失败");
			log.error("PushClient.uploadContents_友盟文件上传失败原因：{}", data.toString());
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060018);
		}
		return data.getString("file_id");
	}

	public QueryResultEntry query(QueryEntry queryEntry) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("appkey", queryEntry.getKey());
		jsonObject.put("task_id", queryEntry.getTaskId());
		jsonObject.put("timestamp",Integer.toString((int)(System.currentTimeMillis() / 1000)));
		String url = host + queryPath;
		String postBody = jsonObject.toString();

		JSONObject json = this.execute(url, postBody, queryEntry.getAppMasterSecret());

		String ret = json.getString("ret");
		QueryResultEntry resultEntry = JSONObject.parseObject(json.getString("data"), QueryResultEntry.class) ;
		resultEntry.setRet(ret);
		resultEntry.setSuccess("SUCCESS".equals(ret));
		return resultEntry;
	}

	public QueryResultEntry cancel(QueryEntry queryEntry) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("appkey", queryEntry.getKey());
		jsonObject.put("task_id", queryEntry.getTaskId());
		jsonObject.put("timestamp",Integer.toString((int)(System.currentTimeMillis() / 1000)));
		String url = host + cancelPath;
		String postBody = jsonObject.toString();

		JSONObject json = this.execute(url, postBody, queryEntry.getAppMasterSecret());

		String ret = json.getString("ret");
		QueryResultEntry resultEntry = JSONObject.parseObject(json.getString("data"), QueryResultEntry.class) ;
		resultEntry.setRet(ret);
		resultEntry.setSuccess("SUCCESS".equals(ret));
		return resultEntry;
	}

}
