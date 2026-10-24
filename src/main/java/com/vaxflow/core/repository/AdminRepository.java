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

    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Admin> findByResetToken(String resetToken);

    boolean existsBySoDienThoaiAndIdNot(String soDienThoai, Long id);

    boolean existsByIdNhanVien(Long idNhanVien);

    Optional<Admin> findByIdNhanVien(Long idNhanVien);
}