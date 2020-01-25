package com.southcn.nfapp.ncov.exception;

import com.southcn.nfapp.ncov.enums.MsgCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SouthcnException extends RuntimeException {

    private MsgCode code;
    private String msg;
    private Object data;



}
