package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Service.CustomFieldService;

/**
 * Controller created for using/adding custom field from web application 
 * @author Praveen Kumar
 *
 */
@Controller
public class CustomFieldsController {
	/**
	 * auto wiring services
	 */
	@Autowired
	private CustomFieldService custom;
	/**
	 * custom fields
	 * @return JSP
	 */
	@GetMapping("/customfield")
	public String showCustomFieldSavePage() {
		return "ThemePages/customFields";
	}
	/**
	 * 
	 * @return List of schema
	 */
	@PostMapping("/getAllSchema")
	@ResponseBody
	public List<?> getAllchema() {
		return custom.allSchemaList();
	}
	/**
	 * 
	 * @param schema
	 * @return List of table
	 */
	@PostMapping("/getAllTablesFromSchema")
	@ResponseBody
	public List<?> getAllTablesFromSchema(@RequestParam String schema) {
		return custom.allTableList(schema);
	}
	/**
	 * 
	 * @param schema
	 * @param table
	 * @return List of column
	 */
	@PostMapping("/getAllColumnsFromTable")
	@ResponseBody
	public List<?> getAllColumnsFromTable(@RequestParam String schema,@RequestParam String table) {
		return custom.allColumnList(schema,table);
	}
	/**
	 * 
	 */
	@PostMapping("/getCustomFieldsFromSchema")
	@ResponseBody
	public List<?> getCustomFields(@RequestParam String schema) {
		return custom.getCustomFields(schema);
	}
	/**
	 * 
	 * @return String message
	 */
	@PostMapping("/saveCustomField")
	@ResponseBody
	public String saveCustomFiels(@RequestParam String schema,@RequestParam String table,@RequestParam String column,@RequestParam String datatype,@RequestParam String length) {
		return custom.saveCustomFiels(schema,table,column,datatype,length);
	}
	/**
	 * 
	 * @param schema
	 * @param table
	 * @return List of data
	 */
	@PostMapping("/getTableData")
	@ResponseBody
	public List<?> getTableDetails(@RequestParam String schema,@RequestParam String table) {
		return custom.getTableData(schema,table);
	}
	/**
	 * paramList is a json string containing schema,table and other column data
	 * @param paramList
	 * @return
	 */
	@PostMapping("/saveDataInTable")
	@ResponseBody
	public String saveDataInTable(@RequestParam String paramList) {
		String message="";
		JSONObject json;
		try {
			if(!String.valueOf(paramList).equalsIgnoreCase("") || !String.valueOf(paramList).equalsIgnoreCase("null")){
				json = new JSONObject(paramList);
				message=custom.saveDataInTable(json);
			}
		} catch (JSONException e) {
			message="Exception : "+ e.getMessage();
		}
		return message;
	}
}
