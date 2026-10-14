package com.hunghaimart.repository;

import com.hunghaimart.entity.GiaoDichTonKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoDichTonKhoRepository extends JpaRepository<GiaoDichTonKho, Long> {
    List<GiaoDichTonKho> findByChiNhanhId(Long chiNhanhId);
}
