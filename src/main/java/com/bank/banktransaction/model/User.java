package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
 
     
    @Column(name = "name",  length = 20)
    private String name;
     
    
    @Column(name="date_of_birth", length = 45)
    private String dateofbirth;
    
    @Column(name="accounttype" , length = 64)
    private String accounttype;
    
    @Column(name="age", length = 45)
    private int age;
    
    @Column(name="address", length = 200)
    private String address;
    
    @Column(nullable = false, unique = true, length = 45)
    private String email;
     
    @Column(nullable = false, unique = true, length = 45)
    private String phonenumber;

	public User(String name, String dateofbirth, String accounttype, int age, String address, String email,
			String phonenumber) {
		super();
		this.name = name;
		this.dateofbirth = dateofbirth;
		this.accounttype = accounttype;
		this.age = age;
		this.address = address;
		this.email = email;
		this.phonenumber = phonenumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
    
    


    
}