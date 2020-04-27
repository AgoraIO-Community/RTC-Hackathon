package com.dzw.web;

import com.dzw.util.JSONChange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;
import java.util.Map;

public class ServerEncoder implements Encoder.Text<MessagePojo> {
    @Override
    public String encode(MessagePojo messagePojo) throws EncodeException {
        ObjectMapper    mapMapper= new ObjectMapper();
        try {
            String json="";
            json=mapMapper.writeValueAsString(messagePojo);
            return  json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
