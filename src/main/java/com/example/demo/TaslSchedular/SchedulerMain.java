package com.example.demo.TaslSchedular;

import java.util.Timer;

public class SchedulerMain {
	public static void main(String args[]) throws InterruptedException {

		Timer time = new Timer();
		ScheduledTask task = new ScheduledTask();
		time.schedule(task, 2, 1000);
/*
		for (int i = 0; i <= 5; i++) {
			System.out.println("Execution in Main Thread...." + i);
			Thread.sleep(2000);
			if (i == 5) {
				System.out.println("Application Terminates");
				System.exit(0);
			}
		}	
*/
	}
}