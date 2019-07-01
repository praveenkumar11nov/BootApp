package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/app")
public class PaymentsController {

	@RequestMapping(value="/getPaymentPage")
	public String paymentpage() {
		return "ThemePages/paymentpage";
	}
}
