package org.example.pip.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceProfileRepository extends JpaRepository<DeviceProfile, Long> {
    // 可以根據需求添加自定義查詢方法
}
