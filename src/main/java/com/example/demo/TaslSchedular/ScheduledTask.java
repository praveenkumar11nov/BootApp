package com.example.demo.TaslSchedular;

import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

	public void run() {
		System.out.println(new Date());
	}
}