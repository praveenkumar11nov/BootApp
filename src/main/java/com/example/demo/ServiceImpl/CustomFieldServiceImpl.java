package com.example.demo.ServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.Model.CustomFields;
import com.example.demo.Service.CustomFieldService;

@Service
public class CustomFieldServiceImpl extends GenericServiceImpl<CustomFields> implements  CustomFieldService{

	@Override
	public List<?> allTableList(String schemaName) {
		List<?> alltables=new ArrayList();
		try {
			alltables=getCustomEntityManager("pgsql").createNativeQuery("SELECT DISTINCT table_name FROM information_schema.columns WHERE table_schema='"+schemaName+"' and table_name LIKE 'cc_%'  ORDER BY table_name").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alltables;
	}

	@SuppressWarnings("null")
	@Override
	public List<?> allColumnList(String schemaName,String table) {
		List<?> allColumns=new ArrayList();
		List allCustomColumns=new ArrayList();
		try {
			allColumns=getCustomEntityManager("pgsql").createNativeQuery("SELECT COLUMN_NAME||' ['||data_type||']' FROM information_schema.columns WHERE table_schema='"+schemaName+"' and table_name='"+table+"' ORDER BY ordinal_position").getResultList();
			allCustomColumns=getCustomEntityManager("pgsql").createNativeQuery("SELECT field_name||' ['||field_type||']' FROM "+schemaName+".custom_fields WHERE table_name='"+table+"'").getResultList();
			if(allCustomColumns!=null||allCustomColumns.size()>0) {
				allColumns.addAll(allCustomColumns);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allColumns;
	}

	@Override
	public List<?> allSchemaList() {
		List<?> allColumns=new ArrayList();
		try {
			allColumns=getCustomEntityManager("pgsql").createNativeQuery("SELECT DISTINCT table_schema FROM information_schema.columns WHERE table_schema NOT IN ('information_schema','pg_catalog')").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allColumns;
	}

	@Override
	public List<?> getCustomFields(String schema) {
		List<?> allColumns=new ArrayList();
		try {
			allColumns=getCustomEntityManager("pgsql").createNativeQuery("SELECT COLUMN_NAME,data_type FROM information_schema.columns WHERE table_schema='"+schema+"' and table_name='custom_fields' ORDER BY ordinal_position").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allColumns;
	}

	@Override
	public String saveCustomFiels(String schema,String table, String column, String datatype, String length) {
		
		int count=Integer.parseInt(getCustomEntityManager("pgsql").createNativeQuery("SELECT count(*) from "+schema+".custom_fields WHERE table_name='"+table+"' and field_name='"+column+"'").getSingleResult().toString());
		if(count<1) {
			String message="Custom field created";
			String insert="INSERT INTO "+schema+".custom_fields(table_name,field_name,field_type,field_length)VALUES('"+table+"','"+column+"','"+datatype+"','"+length+"')";
			try {
				getCustomEntityManager("pgsql").createNativeQuery(insert).executeUpdate();
			} catch (Exception e) {
				message="Exception came while creating custom field " + e.getMessage();
				e.printStackTrace();
			}
			return message;
		}else {
			return column + " already exists in " + table;
		}
		
	}

	@Override
	public List<?> getTableData(String schema, String table) {
		List<?> tabledata=new ArrayList();
		String query="SELECT * FROM "+schema+".generate_query('"+schema+"','"+table+"')";
		try {
			String generated_query=(String) getCustomEntityManager("pgsql").createNativeQuery(query).getSingleResult();			
			tabledata=getCustomEntityManager("pgsql").createNativeQuery(generated_query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabledata;
	}

	@Override
	public String saveDataInTable(JSONObject json) {
		try {
			String schema=json.getString("schema");
			String table=json.getString("table");
			String key="(";
			String value="(";
			/**
			 * generating sequence
			 */
			String seq="SELECT nextval('"+schema+"."+table+"_seq')";
			int sequence=Integer.parseInt(getCustomEntityManager("pgsql").createNativeQuery(seq).getSingleResult().toString());
			/**
			 * creating insert query to save data in main table
			 */
			String query1="SELECT column_name FROM information_schema.columns WHERE table_schema='"+schema+"' and table_name='"+table+"' ORDER BY ordinal_position"; 
			List<?> columnNames=getCustomEntityManager("pgsql").createNativeQuery(query1).getResultList();
			for (Iterator iterator = columnNames.iterator(); iterator.hasNext();) {
				String columnName = String.valueOf(iterator.next());
				if(columnName.equalsIgnoreCase("id")) {
					key+="id,";
					value+=sequence+",";
				}else {
					key+=columnName+",";
					value+="'"+json.getString(columnName)+"',";					
				}
			}
			key=key.substring(0,key.length()-1)+")";
			value=value.substring(0,value.length()-1)+")";
			String insertQuery="INSERT INTO " + schema + "." + table + key + " VALUES " + value;
			System.out.println("insertQuery = "+insertQuery);
			/**
			 * creating insert query to save data in custom filed value
			 */
			String insertCustom="INSERT INTO "+schema+".custom_field_value(custom_field_id,parent_record_id,field_value)VALUES";
			String query2="SELECT id,field_name FROM "+schema+".custom_fields WHERE table_name='"+table+"' ORDER BY id";
			List<?> data=getCustomEntityManager("pgsql").createNativeQuery(query2).getResultList();
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				insertCustom+=("('"+object[0]+"','"+sequence+"','"+json.getString(String.valueOf(object[1]))+"'),");
			}
			insertCustom=insertCustom.substring(0,insertCustom.length()-1);
			System.out.println("insertCustom = " + insertCustom);
			/**
			 * saving data in database
			 */
			getCustomEntityManager("pgsql").createNativeQuery(insertQuery).executeUpdate();
			getCustomEntityManager("pgsql").createNativeQuery(insertCustom).executeUpdate();
			return "Data Saved";
		}
		catch (JSONException e) {
			e.printStackTrace();
			return "Data Not Saved : " + e.getMessage();
		}
	}

}