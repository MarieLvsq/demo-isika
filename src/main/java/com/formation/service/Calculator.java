/**
 * 
 */
package com.formation.service;

import org.springframework.stereotype.Service;

/**
 * @author Stagiaite
 *
 */
@Service
public class Calculator {
	
	public int sum(int a, int b) {
		return a+b;
	}
	
	public int multiply(int a, int b) {
		return a*b;
	}

	public int divide(int a, int b) {
		return a/b;
	}
	
}