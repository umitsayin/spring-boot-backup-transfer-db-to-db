package com.example.dbtodb.repository;

import com.example.dbtodb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
