package com.southcn.nfapp.ncov.bean.dxy;

import com.southcn.nfapp.ncov.bean.CitiesStat;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DxyAreaStat implements Serializable {

    private static final long serialVersionUID = 42L;
    private String provinceName;
    private String provinceShortName;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private String comment;


    private List<DxyCitiesStat> cities;


    /**
     * 获取子区域数据
     *
     * @return
     */
    public List<UnifiedArea> getChildUnifiedAreas() {
        return this.cities.parallelStream().map(DxyCitiesStat::toUnifiedArea).sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed()).collect(Collectors.toList());
    }

    public UnifiedArea toUnifiedArea() {
        UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder().country(NcovConst.CHINA_KEYWORD);
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
        builder.children(this.getChildUnifiedAreas());
        return builder.build();
    }
}
