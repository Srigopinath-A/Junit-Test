package com.example.Junit.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long movieid;
	
	@NonNull
	private String name;
	
	@NonNull
	private String about;
	
	private int rating;
}
