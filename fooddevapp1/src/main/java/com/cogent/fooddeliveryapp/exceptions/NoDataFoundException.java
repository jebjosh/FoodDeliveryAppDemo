package com.cogent.fooddeliveryapp.exceptions;

public class NoDataFoundException extends RuntimeException {

	public NoDataFoundException(String e) {
		// TODO Auto-generated constructor stub
		super(e);
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}
