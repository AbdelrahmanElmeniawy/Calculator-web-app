package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String hi() {
		return "0";
	}

	@GetMapping("/add")
	public String add(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber,
			@RequestParam(value = "second", defaultValue = "0.0") Double secondNumber) {
		return String.format((firstNumber + secondNumber) + "");
	}

	@GetMapping("/minus")
	public String minus(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber,
			@RequestParam(value = "second", defaultValue = "0.0") Double secondNumber) {
		return String.format((secondNumber - firstNumber) + "");
	}

	@GetMapping("/multi")
	public String multi(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber,
			@RequestParam(value = "second", defaultValue = "0.0") Double secondNumber) {
		return String.format((firstNumber * secondNumber) + "");
	}

	@GetMapping("/devide")
	public String devide(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber,
			@RequestParam(value = "second", defaultValue = "0.0") Double secondNumber) {
		if (firstNumber == 0)
			return "Invalid Input";
		return String.format((secondNumber / firstNumber) + "");
	}

	@GetMapping("/sign")
	public String sign(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber) {
		return String.format((-1 * firstNumber) + "");
	}

	@GetMapping("/percent")
	public String percent(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber) {
		return String.format((firstNumber / 100) + "");
	}

	@GetMapping("/inverse")
	public String inverse(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber) {
		if (firstNumber == 0)
			return "Invalid Input";
		return String.format((1 / firstNumber) + "");
	}

	@GetMapping("/square")
	public String square(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber) {
		return String.format((firstNumber * firstNumber) + "");
	}

	@GetMapping("/sqrt")
	public String sqrt(@RequestParam(value = "first", defaultValue = "0.0") Double firstNumber) {
		if (firstNumber < 0)
			return "Invalid Input";
		return String.format((Math.sqrt(firstNumber)) + "");
	}

}
