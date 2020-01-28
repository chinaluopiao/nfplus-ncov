package com.southcn.nfapp.ncov.unified;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnifiedArea implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty("地区")
    private String area;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("确诊")
    private Integer confirm;
    @ApiModelProperty("可疑")
    private Integer suspect;
    @ApiModelProperty("治愈")
    private Integer dead;
    @ApiModelProperty("死亡")
    private Integer heal;

    @ApiModelProperty("子节点数据")
    private List<UnifiedArea> children;


}
