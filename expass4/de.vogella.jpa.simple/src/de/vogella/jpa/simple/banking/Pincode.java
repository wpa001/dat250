package de.vogella.jpa.simple.banking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pincode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pincode;
    private int count;
    
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Pincode [pincode=" + pincode + ", count=" + count + "]";
	}   
}
