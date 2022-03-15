package com.international.bank.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName;
	private String fieldName;
	private Object accountObject;

	public ResourceNotFoundException(String resourceName, String fieldName, Object accountObject) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, accountObject));
		this.resourceName= resourceName;
		this.fieldName= fieldName;
		this.accountObject= accountObject;
	}
	
	public String getResourceName() {return resourceName;}
	
	public String getFieldName() {return fieldName;}
	
	public Object getAccountObject() {return accountObject;}
}
