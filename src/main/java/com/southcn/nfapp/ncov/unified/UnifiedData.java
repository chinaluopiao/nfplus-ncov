package com.southcn.nfapp.ncov.unified;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnifiedData implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty("更新时间")
    private Date time;

    @ApiModelProperty("全局数据")
    private UnifiedGlobal global;

    @ApiModelProperty("每天的数据")
    private List<UnifiedDay> days;

    @ApiModelProperty("广东省每天数据")
    private List<UnifiedDay> gdDays;

    @ApiModelProperty("国内数据")
    private List<UnifiedArea> domestic;

    @ApiModelProperty("国外数据")
    private List<UnifiedArea> abroad;

}
