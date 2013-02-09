package com.dropship.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropship.example.model.SimpleUser;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long>{

}
