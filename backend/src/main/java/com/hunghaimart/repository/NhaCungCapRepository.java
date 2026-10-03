package com.hunghaimart.repository;

import com.hunghaimart.entity.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Long> {
}
