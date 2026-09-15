package com.vaxflow.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thoi_gian_tao")
    private LocalDateTime thoiGianTao;

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat;

    @PrePersist
    protected void onCreate() {
        this.thoiGianTao = LocalDateTime.now();
        this.thoiGianCapNhat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.thoiGianCapNhat = LocalDateTime.now();
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public LocalDateTime getThoiGianTao() { 
        return thoiGianTao; 
    }
    
    public void setThoiGianTao(LocalDateTime thoiGianTao) { 
        this.thoiGianTao = thoiGianTao; 
    }
    
    public LocalDateTime getThoiGianCapNhat() { 
        return thoiGianCapNhat; 
    }
    
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { 
        this.thoiGianCapNhat = thoiGianCapNhat; 
    }
}