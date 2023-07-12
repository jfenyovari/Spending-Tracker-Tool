package com.sprintform.stt.mongodb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transaction")
public class Transaction {

	@Transient
	public static final String SEQUENCE_NAME = "transaction_sequence";

	@Id
	private String id;
	private String summary;

	private String category;

	private int sum;

	private String currency;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paid;

	public Transaction() {
	}

	public Transaction(String id, String summary, String category, int sum, String currency, LocalDateTime paid) {
		this.id = id;
		this.summary = summary;
		this.category = category;
		this.sum = sum;
		this.currency = currency;
		this.paid = paid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDateTime getPaid() {
		return paid;
	}

	public void setPaid(LocalDateTime paid) {
		this.paid = paid;
	}
}
