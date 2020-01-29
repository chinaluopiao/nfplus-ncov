package com.southcn.nfapp.ncov.bean.nfplus;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class NfXlsDataListener extends AnalysisEventListener<NfXlsData> implements Serializable {

    private static final long serialVersionUID = 42L;

    private NfXlsHeaderData headerData;

    private List<NfXlsData> list = new ArrayList<>();

    @Override
    public void invoke(NfXlsData nfXlsData, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }
}
