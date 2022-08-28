package com.revature.daos;

import java.util.List;
import com.revature.exceptions.CarAlreadyExistsException;

public interface ORM {
	
	//See all cars in inventory table
	<T> List<T> getAll(Object object);
		
	//Enter new car into inventory 
	public<T> Object insertItem(Object object) throws CarAlreadyExistsException;
	
	//pulls table and finds id
	public<T> Object updateItem(Object object);
		
	//pulls table, finds id, removes car
	public<T> Object removeItem(Object object);
	
	public<T> Object findById(Object object);
	
	
	
	
	

}
