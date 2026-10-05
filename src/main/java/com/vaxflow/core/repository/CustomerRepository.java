package com.vaxflow.core.repository;

import com.vaxflow.core.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByIdDesc();

    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.hoTen) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "c.soDienThoai LIKE CONCAT('%', :kw, '%') OR " +
           "c.cccd LIKE CONCAT('%', :kw, '%') " +
           "ORDER BY c.id DESC")
    List<Customer> searchCustomers(@Param("kw") String keyword);

    boolean existsBySoDienThoai(String soDienThoai);
}
