package com.hunghaimart.repository;

import com.hunghaimart.entity.GiaoDichTonKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoDichTonKhoRepository extends JpaRepository<GiaoDichTonKho, Long> {
}
