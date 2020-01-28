package com.southcn.nfapp.ncov.bean.tx;


import com.alibaba.fastjson.annotation.JSONField;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
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
public class TxGlobalData implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer confirmCount;
    private Integer suspectCount;
    private Integer deadCount;
    private Integer cure;
    private Boolean useTotal;
    private String hintWords;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date recentTime;


    public UnifiedGlobal getGlobal() {
        UnifiedGlobal.UnifiedGlobalBuilder builder = UnifiedGlobal.builder();
        builder.confirm(this.confirmCount).suspected(this.suspectCount)
                .cure(this.cure).die(this.deadCount).time(this.recentTime);

        return builder.build();
    }


}
