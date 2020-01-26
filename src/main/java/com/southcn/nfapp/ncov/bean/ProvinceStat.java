package com.southcn.nfapp.ncov.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProvinceStat implements Serializable {

    private static final long serialVersionUID = 42L;
    private String provinceName;
    private String provinceShortName;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private String comment;

    private List<CitiesStat> cities;


}
