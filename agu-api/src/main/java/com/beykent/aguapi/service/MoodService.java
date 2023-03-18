package com.beykent.aguapi.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beykent.aguapi.entity.Mood;
import com.beykent.aguapi.repository.MoodRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoodService {
	
	private final MoodRepository moodRepository;
	
	@PostConstruct
	public void prePersist() {
		List<Mood> moodList = Arrays.asList(
				new Mood(Mood.HAPPY, "HAPPY"),
				new Mood(Mood.SAD, "SAD"),
				new Mood(Mood.ANGRY, "ANGRY"),
				new Mood(Mood.EXCITED, "EXCITED"),
				new Mood(Mood.BORED, "BORED"),
				new Mood(Mood.INSPIRED, "INSPIRED"),
				new Mood(Mood.CALM, "CALM"),
				new Mood(Mood.CONFUSED, "CONFUSED"),
				new Mood(Mood.CONFUSED, "CONFUSED"),
				new Mood(Mood.HANGRY, "HANGRY")
			);
		this.moodRepository.saveAll(moodList);
	}

}
