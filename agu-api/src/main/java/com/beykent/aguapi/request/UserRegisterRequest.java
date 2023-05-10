package com.beykent.aguapi.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
	
	@NotBlank
	@NotNull
	@Size(min = 5, max = 60)
	@Email
	private String email;
	
	@NotBlank
	@NotNull
	@Size(min = 3, max = 25)
	private String userName;

	@NotBlank
	@NotNull
	@Size(min = 8, max = 25)
	private String password;
	
}
