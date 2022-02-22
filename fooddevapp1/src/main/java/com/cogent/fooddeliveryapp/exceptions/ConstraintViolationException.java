package com.cogent.fooddeliveryapp.exceptions;

public class ConstraintViolationException  extends RuntimeException{
	
	public ConstraintViolationException(String msg) {
	
		
		super(msg);// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
