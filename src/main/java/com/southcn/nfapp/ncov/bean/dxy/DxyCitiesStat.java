package com.southcn.nfapp.ncov.bean.dxy;


import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DxyCitiesStat implements Serializable {

    private static final long serialVersionUID = 42L;
    private String cityName;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;

    public UnifiedArea toUnifiedArea() {
        UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder().area(NcovConst.CHINA_KEYWORD);
        builder.city(this.getCityName());
        //确诊
        builder.confirm(this.confirmedCount);
        //怀疑
        builder.suspect(this.suspectedCount);
        //自愈
        builder.heal(this.curedCount);
        //死亡
        builder.dead(this.deadCount);
        return builder.build();
    }

}
