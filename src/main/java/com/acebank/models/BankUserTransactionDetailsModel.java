package com.acebank.models;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class BankUserTransactionDetailsModel implements Serializable {// POJO for Transaction

	/*
	 * POJOs are used to represent data in Java programs. They are often used in
	 * conjunction with frameworks, such as Spring Boot, to store and retrieve data
	 * from databases.
	 * 
	 * Here are some of the benefits of using POJOs:
	 * 
	 * - They are easy to understand and maintain. - They are reusable and can be
	 * used in any Java program. - They are lightweight and do not require any
	 * special framework. - They can be easily serialized and deserialized. - POJOs
	 * are a fundamental building block in Java programming. - They are used in a
	 * wide variety of applications, from simple console applications to complex
	 * enterprise applications.
	 */

	private static final long serialVersionUID = -7803560753676897236L;

	@Setter(AccessLevel.PRIVATE)
	private int id;

	private final int account1;
	private final int account2;
	private final int debit;
	private final int credit;
	private final int balance;

}
