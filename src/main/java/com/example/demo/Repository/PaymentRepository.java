package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Payments;

@Repository
public interface PaymentRepository extends CrudRepository<Payments,Integer>{

	Payments findById(int id);
}
