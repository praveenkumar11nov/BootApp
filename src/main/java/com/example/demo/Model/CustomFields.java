package com.example.demo.Model;

import lombok.Data;

/**
 * Entity class for custom fields
 * @author Praveen Kumar
 *
 */
@Data
public class CustomFields {
	private int customerid;
	private String tablename;
	private String columnname;
	private String apiname;
	private String datatype;
	private int length;
}