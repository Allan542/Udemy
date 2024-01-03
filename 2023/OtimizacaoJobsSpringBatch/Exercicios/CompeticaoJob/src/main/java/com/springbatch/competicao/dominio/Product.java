package com.springbatch.competicao.dominio;

import java.math.BigDecimal;
import java.util.List;

public class Product {
	private Integer id;
	private String name;
	private String description;
	private String thumbnail;
	private BigDecimal price;
	private BigDecimal discountAmount;
	private String status;
	private String textImages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTextImages() {
		return textImages;
	}

	public void setTextImages(String textImages) {
		this.textImages = textImages;
	}

}
