package com.example.demo.Service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;


/**
 * Interface for GenericServiceImpl.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 */
public interface GenericService<T> {
	
	T save(T t);
	void delete(Object id);
	T find(Object id);
	T update(T t);
	String getCheckConstraints(String tableName,String constraintName);
	
	//To get recent path
	void getRecentPath(String path,HttpServletRequest request);
	Date getDate2(String dateString);
	String getDate3(Date date);
	String getCurrentMonthyear();
	String getCurrentYearPreviousMonth();
	String getBeforePreviousMonthYear(String monthYear);

	int UpdateNoMRDflag(String accno, int billmonth, String mrname);
	
	T customSave(T t);

	void flush();

	EntityManager getCustomEntityManager(String schemaName);

	T customsaveBySchema(T t, String Schema);

	T customupdateBySchema(T t, String Schema);

	T customfind(Object id);
	T customsavemdas(T t);
	T customupdatemdas(T t);

	List<String> getCheckConstraints(String tableName, String constraintName,HttpServletRequest request);

	int getOid(String tableName);

	void customdelete(Object id);

	int customExecuteUpdate(javax.persistence.Query query);

	T customUpdate(T t);

}