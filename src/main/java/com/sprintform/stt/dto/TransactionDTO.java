package com.sprintform.stt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprintform.stt.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	private String id;
	private String summary;
	private CategoryEnum category;
	private int sum;
	private String currency;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime paid;


}