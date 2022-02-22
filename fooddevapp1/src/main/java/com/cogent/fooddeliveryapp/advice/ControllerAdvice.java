package com.cogent.fooddeliveryapp.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cogent.fooddeliveryapp.exceptions.NameAlreadyExistsException;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.exceptions.apierror.ApiError;



//will handle all excpetions 
													//which are thrown by the controller
													// or the rest controller using throws
@org.springframework.web.bind.annotation.ControllerAdvice 
public class ControllerAdvice  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NameAlreadyExistsException.class) //responsible 
	public ResponseEntity<?> nameAlreadyExistsException(NameAlreadyExistsException e){
		
		Map<String, String> map = new HashMap<>();
		map.put("message","Name Already Exists");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"name already exist",e);
		return buildResponseEntity(apiError);
	}
	@ExceptionHandler(NoDataFoundException.class) //responsible 
	public ResponseEntity<?> NoDataFoundException(NoDataFoundException e){
		
		Map<String, String> map = new HashMap<>();
		map.put("message","no data found");
		System.out.println(e);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,e.getMessage(),e);
		System.out.println(apiError);
		return buildResponseEntity(apiError);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
		
	,HttpHeaders headers,HttpStatus httpStatus, WebRequest request){
		
		ApiError apiError = new ApiError(httpStatus);
		
		apiError.setMessage("Validation Error");
		
		apiError.addValidationErrors(ex.getFieldErrors());
		apiError.addValidationObjectErrors(ex.getBindingResult().getGlobalErrors());
		
		return buildResponseEntity(apiError);
		
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		
		
		return new ResponseEntity<Object>(apiError,apiError.getStatus());
		
		
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
//		apiError.setDebugMessage(e.getRequiredType().getName());
		
		return buildResponseEntity(apiError);
	}

	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)		
	protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		apiError.setDebugMessage(e.getRequiredType().getName());
		
		return buildResponseEntity(apiError);
	}
	
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<?> handleMethodException(MethodArgumentTypeMismatchException e) {
		
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
		apiError.setMessage(e.getMessage());
		
		return buildResponseEntity(apiError);
	}
	

}
