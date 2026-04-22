package com.parkease.auth.repository;

import com.parkease.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Integer userId);
    boolean existsByEmail(String email);
    List<User> findAllByRole(User.Role role);
    Optional<User> findByVehiclePlate(String vehiclePlate);
    Optional<User> findByPhone(String phone);
    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);
    void deleteByUserId(Integer userId);
}