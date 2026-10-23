package com.vaxflow.core.repository;

import com.vaxflow.core.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByTenDangNhap(String tenDangNhap);

    Optional<Admin> findBySoDienThoai(String soDienThoai);

    boolean existsByTenDangNhap(String tenDangNhap);

    boolean existsBySoDienThoai(String soDienThoai);

    // boolean existsByEmail(String Email); sau do vao AdminService them dieu kien

    boolean existsBySoDienThoaiAndIdNot(String soDienThoai, Long id);

    boolean existsByIdNhanVien(Long idNhanVien);

    Optional<Admin> findByIdNhanVien(Long idNhanVien);
}