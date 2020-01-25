package com.southcn.nfapp.ncov.assist;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Response {
    private Integer code;
    private String msg;
    private Object data;
    private Boolean success;
}
