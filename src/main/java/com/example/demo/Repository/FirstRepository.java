package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.FirstEntity;

public interface FirstRepository extends JpaRepository<FirstEntity, Long>{

}
