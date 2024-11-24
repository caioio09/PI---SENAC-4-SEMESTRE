package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
}
