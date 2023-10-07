package com.codewithdurgesh.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty(message = "Name cannot be Blank")
	@Size(min=4,message = "Username should me more than 3 chars")
	private String name;
	@Email(message = "Email is not Valid")
	private String email;
	@NotEmpty(message = "Password cannot be empty")
	@Size(min=4,max=10,message = "Password should be >3 and <10")
	private String password;
	@NotEmpty
	private String about;
}
