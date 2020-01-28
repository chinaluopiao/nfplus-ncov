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
public class UnifiedDay implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("确诊")
    private Integer confirm;

    @ApiModelProperty("可疑")
    private Integer suspect;

    @ApiModelProperty("治愈")
    private Integer dead;

    @ApiModelProperty("死亡")
    private Integer heal;


}
