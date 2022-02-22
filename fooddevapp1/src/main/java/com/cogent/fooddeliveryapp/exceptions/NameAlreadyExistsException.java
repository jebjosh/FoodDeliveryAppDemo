package com.cogent.fooddeliveryapp.exceptions;

public class NameAlreadyExistsException extends RuntimeException {
	
	public NameAlreadyExistsException(String e) {
		// TODO Auto-generated constructor stub
		super(e);
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}
