package com.southcn.nfapp.ncov.bean.nfplus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NfXlsHeaderData implements Serializable {

    private static final long serialVersionUID = 42L;
    private String city;
    private String confirm;
    private String dead;
    private String heal;
    private String text;

}
