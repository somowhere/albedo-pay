/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.qingju.java.common.pay.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
@Slf4j
public class ClientCustomSSL {

    private ClientCustomSSL clientCustomSSL;

    private CloseableHttpClient closeableHttpClient;

    public static ClientCustomSSL getInstance(){
        return new ClientCustomSSL();
    }

    public CloseableHttpClient getWCCloseableHttpClient(String certFile, String certPass){
        InputStream instream = null;
        CloseableHttpClient closeableHttpClient = null;
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
            instream = resourceLoader.getResources(certFile)[0].getInputStream();
            keyStore.load(instream, certPass.toCharArray());

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, certPass.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            closeableHttpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return closeableHttpClient;
    }
}
