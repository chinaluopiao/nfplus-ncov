package com.southcn.nfapp.ncov.unified;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnifiedGlobal implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty("确诊")
    private Integer confirm;
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
