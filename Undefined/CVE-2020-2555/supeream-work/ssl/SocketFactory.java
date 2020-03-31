package com.supeream.ssl;

import com.supeream.Main;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.net.Socket;
import java.security.SecureRandom;

/**
 * Created by nike on 17/6/29.
 */
public class SocketFactory {
    private SocketFactory() {
    }

    public static Socket newSocket(String host, int port) throws Exception {
        Socket socket = null;
        if (Main.cmdLine.hasOption("https")) {
            SSLContext context = SSLContext.getInstance("SSL");
            // 初始化
            context.init(null,
                    new TrustManager[]{new TrustManagerImpl()},
                    new SecureRandom());
            SSLSocketFactory factory = context.getSocketFactory();
            socket = factory.createSocket(host, port);
        } else {
            socket = new Socket(host, port);
        }

        return socket;
    }
}
