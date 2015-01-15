package com.samovich.program;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CookiesHandler {

    public static void main(String[] args) throws IOException {

        for (int run = 0; run < 20; run++ ) {

            ConfigureSSLContext();

            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod("https://wdw-latest.disney.go.com/plan/itinerary/2015-03-01/");

            try {
                client.executeMethod(method);
                Cookie[] cookies = client.getState().getCookies();

                if (null != cookies) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = cookies[i];
                        System.err.println(
                                "Cookie: " + cookie.getName() +
                                        ", Value: " + cookie.getValue() +
                                        ", IsPersistent?: " + cookie.isPersistent() +
                                        ", Expiry Date: " + cookie.getExpiryDate() +
                                        ", Comment: " + cookie.getComment());
                    }
                }

                if (null == cookies) {
                    System.out.println("No cookies presented!");
                }

                client.executeMethod(method);
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                method.releaseConnection();
            }
            System.out.println("\n");
        }

    }

    private static void ConfigureSSLContext() throws IOException {

        // configure the SSLContext with a TrustManager
        SSLContext ctx = null;

        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLContext.setDefault(ctx);

        URL url = new URL("https://wdw-latest.disney.go.com/plan/itinerary/2015-03-01/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

        });

        System.out.println("Response code: " + conn.getResponseCode());
        System.out.println("Permissions: " + conn.getPermission());
        System.out.println("Request method :" + conn.getRequestMethod());
        System.out.println("Content: " + conn.getContent());
        System.out.println("Content length: " + conn.getContentLength());
        System.out.println("Content type: " + conn.getContentType());
        System.out.println("Error stream: " + conn.getErrorStream());

        conn.disconnect();

    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
