package com.supeream.weblogic;

import com.supeream.serial.BytesOperation;

import java.io.*;

/**
 * Created by nike on 17/7/11.
 */
public class ObjectTest {
    public static void main(String[] args) throws Exception {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeUTF("xxx");
        String xx = BytesOperation.bytesToHexString(byteArrayOutputStream.toByteArray());
        System.out.println(xx);
        byte[] cons = BytesOperation.hexStringToBytes(xx);



        ByteArrayInputStream bis = new ByteArrayInputStream(cons);
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        objectInputStream.readObject();
    }
}
