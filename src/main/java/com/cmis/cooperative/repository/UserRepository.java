package com.cmis.cooperative.repository;

import com.cmis.cooperative.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByPublicId(UUID publicId);
}
