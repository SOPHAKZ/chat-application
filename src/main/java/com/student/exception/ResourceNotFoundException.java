package com.student.exception;


public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String resourceFiled;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String resourceFiled,String fieldValue){
        super(String.format("%s not found with %s : '%s'",resourceName,resourceFiled,fieldValue));
        this.resourceName = resourceName;
        this.resourceFiled = resourceFiled;
        this.fieldValue = fieldValue;
    }
}
