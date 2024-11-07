package ca.algomau.zprajapati;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DishService {
	
	private final List<Dish> dishes = new CopyOnWriteArrayList<>();


    // Method to add a dish
    public void addDish(Dish dish) {
        dishes.add(dish);
        System.out.println("Dish added: " + dish);
    }
    
    // Method to get all dishes
    public List<Dish> getDishes() {
    	System.out.println("Fetching dishes: " + dishes);
        return new ArrayList<>(dishes); // Return a copy to avoid external modification
    }
}

