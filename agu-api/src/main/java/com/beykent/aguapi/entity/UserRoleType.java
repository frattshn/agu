package com.beykent.aguapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_role_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoleType {
	
	public static final int ADMIN = Integer.valueOf(1);
	public static final int USER = Integer.valueOf(0);
	
	@Id
	private Integer id;
	
	private String description;

}
