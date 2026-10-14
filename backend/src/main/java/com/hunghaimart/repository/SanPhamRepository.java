package com.hunghaimart.repository;

import com.hunghaimart.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    List<SanPham> findByChiNhanhId(Long chiNhanhId);
}
