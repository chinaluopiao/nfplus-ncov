package com.southcn.nfapp.ncov.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NcovData implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer error;

    private String msg;

    @JSONField(name = "stats_time")
    private Date statsTime;

    @JSONField(name = "global_stats")
    private GlobalStats globalStats;

    @JSONField(name = "prov_stats")
    private List<ProvStats> provStats;

    @JSONField(name = "other_stats")
    private List<OtherStats> otherStats;



}
