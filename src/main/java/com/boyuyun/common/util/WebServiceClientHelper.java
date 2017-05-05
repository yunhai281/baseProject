/**
 * @ProjectName boyuyun
 * @FileName WebServiceClientHelper.java
 * @PackageName com.boyuyun.common.webservice
 * @Date 2015-12-15 上午10:12:23
 * Copyright (c) 2015,boyuyun All Rights Reserved.
 *
*/  

package com.boyuyun.common.util;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.util.Base64;
import org.junit.Test;

import com.boyuyun.common.util.webservice.client.callback.ClientPasswordHandler;

/**
 * 调用外部WebService接口工具类
 * @author XHL
 * @date 2016-7-7 上午9:23:28
 */
public class WebServiceClientHelper {
	static int socketTimeout = 30000;// 请求超时时间  
    static int connectTimeout = 30000;// 传输超时时间 
	/**
	 * 不依赖服务器端接口来完成调用
	 * @param wsUrl 调用远程的webservice并返回数据,带?wsdl 
	 * @param method 调用的ws方法名
	 * @param arg 传入接口方法的参数，无参不能为null,应为new String[]{}
	 * @return
	 * @author XHL
	 * @date 2016-7-7 上午9:21:07
	 */
	public static Object callService(String wsUrl, String method, Object... arg) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Object[] res = null;
		try{
			Client client = dcf.createClient(wsUrl);
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();
			policy.setAllowChunking(false); // 取消块编码，解决webservice上传失败问题
			policy.setConnectionTimeout(10000); // 连接超时时间
			policy.setReceiveTimeout(30000);// 请求超时时间
			conduit.setClient(policy);
			
			res = client.invoke(method, arg);
			System.out.println("来源:"+wsUrl+"  ;res:"+res[0].toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
		if(res==null||res.length==0){
			return null;
		}
		return res[0];
	}
	
	public static Object sslCallService(String wsUrl, String method, Object... arg) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsUrl);
		Endpoint cxfEndpoint = client.getEndpoint();
		
		Map<String,Object> outProps = new HashMap<String,Object>();
		
