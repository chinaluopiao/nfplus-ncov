package com.southcn.nfapp.ncov.bean;

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
public class GlobalStats implements Serializable {

    private static final long serialVersionUID = 42L;


    private String title;
    private Integer diagnose;
    private Integer suspected;
    private Integer cure;
    private Integer die;
    private Integer total;
    private Date time;



}
