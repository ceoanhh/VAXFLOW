package com.vaxflow.core.service;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String tenDangNhap)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Không tìm thấy tài khoản đăng nhập"
                        )
                );

        return User.withUsername(admin.getTenDangNhap())
                .password(admin.getMatKhau())
                .roles("ADMIN")
                .disabled(!admin.isTrangThai())
                .build();
    }
}