		outProps.put(WSHandlerConstants.ACTION, "Signature Encrypt");
		outProps.put(WSHandlerConstants.USER, "clientprivatekey");
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "Client_Sign.properties");
		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "serverpublickey");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE, "Client_Encrypt.properties");
		// Callback used to retrieve password for given user.
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordHandler.class.getName());
		LoggingOutInterceptor logOut = new LoggingOutInterceptor();
		LoggingInInterceptor logIn = new LoggingInInterceptor();
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		
		cxfEndpoint.getOutInterceptors().add(wssOut);
		cxfEndpoint.getOutInterceptors().add(logOut);
		cxfEndpoint.getInInterceptors().add(logIn);
		
		Object[] res = null;
		try {
			res = client.invoke(method, arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res==null||res.length==0){
			return null;
		}
		return res[0];
	}
	
	public static Object userNameTokenCallService(String wsUrl, String method, Object... arg) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsUrl);
		Endpoint cxfEndpoint = client.getEndpoint();
		
		Map<String,Object> outProps = new HashMap<String,Object>();
		
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN+" "+WSHandlerConstants.TIMESTAMP);
		//添加用户名
		outProps.put(WSHandlerConstants.USER, "oa");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		//密码通过CallbackHandler接口实现类得到
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordHandler.class.getName());

		LoggingOutInterceptor logOut = new LoggingOutInterceptor();
		LoggingInInterceptor logIn = new LoggingInInterceptor();
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		
		cxfEndpoint.getOutInterceptors().add(wssOut);
		cxfEndpoint.getOutInterceptors().add(logOut);
		cxfEndpoint.getInInterceptors().add(logIn);
		
		Object[] res = null;
		try {
			res = client.invoke(method, arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res==null||res.length==0){
			return null;
		}
		return res[0];
	}
	
	public String commonCallService(String userName,String password,String wsdl,String namespace,String methodName,LinkedHashMap<String, Object> args){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\"><wsu:Timestamp wsu:Id=\"TS-${tId}\"><wsu:Created>${created}</wsu:Created><wsu:Expires>${expires}</wsu:Expires></wsu:Timestamp><wsse:UsernameToken wsu:Id=\"UsernameToken-${uId}\"><wsse:Username>${userName}</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">${password}</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">${nonce}</wsse:Nonce><wsu:Created>${created}</wsu:Created></wsse:UsernameToken></wsse:Security></SOAP-ENV:Header><soap:Body><ns2:${methodName} xmlns:ns2=\"${namespace}\"> ${args}</ns2:${methodName}></soap:Body></soap:Envelope>";
		String result = null;
		
		String created = "";
		String expires = "";
		String tId = UUID.randomUUID().toString().replace("-", "");
		String uId = UUID.randomUUID().toString().replace("-", "");
		String nonce = null;
		String passwordDigest = null;
		
		Date createdDate = new Date();
		DateFormat zulu = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        zulu.setTimeZone(TimeZone.getTimeZone("UTC"));
		created = zulu.format(createdDate);
		Date expiresDate = new Date(createdDate.getTime() + ((long)300 * 1000L));
		expires = zulu.format(expiresDate);
		byte[]nonceByte = new byte[16];
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(nonceByte);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		nonce = Base64.encode(nonceByte);
		passwordDigest = generatePasswordDigest(nonce,created,password);
		xml = xml.replace("${tId}", tId).replace("${uId}", uId).replace("${created}", created)
				.replace("${expires}", expires).replace("${userName}", userName)
				.replace("${password}", passwordDigest).replace("${nonce}", nonce)
				.replace("${methodName}", methodName).replace("${namespace}", namespace);
		StringBuffer argsXml = new StringBuffer("");
		if(args!=null && !args.isEmpty()){
			for(Entry<String, Object> entry:args.entrySet()){
				argsXml.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
			}
		}
		xml = xml.replace("${args}", argsXml.toString());
		//http请求发送xml
        result = httpPost(wsdl, xml);
		return result;
	}
	
	private String httpPost(String wsdl, String xml) {
		String result = null;
		// 创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
        HttpPost httpPost = new HttpPost(wsdl);
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom() .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();  
        httpPost.setConfig(requestConfig); 
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
            httpPost.setHeader("SOAPAction", "");
            StringEntity data = new StringEntity(xml,Charset.forName("UTF-8")); 
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();  
            if (httpEntity != null) {  
                // 打印响应内容 
            	result = EntityUtils.toString(httpEntity, "UTF-8");  
            }  
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(closeableHttpClient!=null){
        		try {
					closeableHttpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
		return result;
	}
	
	private String generatePasswordDigest(String nonce, String created, String password) {
		String passwdDigest = null;
        try {
            byte[] b1 = nonce != null ? Base64.decode(nonce) : new byte[0];
            byte[] b2 = created != null ? created.getBytes("UTF-8") : new byte[0];
            byte[] b3 = password.getBytes("UTF-8");
            byte[] b4 = new byte[b1.length + b2.length + b3.length];
            int offset = 0;
            System.arraycopy(b1, 0, b4, offset, b1.length);
            offset += b1.length;
            
            System.arraycopy(b2, 0, b4, offset, b2.length);
            offset += b2.length;

            System.arraycopy(b3, 0, b4, offset, b3.length);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bt = digest.digest(b4);
            passwdDigest = Base64.encode(bt);
        }catch (Exception e) {
        	e.printStackTrace();
		}
		return passwdDigest;
	}

	/**
	 * cxf客户端调用服务端方法测试
	 */
	public static void main(String[] args) {
		
		//System.setProperty("javax.net.ssl.trustStore", "E:\\server\\keystore\\publickey.keystore");        
		//System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		String res = callService("http://dzdd.boyuyun.com.cn/ws/syncService?wsdl","addSchool",new Object[]{"1", "1", "1", "", "1","1" ,"1" }).toString();
		System.out.println(res);
	}
	
	
	@Test
	public void testUserNameToken(){
		String res = userNameTokenCallService("http://localhost/services/userNameTokenWebService?wsdl","sayHello",new Object[]{"Jim"}).toString();
		System.out.println(res);
	}
	
	@Test
	public void testSms(){
		Object res = callService("http://localhost/services/schoolAdminWebService?wsdl", "getAllSchoolAdmin", new Object[]{});
		System.out.println(res);
	}
	
	@Test
	public void testCall()throws Exception{
		String userName = "oa";
		String password = "123456";
		String wsdl = "http://localhost/services/userNameTokenWebService";
		String namespace = "http://demo.webservice.boyuyun.com/";
		String methodName = "sayHello";
		LinkedHashMap<String, Object> args = new LinkedHashMap<String, Object>();
		args.put("name", "Jim");
		String result = commonCallService(userName, password, wsdl, namespace, methodName, args);
		System.out.println(result);
	}
}
