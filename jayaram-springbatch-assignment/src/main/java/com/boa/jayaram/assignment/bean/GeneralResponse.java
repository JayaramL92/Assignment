package com.boa.jayaram.assignment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse {

	private int httpStatus;
	private String message;

}
