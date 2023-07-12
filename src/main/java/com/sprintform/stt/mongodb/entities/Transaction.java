package com.sprintform.stt.mongodb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprintform.stt.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {

	@Transient
	public static final String SEQUENCE_NAME = "transaction_sequence";

	@Id
	private String id;

	private String summary;

	private CategoryEnum category;

	private BigDecimal sum;

	private String currency;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paid;
}
