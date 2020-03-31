package com.supeream.ssl;

import weblogic.security.SSL.TrustManager;

import java.security.cert.X509Certificate;

/**
 * Created by nike on 17/6/29.
 */
public class WeblogicTrustManager implements TrustManager {
    @Override
    public boolean certificateCallback(X509Certificate[] x509Certificates, int i) {
        return true;
    }
}
