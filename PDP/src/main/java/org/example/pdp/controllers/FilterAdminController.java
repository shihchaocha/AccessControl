package org.example.pdp.controllers;

import org.example.models.FilterEntityDTO;
import org.example.pdp.models.FilterEntity;
import org.example.pdp.models.FilterEntityMapper;
import org.example.pdp.models.FilterEntityRepository;
import org.example.pdp.services.FilterLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pdp/filters")
public class FilterAdminController {

    @Autowired
    private FilterEntityRepository filterEntityRepository;

    @Autowired
    private FilterLoader filterLoader;

    private final Object lock = new Object();

    // 獲取所有 FilterEntityDTO
    @GetMapping
    public List<FilterEntityDTO> getAllFilters() {
        List<FilterEntity> filters = filterEntityRepository.findAll();
        return filters.stream()
                .map(FilterEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 根據 ID 獲取單個 FilterEntityDTO
    @GetMapping("/{id}")
    public ResponseEntity<FilterEntityDTO> getFilterById(@PathVariable Long id) {
        FilterEntity filterEntity = filterEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with id " + id));
        return ResponseEntity.ok(FilterEntityMapper.toDTO(filterEntity));
    }

    // 創建新的 FilterEntity
    @PostMapping
    public FilterEntityDTO createFilter(@RequestBody FilterEntityDTO filterDTO) throws Exception {
        synchronized (lock) {
            FilterEntity filterEntity = FilterEntityMapper.toEntity(filterDTO);
            FilterEntity savedEntity = filterEntityRepository.save(filterEntity);
            filterLoader.loadFilters();
            return FilterEntityMapper.toDTO(savedEntity);
        }
    }

    // 更新現有的 FilterEntity
    @PutMapping("/{id}")
    public ResponseEntity<FilterEntityDTO> updateFilter(@PathVariable Long id, @RequestBody FilterEntityDTO filterDTO) throws Exception {
        synchronized (lock) {
            FilterEntity filterEntity = filterEntityRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Filter not found with id " + id));

            filterEntity.setName(filterDTO.getName());
            filterEntity.setFilterOrder(filterDTO.getOrder());
            filterEntity.setClassName(filterDTO.getClassName());

            FilterEntity updatedEntity = filterEntityRepository.save(filterEntity);
            filterLoader.loadFilters();
            return ResponseEntity.ok(FilterEntityMapper.toDTO(updatedEntity));
        }
    }

    // 刪除 FilterEntity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) throws Exception {
        synchronized (lock) {
            FilterEntity filterEntity = filterEntityRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Filter not found with id " + id));

            filterEntityRepository.delete(filterEntity);
            filterLoader.loadFilters();
            return ResponseEntity.noContent().build();
        }
    }
}
