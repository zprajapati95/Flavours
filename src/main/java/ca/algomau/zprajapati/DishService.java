package ca.algomau.zprajapati;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DishService {
	
    private final DishRepository dishRepository;
	
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    // Add dish to the database
    public void addDishToDatabase(Dish dish) {
        dishRepository.addDish(dish);
        System.out.println("Dish added to database: " + dish);
    }

 // Get all dishes from the database
    public List<Dish> getDishesFromDatabase() {
    	 List<Dish> dishes = dishRepository.getDishes();
    	    System.out.println("Fetched dishes from database: " + dishes);  // Log the fetched dishes
    	    return dishes;
    }

	public List<Dish> getDishes() {
		// TODO Auto-generated method stub
		return getDishesFromDatabase(); 
	}
 
}

