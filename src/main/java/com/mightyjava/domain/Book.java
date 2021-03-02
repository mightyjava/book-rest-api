package com.mightyjava.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private String author;

	@NotNull
	private String coverPhotoURL;

	@NotNull
	private Long isbnNumber;

	@NotNull
	private Double price;

	@NotNull
	private String language;

}
