package org.example.pdp.services;

import jakarta.annotation.PostConstruct;
import org.example.pdp.filters.AccessControlFilter;
import org.example.pdp.models.FilterEntity;
import org.example.pdp.models.FilterEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FilterLoader {

    @Autowired
    private FilterEntityRepository filterEntityRepository;

    private List<AccessControlFilter> filterList = new ArrayList<>();

    @PostConstruct
    public void loadFilters() throws Exception {
        try {
            // 讀取所有 FilterEntity 資料
            List<FilterEntity> filters = filterEntityRepository.findAll();

            // 遍歷每一個 FilterEntity，通過反射實例化對應的 AccessControlFilter
            for (FilterEntity filterEntity : filters) {
                // 通過 className 反射創建對應的類別實例
                Class<?> clazz = Class.forName(filterEntity.getClassName());
                AccessControlFilter filterInstance = (AccessControlFilter) clazz.getDeclaredConstructor().newInstance();

                // 初始化過濾器
                filterInstance.init();

                System.out.println("" + filterEntity.getName() + " loaded");

                // 將實例存入列表
                filterList.add(filterInstance);
            }

            // 根據 order 排序
            Collections.sort(filterList, Comparator.comparingInt(f -> filters.stream()
                    .filter(e -> e.getClassName().equals(f.getClass().getName()))
                    .findFirst().get().getFilterOrder()));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new Exception("Error loading filters");
        }
    }

    public List<AccessControlFilter> getFilterList() {
        return filterList;
    }
}