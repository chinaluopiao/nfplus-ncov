package com.southcn.nfapp.ncov.entity;

import com.southcn.nfapp.ncov.unified.UnifiedArea;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxUnifiedData implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    private String id;

    private Date time;

    private UnifiedGlobal global;

    private List<UnifiedDay> days;

    private List<UnifiedDay> gdDays;

    private List<UnifiedArea> domestic;

    private List<UnifiedArea> abroad;

}
