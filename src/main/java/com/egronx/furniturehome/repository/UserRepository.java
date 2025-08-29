package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
