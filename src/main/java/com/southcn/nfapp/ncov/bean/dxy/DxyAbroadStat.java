package com.southcn.nfapp.ncov.bean.dxy;

import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
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
public class DxyAbroadStat implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer id;
    private Date createTime;
    private Date modifyTime;
    private String tags;
    private String provinceName;
    private String provinceShortName;
    private String cityName;
    private String comment;
    private Integer countryType;
    private Integer provinceId;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Integer sort;
    private String operator;

    public UnifiedArea toUnifiedArea() {
        UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder().country(this.provinceName);
        builder.area(this.getProvinceName());
        //确诊
        builder.confirm(this.confirmedCount);
        //怀疑
        builder.suspect(this.suspectedCount);
        //自愈
        builder.heal(this.curedCount);
        //死亡
        builder.dead(this.deadCount);
        //设置子节点
        return builder.build();
    }
}
