package com.example.demo;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.example.demo.Model.FirstEntity;
//import com.example.demo.Model.SecondEntity;
//import com.example.demo.Repository.FirstRepository;
//import com.example.demo.Repository.SecondRepository;

@SpringBootApplication
public class BootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootAppApplication.class, args);
		
		//BootAppApplication boot=new BootAppApplication();
		//boot.run();
	}
	/*
    @Autowired
    private FirstRepository firstRepository;

    @Autowired
    private SecondRepository secondRepository;

    public void run(){

        FirstEntity firstSaved = this.firstRepository.save(new FirstEntity("first database"));

        SecondEntity secondSaved = this.secondRepository.save(new SecondEntity("second database"));

       System.out.println(firstSaved.toString());

       System.out.println(secondSaved.toString());

    }
    */
}
