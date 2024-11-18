package ca.algomau.zprajapati;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DishRepository {

	protected NamedParameterJdbcTemplate jdbc;

    @Autowired
    public DishRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public void addDish(Dish dish) {
    	MapSqlParameterSource parameters = new MapSqlParameterSource(); 
        String query = "INSERT INTO dish (name, price, description, imagePath, category) "
                     + "VALUES (:name, :price, :description, :imagePath, :category)";
        		parameters.addValue("name", dish.getName());
        		parameters.addValue("price", dish.getPrice());
        		parameters.addValue("description", dish.getDescription());
        		parameters.addValue("imagePath", dish.getImagePath());
        		parameters.addValue("category", dish.getCategory());
               
        jdbc.update(query, parameters);
          
    }
    // Get all dishes
    public List<Dish> getDishes() {
    	
        String query = "SELECT * FROM dish";
 
        return jdbc.query(query, new BeanPropertyRowMapper<>(Dish.class));
    }

    // Get a specific dish by ID
    public Dish getDishById(long id) {
        String query = "SELECT * FROM dish WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        List<Dish> dishes = jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Dish.class));
        return dishes.isEmpty() ? null : dishes.get(0);
    }
  

   
}
