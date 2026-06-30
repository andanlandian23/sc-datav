package com.scdatav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scdatav.entity.EconIndicator;
import com.scdatav.entity.TradeRecord;
import com.scdatav.mapper.EconIndicatorMapper;
import com.scdatav.mapper.TradeRecordMapper;
import com.scdatav.service.Demo0Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Demo0ServiceImpl implements Demo0Service {

    private final EconIndicatorMapper econIndicatorMapper;
    private final TradeRecordMapper tradeRecordMapper;

    @Override
    public List<Map<String, Object>> getTrend() {
        List<EconIndicator> list = econIndicatorMapper.selectList(
                new LambdaQueryWrapper<EconIndicator>()
                        .eq(EconIndicator::getIndicator, "trend")
                        .orderByAsc(EconIndicator::getDataDate));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", item.getDataDate());
            map.put("value", item.getValue());
            map.put("value2", item.getValue2());
            map.put("label", item.getLabel());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTrade() {
        List<EconIndicator> list = econIndicatorMapper.selectList(
                new LambdaQueryWrapper<EconIndicator>()
                        .eq(EconIndicator::getIndicator, "import_export")
                        .orderByAsc(EconIndicator::getDataDate));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", item.getDataDate());
            map.put("value", item.getValue());
            map.put("value2", item.getValue2());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getQuarterly() {
        List<EconIndicator> list = econIndicatorMapper.selectList(
                new LambdaQueryWrapper<EconIndicator>()
                        .eq(EconIndicator::getIndicator, "quarterly")
                        .orderByAsc(EconIndicator::getQuarter));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("quarter", "Q" + item.getQuarter());
            map.put("value", item.getValue());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getRecords() {
        List<TradeRecord> list = tradeRecordMapper.selectList(
                new LambdaQueryWrapper<TradeRecord>().orderByDesc(TradeRecord::getId));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("typeName", item.getTypeName());
            map.put("quantity", item.getQuantity());
            map.put("tradeValue", item.getTradeValue());
            map.put("dataDate", item.getDataDate());
            return map;
        }).collect(Collectors.toList());
    }
}
