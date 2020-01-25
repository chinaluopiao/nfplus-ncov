package com.southcn.nfapp.ncov.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalStats implements Serializable {

    private static final long serialVersionUID = 42L;


    @ApiModelProperty("地区")
    private String title;
    @ApiModelProperty("确诊")
    private Integer diagnose;
    @ApiModelProperty("疑似")
    private Integer suspected;
    @ApiModelProperty("治愈")
    private Integer cure;
    @ApiModelProperty("死亡")
    private Integer die;

    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("更新时间")
    private Date time;


}
