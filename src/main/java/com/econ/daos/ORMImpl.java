package com.econ.daos;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import com.revature.annotations.Id;
import com.revature.utils.ConnectionUtil;

public class ORMImpl implements ORM {

	private ConnectionUtil con = ConnectionUtil.getConn(); // delete and use original

	public <T> List<T> getAll(Object obj) {
		Class<?> cls = obj.getClass();
		String sql = "SELECT * FROM " + cls.getSimpleName().toLowerCase();
		List<T> stock = new ArrayList<>();
		try (Connection conn = con.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet result;
			Field[] dfield = cls.getDeclaredFields();
			result = ps.executeQuery();
			while (result.next()) {
				T tvar = (T) obj.getClass().getConstructor().newInstance();

				for (Field field : dfield) {
					field.setAccessible(true);
					Annotation id = field.getAnnotation(Id.class);
					id = field.getAnnotation(Id.class);
					if (id == null) {
						int col = result.findColumn(field.getName());

						if (field.getType().getSimpleName().equals("String")) {
							field.set(tvar, result.getObject(col));
						} else if (field.getType().getSimpleName().equals("int")) {
							field.setInt(tvar, (int) result.getObject(col));
						} else if (field.getType().getSimpleName().equals("Double")) {
							double d = Double.parseDouble((String) result.getObject(col));
							field.set(tvar, d);
						} else if (field.getType().getSimpleName().equals("boolean")) {
							field.setBoolean(tvar, (boolean) result.getObject(col));
						}
					} else {
						int col = result.findColumn(field.getName());
						field.set(tvar, result.getObject(col));
					}
				}
				stock.add(tvar);
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException ex) {

			ex.printStackTrace();
		}
		return stock;
	}
	
	public <T> Object insertItem(Object obj) {
		StringJoiner com1 = new StringJoiner(", ");
		StringJoiner com2 = new StringJoiner("', '");		
		Class<?> cls = obj.getClass();
		Field[] dfield = cls.getDeclaredFields();
		String dbt = cls.getSimpleName().toLowerCase();
		Stream<Field> strArray = Arrays.stream(dfield);
		
		strArray.forEach(field -> {
			field.setAccessible(true);
			try {
				if (!field.get(obj).equals(null) & !field.isAnnotationPresent(Id.class)){
					com1.add(field.getName());
					com2.add(field.get(obj).toString());
				} else if (field.isAnnotationPresent(Id.class)){
				}
			} catch (Exception e) {
				//no nulls
			}
		});
		//
		String query = "INSERT INTO " + dbt + "(" + com1.toString() + ") VALUES ('" + com2.toString() + "')";

		try (Connection conn = con.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return obj;
		
	}

	public <T> Object updateItem(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Object removeItem(Object obj) {
		String nme = null;
		String nval = null;
		Class<?> cls = obj.getClass();
		Field[] dfield = cls.getDeclaredFields();
		String dbt = cls.getSimpleName().toLowerCase();
		for (Field field : dfield) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				nme = field.getName().toString();
				try {
					nval = field.get(obj).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}		
		String del = "DELETE FROM " + dbt + " WHERE " + nme + "='" + nval + "';";
		try (Connection conn = con.getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(del);
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obj = null;
		return obj;
	}

	@Override
	public <T> Object findById(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
