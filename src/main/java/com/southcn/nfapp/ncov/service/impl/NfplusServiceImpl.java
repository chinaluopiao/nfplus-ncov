package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.southcn.nfapp.ncov.bean.nfplus.NfXlsData;
import com.southcn.nfapp.ncov.bean.nfplus.NfXlsHeaderData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.dao.NfUnifiedDataRepository;
import com.southcn.nfapp.ncov.dao.NfXlsUnifiedDataRepository;
import com.southcn.nfapp.ncov.entity.NfUnifiedData;
import com.southcn.nfapp.ncov.entity.NfXlsUnifiedData;
import com.southcn.nfapp.ncov.service.NfplusService;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import com.southcn.nfapp.ncov.utils.NfplusUtils;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NfplusServiceImpl implements NfplusService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private NfUnifiedDataRepository nfUnifiedDataRepository;

    @Autowired
    private NfXlsUnifiedDataRepository nfXlsUnifiedDataRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean spiderSpecialTopic() {
        log.info("专题稿件数据。。。。");
        final String url = "https://api.nfapp.southcn.com/nanfang_if/v1/getSpecialTopic?columnId=17076&count=20&type=0";
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            log.info("专题稿件数据不为空，存储缓存");
            this.stringRedisTemplate.opsForValue().set(NcovConst.NFPLUS_SPECIAL_TOPIC_DATA, string);

            JSONObject jsonObject = JSON.parseObject(string);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray columns = data.getJSONArray("columns");
            //获取到全部稿件数据
            List<JSONObject> articles = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                JSONArray jsonArray = columns.getJSONObject(i).getJSONObject("articles").getJSONArray("list");
                for (int j = 0; j < jsonArray.size(); j++) {
                    articles.add(jsonArray.getJSONObject(j));
                }
            }

            //全国版本最新动态
            List<JSONObject> collect = articles.parallelStream().
                    filter(value -> NcovConst.NEW_DYNAMIC_COLUMNS.contains(value.getInteger("colID"))).sorted(Comparator.comparing(value -> value.getString("publishtime"))).collect(Collectors.toList());
            Collections.reverse(collect);
            this.redisTemplate.opsForValue().set(NcovConst.NEW_DYNAMIC_COLUMNS_ARTICLES, collect);

            //广东版本

            List<JSONObject> gDcollect = articles.parallelStream().
                    filter(value -> NcovConst.NEW_DYNAMIC_GUANGDONG_COLUMNS.contains(value.getInteger("colID"))).sorted(Comparator.comparing(value -> value.getString("publishtime"))).collect(Collectors.toList());
            Collections.reverse(gDcollect);
            this.redisTemplate.opsForValue().set(NcovConst.NEW_DYNAMIC_COLUMNS_GUANGDONG_ARTICLES, gDcollect);

        }
        log.info("专题稿件数据抓取结束。");
        return null;
    }

    @Override
    public Boolean nfWriteback() {
        Object object = this.redisTemplate.opsForValue().get(NcovConst.NF_UNIFIED_NCOV_DATA);
        if (object instanceof UnifiedData) {
            UnifiedData data = (UnifiedData) object;
            //存储数据库
            NfUnifiedData nfUnifiedData = new NfUnifiedData();
            BeanUtils.copyProperties(data, nfUnifiedData);
            nfUnifiedData.setId(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(data.getGlobal().getTime()));
            this.nfUnifiedDataRepository.save(nfUnifiedData);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean nfFileWriteback() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        File file = new File(jarFile.getParentFile().toString() + File.separator + NcovConst.NF_FILE_WRITEBACK_FILENAME);
        log.info("读取文件：{}", file.getAbsoluteFile());
        if (file.exists() && file.canRead()) {
            Optional<Object> header = EasyExcel.read(file).head(NfXlsHeaderData.class).headRowNumber(NumberUtils.INTEGER_ZERO).sheet().doReadSync().stream().findFirst();
            header.ifPresent(value -> {
                UnifiedData.UnifiedDataBuilder builder = UnifiedData.builder().time(new Date());
                NfXlsHeaderData headerData = (NfXlsHeaderData) value;
                List<NfXlsData> list = EasyExcel.read(file).head(NfXlsData.class).headRowNumber(2).sheet().doReadSync();
                log.info("xls数据头:{}", JSON.toJSONString(headerData));
                log.info("xls数据集合:{}", JSON.toJSONString(list));
                Date date = NfplusUtils.parseXlsHeaderDate(headerData.getText());
                UnifiedGlobal global = NfplusUtils.getUnifiedGlobal(headerData.getText(), date, list);
                builder.global(global);
                builder.domestic(Collections.singletonList(NfplusUtils.getUnifiedArea(global, list)));
                //设置广东省天的
                builder.gdDays(NfplusUtils.getGdUnifiedDay(this.nfXlsUnifiedDataRepository.findAll()));
                UnifiedData data = builder.build();
                log.info("xls数据组装:{}", JSON.toJSONString(data));
                this.redisTemplate.opsForValue().set(NcovConst.NF_XLS_UNIFIED_NCOV_DATA, data);
                NfXlsUnifiedData target = new NfXlsUnifiedData();
                BeanUtils.copyProperties(data, target);
                target.setId(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(data.getGlobal().getTime()));
                this.nfXlsUnifiedDataRepository.save(target);
                boolean delete = file.delete();
                log.info("处理文件{}后删除文件结果:{}", file.getAbsoluteFile(), delete);
            });
        } else {
            log.info("文件读取异常:{}", file.getAbsoluteFile());
        }
        return Boolean.TRUE;
    }
}
