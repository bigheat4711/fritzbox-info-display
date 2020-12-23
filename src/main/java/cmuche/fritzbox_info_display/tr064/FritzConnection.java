/**
 * *********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2015 Marin Pollmann <pollmann.m@gmail.com>
 *
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 **********************************************************************************************************************
 */
package cmuche.fritzbox_info_display.tr064;

import cmuche.fritzbox_info_display.beans.DeviceType;
import cmuche.fritzbox_info_display.beans.RootType;
import cmuche.fritzbox_info_display.beans.ServiceType;
import cmuche.fritzbox_info_display.model.Credentials;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class FritzConnection {
	private static final int DEFAULT_HTTP_PORT = 49000;
	private static final int DEFAULT_HTTPS_PORT = 49000;
	private static final String FRITZ_IGD_DESC_FILE = "igddesc.xml";
	private static final String FRITZ_TR64_DESC_FILE = "tr64desc.xml";

	private final Map<String, Service> services;
	private String user = null;
	private String pwd = null;
	private final String targetHost;
	private final RestTemplate restTemplate;
	//private final HttpClientContext context;

	private String name;

	public FritzConnection(Credentials credentials) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy trustStrategy = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, trustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", socketFactory)
				.register("http", new PlainConnectionSocketFactory())
				.build();

		BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(registry);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(socketFactory)
				.setDefaultCredentialsProvider(provider())
				.setConnectionManager(connectionManager)
				.build();

		//https://www.baeldung.com/httpclient-ssl
		//der obige code geht auch kürzer. allerdings ist die frage, ob die trustStragegy -> true gebraucht wird, weil das fritz.box zertifikat nicht offiziell ist.

				//nächstes Problem: der obige code macht kein digest
				//das ist hier erklärt:
				//https://www.baeldung.com/resttemplate-digest-authentication
				//allerdings ist hier wiederum keine spur von trustStrategy und glaub noch irgendwas ....



		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		this.restTemplate = new RestTemplate(requestFactory);

		targetHost = credentials.getUrl() + ":" + DEFAULT_HTTP_PORT;
		this.user = credentials.getUsername();
		this.pwd = credentials.getPassword();

		//this.httpClient = HttpClients.createDefault();
		//context = HttpClientContext.create();
		services = new HashMap<>();
	}

	private CredentialsProvider provider() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pwd));
		return provider;
	}

	private void createHttpContext(){
		AuthCache authCache = new BasicAuthCache();
		DigestScheme digestScheme = new DigestScheme();
		digestScheme.overrideParamter("realm", "F!Box SOAP-Auth");
		digestScheme.overrideParamter("nonce", Long.toString(new Random().nextLong(), 36));
		digestScheme.overrideParamter("qop", "auth");
		digestScheme.overrideParamter("nc", "0");
		digestScheme.overrideParamter("cnonce", DigestScheme.createCnonce());
		authCache.put(targetHost, digestScheme);
		//context.setCredentialsProvider(provider);
		//context.setAuthCache(authCache);
	}

	public void init() throws IOException, JAXBException {
		if (user != null && pwd != null) {
			readTR64();
		} else {
			readIGDDESC();
		}

	}

	private void readTR64() throws IOException, JAXBException {
		InputStream xml = getXMLIS("/" + FRITZ_TR64_DESC_FILE);
		JAXBContext jaxbContext = JAXBContext.newInstance(RootType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RootType root = (RootType) jaxbUnmarshaller.unmarshal(xml);
		DeviceType device = root.getDevice();
		name = device.getFriendlyName();
		getServicesFromDevice(device);
	}

	private void readIGDDESC() throws IOException, JAXBException {
		InputStream xml = namespaceHack(getXMLIS("/" + FRITZ_IGD_DESC_FILE));
		JAXBContext jaxbContext = JAXBContext.newInstance(RootType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RootType root = (RootType) jaxbUnmarshaller.unmarshal(xml);
		DeviceType device = root.getDevice();
		name = device.getFriendlyName();
		getServicesFromDevice(device);
	}

	public static ByteArrayInputStream namespaceHack(InputStream xml) throws IOException {
		String xmlContent = new String(xml.readAllBytes());
		xmlContent = xmlContent.replaceAll("urn:schemas-upnp-org:", "urn:dslforum-org:");
		return new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
	}

	private void getServicesFromDevice(DeviceType device) throws IOException, JAXBException {
		for (ServiceType sT : device.getServiceList().getService()) {
			String[] tmp = sT.getServiceType().split(":");
			String key = tmp[tmp.length - 2] + ":" + tmp[tmp.length - 1];

			services.put(key, new Service(sT, this));
		}
		if (device.getDeviceList() != null) {
			for (DeviceType d : device.getDeviceList().getDevice()) {
				getServicesFromDevice(d);
			}
		}
	}

	private InputStream httpRequest(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
		CloseableHttpResponse response = null;
		byte[] content = null;
		try {
			response = httpClient.execute(target, request, context);
			restTemplate.exchange(target, )
			content = EntityUtils.toByteArray(response.getEntity());
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new IOException(response.getStatusLine().toString());
				}
			}

		}
		if (content != null) {
			return new ByteArrayInputStream(content);
		} else {
			return new ByteArrayInputStream(new byte[0]);
		}
	}

	protected InputStream getXMLIS(String fileName) throws IOException {
		HttpGet httpget = new HttpGet(fileName);
		return httpRequest(targetHost, httpget, context);

	}

	protected InputStream getSOAPXMLIS(String fileName, String urn, HttpEntity entity) throws IOException {
		HttpPost httppost = new HttpPost(fileName);
		httppost.addHeader("soapaction", urn);
		httppost.addHeader("charset", "utf-8");
		httppost.addHeader("content-type", "text/xml");
		httppost.setEntity(entity);
		return httpRequest(targetHost, httppost, context);
	}

	public Map<String, Service> getServices() {
		return services;
	}

	public Service getService(String name) {
		return getServices().get(name);
	}

	public void printInfo() {
		System.out.println(name);
		System.out.println("----------------------------------");
		for (String a : services.keySet()) {
			System.out.println(a);
			Service s = services.get(a);
			for (String b : s.getActions().keySet()) {
				System.out.print("    ");
				System.out.println(b);
				System.out.print("       ");
				System.out.println(s.getActions().get(b).getArguments());
			}
		}
	}

}
