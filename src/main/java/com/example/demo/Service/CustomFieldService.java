package com.example.demo.Service;

import java.util.List;

import org.json.JSONObject;

import com.example.demo.Model.CustomFields;

public interface CustomFieldService extends GenericService<CustomFields>{
	List<?> allTableList(String schemaName);
	List<?> allColumnList(String schemaName,String table);
	List<?> allSchemaList();
	List<?> getCustomFields(String schema);
	String saveCustomFiels(String schema,String table, String column, String datatype, String length);
	List<?> getTableData(String schema, String table);
	String saveDataInTable(JSONObject json);
}