package com.java.react.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {
	
	public ResponseEntity<?> mapValidationService(BindingResult result){
		if(result.hasErrors()) {
			Map<String, String> errormap = new HashMap<String, String>();
			for(FieldError error: result.getFieldErrors()) {
				errormap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>> (errormap ,HttpStatus.BAD_REQUEST);
		}
		return null;
	}

}
