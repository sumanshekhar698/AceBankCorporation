package com.acebank.models;

//Entities map to a row of DB to a class
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class BankUserModel implements Serializable { // POJO for the bankholder details

	private static final long serialVersionUID = -7113631521329269747L;
	private int accountNumber;
	private String firstName, lastName;
	private String email, password;
	private long aadharNumber;
	private int balance;

	private int depositAmount;
	private int transferAmount;
	private int transferToAccount;

	private List<BankUserTransactionDetailsModel> bankUserTransactionDetailsModelList = new ArrayList<>();
}
