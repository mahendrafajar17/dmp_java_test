package com.mahendrafajar.dansmultipro.services;

import com.mahendrafajar.dansmultipro.configurations.HttpErrorHandler;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

@Service
public class DMPService {
    @Value("${rest-template.timeout}")
    Integer timedOut;

    @Value("${dmp.url}")
    String dmpUrl;

    @Value("${dmp.api.path.jobs}")
    String dmpApiPathJobs;

    @Value("${dmp.api.path.job-details}")
    String dmpApiPathJobsDetails;

    public Object getJobs() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = restTemplate();

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(dmpUrl + dmpApiPathJobs, Object.class);

        return responseEntity.getBody();
    }

    public Object getJobDetails(String id) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = restTemplate();

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(dmpUrl + dmpApiPathJobsDetails + "/" + id, Object.class);

        return responseEntity.getBody();
    }

    private RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");

        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(timedOut);
        requestFactory.setReadTimeout(timedOut);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new HttpErrorHandler());
        return restTemplate;
    }

}
