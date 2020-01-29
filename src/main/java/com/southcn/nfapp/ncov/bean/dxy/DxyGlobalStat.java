package com.southcn.nfapp.ncov.bean.dxy;

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
public class DxyGlobalStat implements Serializable {
    private Integer id;
    private Date createTime;
    private Date modifyTime;
    private String infectSource;
    private String passWay;
    private String imgUrl;
    private String dailyPic;
    private String summary;
    private String countRemark;
    private String virus;
    private String remark1;
    private String remark2;
    private String remark3;
    private String remark4;
    private String remark5;
    private String generalRemark;
    private String abroadRemark;
    private Boolean deleted;

    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;


    public UnifiedGlobal getUnifiedGlobal() {
        UnifiedGlobal.UnifiedGlobalBuilder builder = UnifiedGlobal.builder();
        builder.time(this.modifyTime);
        builder.confirm(this.confirmedCount);
        builder.suspected(this.suspectedCount);
        builder.cure(this.curedCount);
        builder.die(this.deadCount);
        return builder.build();
    }


}
