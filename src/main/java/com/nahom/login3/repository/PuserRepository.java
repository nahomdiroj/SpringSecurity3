package com.nahom.login3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nahom.login3.entity.Puser;
import java.util.Optional;


public interface PuserRepository extends JpaRepository<Puser ,Long> {
    @Query(value = "select * from puser where email=?1", nativeQuery = true)
    Optional<Puser> findByEmail(String email);
}
