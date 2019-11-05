package com.example.demo.ServiceImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Service.GenericService;
/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 */
@Transactional(propagation = Propagation.REQUIRED)
public abstract class GenericServiceImpl<T> implements GenericService<T> {

	@PersistenceContext(unitName="pgsql")
	protected EntityManager pgsql;

	@PersistenceContext(unitName="mysql")
	protected EntityManager mysql;

	@PersistenceContext(unitName="odba")
	protected EntityManager odba;

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl(){
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	/**
	 * Perform an initial save of a previously unsaved Entity. All subsequent
	 * persist actions of this entity should use the #update() method. This
	 * operation must be performed within the a database transaction context for
	 * the entity's data to be permanently saved to the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity to persist
	 * @return Entity instance
	 * @since 0.1
	 */
	@Override
	public T save(final T t) {		
		try {
			this.pgsql.persist(t);
			return t;
		} 
		catch (RuntimeException re) {
			throw re;
		}
	}
	@Override
	public void flush(){
		try {
			this.pgsql.clear();
		} 
		catch (RuntimeException re) {
			throw re;
		}
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public T customSave(final T t) {		
		Session session=pgsql.unwrap(Session.class);
		Transaction tx=session.beginTransaction();
		try {
			session.save(t);
			tx.commit();
			System.out.println("---saved-----");
			return t;

		} catch (RuntimeException re) {
			tx.rollback();
			System.out.println("---Error while saved-----");
			throw re;
		}

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public T customsavemdas(final T t) {		
		Session session=pgsql.unwrap(Session.class);
		Transaction tx=session.beginTransaction();
		try {
			session.persist(t);
			tx.commit();
			System.out.println("---saved-----");
			return t;

		} catch (RuntimeException re) {
			tx.rollback();
			System.out.println("---Error while saved-----");
			throw re;
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public T customupdatemdas(final T t) {		
		Session session=pgsql.unwrap(Session.class);
		Transaction tx=session.beginTransaction();
		try {
			session.merge(t);
			tx.commit();
			System.out.println("---updated-----");
			session.flush();
			session.clear();
			return t;

		} catch (RuntimeException re) {
			tx.rollback();
			session.flush();
			session.clear();
			System.out.println("---Error while updation-----");
			throw re;
		}

	}

	/**
	 * Delete a persistent Entity. This operation must be performed within the a
	 * database transaction context for the entity's data to be permanently
	 * deleted from the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @param id
	 *            Entity property
	 * @since 0.1
	 */
	@Override
	public void delete(final Object id) {
		try {
			this.pgsql.remove(this.pgsql.getReference(type, id));
		} 
		catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * Persist a previously saved Entity and return it or a copy of it to the
	 * sender. A copy of the Users entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = UsersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity instance to update
	 * @return Updated entity instance
	 * @since 0.1
	 */
	@Override
	public T update(final T t) {
		try {
			T result = this.pgsql.merge(t);
			return result;
		} 
		catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * Find all Entity instances with a specific property value.

	 * @param id
	 *            property to query
	 * @return T found by query
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public T find(final Object id) {		
		try {
			return (T) this.pgsql.find(type, id);
		} 
		catch (RuntimeException re) {
			throw re;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public String getCheckConstraints(String tableName,String constraintName) {		
		String res= (String) pgsql.createNamedQuery("constraint.getChecks").setParameter("tableName", tableName).setParameter("constraintName", constraintName).getSingleResult();
		String[] splitRes=res.split("OR");
		int subLength = 0;
		String[] test = null;
		if(splitRes.length > 0)
		{
			test = splitRes[0].split("'");
			subLength = test[0].length()+1;
		}
		String finalRes="";
		String result = "";
		for(int i=0;i<splitRes.length;i++)
		{
			finalRes=splitRes[i].trim();

			result = result + (finalRes.substring(subLength, finalRes.length()-1)) + ",";

		}
		result = result.substring(0,result.length()-1);
		return result;
	}


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getCheckConstraints(String tableName,String constraintName,HttpServletRequest request)
	{		
		int j=1;
		int oid=getOid(tableName);
		String sql="select c.consrc from Pg_Constraint_Entity c  where conrelid ='"+oid+"' and conname='"+constraintName.toLowerCase()+"'";
		String  s=(String)pgsql.createQuery(sql).getSingleResult();
		String s1=s.replace("(", "");
		String[] res1=s1.split(" OR ");
		String[] res2=null;
		List<String> list=new ArrayList<String>();
		for (int i = 0; i < res1.length; i++) 
		{
			res2=res1[i].split("'");
			list.add(res2[j]);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public int getOid(String tableName)
	{
		String sql="select p.oid from Pg_Class_Entity p where p.relname='"+tableName.toLowerCase()+"'";
		int  s=(Integer)pgsql.createQuery(sql).getSingleResult();
		return s;		
	}

	public void getRecentPath(String recentPath,HttpServletRequest request) {
		HttpSession session= request.getSession();
		session.setAttribute("path", recentPath);

	}


	public Date getDate2(String dateString) {
		DateFormat formatter = null;
		Date convertedDate = null;
		try{
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			convertedDate =  formatter.parse(dateString);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return convertedDate;
	}

	public String getDate3(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		String datevalue=sdf.format(date);		 
		return datevalue;
	}

	public String getCurrentMonthyear() {
		int calenderMonth=Calendar.getInstance().get(Calendar.MONTH);//By default it will show previous month only
		calenderMonth=calenderMonth+1;//By default it will show previous month only so increment to get current month
		int calenderYear=Calendar.getInstance().get(Calendar.YEAR);
		String monthyear="";

		if(calenderMonth<10)
			monthyear=calenderYear+"0"+calenderMonth;
		else
			monthyear=calenderYear+""+calenderMonth;
		return monthyear;
	}

	public String getCurrentYearPreviousMonth() {
		int calenderMonth=Calendar.getInstance().get(Calendar.MONTH);//By default it will show previous month only		        
		int calenderYear=Calendar.getInstance().get(Calendar.YEAR);
		if(calenderMonth==0)
		{
			calenderMonth=12;
			calenderYear=calenderYear-1;
		}
		String monthyear="";			
		if(calenderMonth<10)
			monthyear=calenderYear+"0"+calenderMonth;
		else
			monthyear=calenderYear+""+calenderMonth;
		return monthyear;
	}

	public String getBeforePreviousMonthYear(String monthYear)
	{
		int Month=Integer.parseInt(monthYear.substring(4,monthYear.length()));	        
		int Year=Integer.parseInt(monthYear.substring(0,4));
		if(Month==02)//check month if 1 means get previous year
		{
			Month=12;
			Year=Year-1;
		}
		else if(Month==01)
		{
			Month=11;
			Year=Year-1;
		}
		else 
		{
			Month=Month-2;
		}
		String monthyear="";
		if(Month<10)
			monthyear=Year+"0"+Month;
		else 
			monthyear=Year+""+Month;			

		return monthyear;
	}

	public int UpdateNoMRDflag(String accno, int billmonth, String mrname) {
		return 0;
	}


	@Override
	public EntityManager getCustomEntityManager(String schemaName) {
		switch(schemaName){
			case "pgsql": 
				return pgsql;
			case "odba": 
				return odba;
			case "mysql": 
				return mysql;
		}
		return null; 
	}

	@Override
	public T customsaveBySchema(final T t,String Schema) {
		Session session = getCustomEntityManager(Schema).unwrap(Session.class);
		try {
			session.persist(t);
			session.flush();
			session.clear();
			return t;

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public T customupdateBySchema(final T t,String Schema) {
		Session session = getCustomEntityManager(Schema).unwrap(Session.class);
		try {
			session.merge(t);
			session.flush();
			session.clear();
			return t;

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public T customfind(final Object id) {		
		try {
			return (T) getCustomEntityManager("pgsql").find(type, id);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void customdelete(final Object id) {		
		try {
			this.pgsql.remove(this.pgsql.getReference(type, id));
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public int customExecuteUpdate(final javax.persistence.Query query) {		

		try {
			int i=query.executeUpdate();
			return i;
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public T customUpdate(final T t) {		
		Session session=pgsql.unwrap(Session.class);
		Transaction tx=session.beginTransaction();
		try {
			this.pgsql.merge(t);
			tx.commit();
			System.out.println("---saved-----");
			return t;

		} catch (RuntimeException re) {
			tx.rollback();
			System.out.println("---Error while saved-----");
			throw re;
		}

	}

}