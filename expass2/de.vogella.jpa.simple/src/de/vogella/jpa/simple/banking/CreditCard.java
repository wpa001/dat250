package de.vogella.jpa.simple.banking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CreditCard {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private int number;
    private int limit;
    private int balance;

    @OneToOne
    private Bank bank;

    @OneToOne
    private Pincode pincode;
    
    public CreditCard() {
    	
    }

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Pincode getPincode() {
		return pincode;
	}

	public void setPincode(Pincode pincode) {
		this.pincode = pincode;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", limit=" + limit + ", balance=" + balance + ", bank="
				+ bank + ", pincode=" + pincode + "]";
	}
	
}
