package com.vn.hcmus.qlqtpm.backendvnexpress.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vn.hcmus.qlqtpm.backendvnexpress.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value ="SELECT * FROM user WHERE email = :email LIMIT 1", nativeQuery = true)
    UserEntity logInByEmail(@Param("email") String email);

    @Query(value ="SELECT * FROM user WHERE email = :email LIMIT 1", nativeQuery = true)
    UserEntity isExistEmail(@Param("email") String email);
    
}
