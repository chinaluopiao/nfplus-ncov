package com.southcn.nfapp.ncov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MsgCode {

    SUCCESS(200, "成功"),
    FAIL(100, "失败");

    private Integer code;
    private String msg;

}
