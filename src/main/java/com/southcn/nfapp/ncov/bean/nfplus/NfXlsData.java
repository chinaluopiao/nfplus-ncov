package com.southcn.nfapp.ncov.bean.nfplus;

import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NfXlsData implements Serializable {

    private static final long serialVersionUID = 42L;
    private String city;
    private Integer confirm;
    private Integer dead;
    private Integer heal;


    public UnifiedArea toUnifiedArea() {
        UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder().area(NcovConst.CHINA_KEYWORD);
        builder.city(this.city);
        //确诊
        builder.confirm(this.confirm);
        //怀疑
        builder.suspect(NumberUtils.INTEGER_ZERO);
        //自愈
        builder.heal(this.heal);
        //死亡
        builder.dead(this.dead);
        return builder.build();
    }

}
