package com.southcn.nfapp.ncov.bean;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PneumoniaStats implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Date statsTime;

    private List<ProvinceStat> provinceStats;
}
