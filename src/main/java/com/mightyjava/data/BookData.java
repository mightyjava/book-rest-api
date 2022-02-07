package com.mightyjava.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookData {
	private String title;
	private String author;
	private String coverPhotoURL;
	private Long isbnNumber;
	private Double price;
	private String language;
}
