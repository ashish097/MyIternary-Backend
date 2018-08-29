package com.dis.valid;



import org.springframework.stereotype.Component;


@Component
public class ValidationError {
	
	

   
	private  String errorMessage;
    private  int status;
    
    public ValidationError() {
    	//validation error
	
	}
    
	public ValidationError(String errorMessage, int status) {
		super();
		this.errorMessage = errorMessage;
		this.status = status;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    
    
}
