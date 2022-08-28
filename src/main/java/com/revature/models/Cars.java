package com.revature.models;

import java.util.Objects;

import com.revature.annotations.Id;

public class Cars {

	@Id
	private int id;
	private String year;
	private String make;
	private String model;
	private String color;

	public Cars() {
		super();
	}

	public Cars(int id, String year, String make, String model, String color) {
		super();
		this.id = id;
		this.year = year;
		this.make = make;
		this.model = model;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, id, make, model, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Cars other = (Cars) obj;
		return Objects.equals(color, other.color) && id == other.id && Objects.equals(make, other.make)
				&& Objects.equals(model, other.model) && Objects.equals(year, other.year);
	}

	@Override
	public String toString() {
		return "Cars [id=" + id + ", year=" + year + ", make=" + make + ", model=" + model + ", color=" + color + "]";
	}













}
