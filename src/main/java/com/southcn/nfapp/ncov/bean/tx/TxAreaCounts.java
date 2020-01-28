package com.southcn.nfapp.ncov.bean.tx;

import com.southcn.nfapp.ncov.unified.UnifiedArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TxAreaCounts implements Serializable {

    private static final long serialVersionUID = 42L;

    private String country;
    private String area;
    private String city;
    private Integer confirm;
    private Integer suspect;
    private Integer dead;
    private Integer heal;


    public UnifiedArea getUnifiedArea() {
        UnifiedArea unifiedArea = new UnifiedArea();
        BeanUtils.copyProperties(this, unifiedArea);
        return unifiedArea;
    }
}
