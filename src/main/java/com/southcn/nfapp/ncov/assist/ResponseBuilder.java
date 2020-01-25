package com.southcn.nfapp.ncov.assist;

import com.southcn.nfapp.ncov.enums.MsgCode;

public class ResponseBuilder {

    public static Response buildSuccess() {
        return build(MsgCode.SUCCESS, MsgCode.SUCCESS.getMsg(), Boolean.TRUE, null);
    }

    public static Response buildSuccess(Object data) {
        return build(MsgCode.SUCCESS, MsgCode.SUCCESS.getMsg(), Boolean.TRUE, data);
    }

    public static Response buildFail() {
        return build(MsgCode.FAIL, MsgCode.FAIL.getMsg(), Boolean.FALSE, null);
    }

    public static Response buildFail(Object data) {
        return build(MsgCode.FAIL, MsgCode.FAIL.getMsg(), Boolean.FALSE, data);
    }


    public static Response build(MsgCode msgCode, String msg, Boolean success, Object data) {
        return Response.builder().code(msgCode.getCode()).msg(msg).success(success).data(data).build();
    }


}
