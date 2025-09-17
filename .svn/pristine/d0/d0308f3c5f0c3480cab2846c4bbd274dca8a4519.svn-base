package com.wanmi.sbc.empower.logisticssetting.service.kuaidi100;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {

	public static String postData(String url, Map<String, String> params, String codePage) throws Exception {

		final HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(25 * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30 * 1000);

		final PostMethod method = new PostMethod(url);
		if (params != null) {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, codePage);
			method.setRequestBody(assembleRequestParams(params));
		}
		String result;
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), codePage);
		} catch (final Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	/**
	 * 组装http请求参数
	 *
	 * @param data
	 * @return
	 */
	private static NameValuePair[] assembleRequestParams(Map<String, String> data) {
		final List<NameValuePair> nameValueList = new ArrayList<>();

		for (Map.Entry<String, String> entry : data.entrySet()) {
			nameValueList.add(new NameValuePair(entry.getKey(), entry.getValue()));
		}

		return nameValueList.toArray(new NameValuePair[0]);
	}

}
