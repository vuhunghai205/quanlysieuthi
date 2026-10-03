package com.hunghaimart.repository;

import com.hunghaimart.entity.ChiNhanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ChiNhanhRepository extends JpaRepository<ChiNhanh, Long> {
    Optional<ChiNhanh> findByMaChiNhanh(String maChiNhanh);
}
