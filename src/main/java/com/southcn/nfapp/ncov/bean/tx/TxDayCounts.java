package com.southcn.nfapp.ncov.bean.tx;

import com.alibaba.fastjson.annotation.JSONField;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TxDayCounts implements Serializable {

    private static final long serialVersionUID = 42L;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("确诊")
    private Integer confirm;

    @ApiModelProperty("可疑")
    @JSONField(serialize = false, deserialize = false)
    private Integer suspect;

    @ApiModelProperty("治愈")
    private Integer dead;

    @ApiModelProperty("死亡")
    private Integer heal;

    public UnifiedDay getUnifiedDay() {
        UnifiedDay unifiedDay = new UnifiedDay();
        BeanUtils.copyProperties(this, unifiedDay);
        return unifiedDay;
    }
}
