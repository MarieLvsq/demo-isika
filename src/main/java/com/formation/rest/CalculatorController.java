/**
 * 
 */
package com.formation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formation.service.Calculator;

/**
 * @author Stagiaite
 *
 */
@RestController
public class CalculatorController {
	
	@Autowired
	private Calculator calculator;
	
	@RequestMapping("/")
	String root() {
		return "<h1> Hello from isika V2 </h1>";
	}
	
	@RequestMapping("/sum")
	String sum(@RequestParam("a")Integer a,@RequestParam("b") Integer b) {
		return String.valueOf(calculator.sum(a, b));
	}
}
