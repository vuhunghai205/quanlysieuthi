package com.hunghaimart.repository;

import com.hunghaimart.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
    List<DonHang> findByChiNhanhId(Long chiNhanhId);
}
