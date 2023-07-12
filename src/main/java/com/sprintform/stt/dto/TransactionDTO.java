package com.sprintform.stt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TransactionDTO {

	private String id;
	private String summary;
	private String category;
	private int sum;
	private String currency;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime paid;

	public TransactionDTO() {
	}

	public TransactionDTO(String id, String summary, String category, int sum, String currency, LocalDateTime paid) {
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