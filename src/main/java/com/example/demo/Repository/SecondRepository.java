package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.SecondEntity;

public interface SecondRepository extends JpaRepository<SecondEntity,Long>{

}
