package com.beykent.aguapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mood {
	
	public final static int HAPPY = Integer.valueOf(1);
	public final static int SAD = Integer.valueOf(2);
	public final static int ANGRY = Integer.valueOf(3);
	public final static int EXCITED = Integer.valueOf(4);
	public final static int BORED = Integer.valueOf(5);
	public final static int INSPIRED = Integer.valueOf(6);
	public final static int CALM = Integer.valueOf(7);
	public final static int CONFUSED = Integer.valueOf(8);
	public final static int HANGRY = Integer.valueOf(9);

	@Id
	private Integer moodId;
	
	private String moodType;
	
}