package ca.algomau.zprajapati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.algomau.zprajapati.Dish;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DishRepository {
    private NamedParameterJdbcTemplate jdbc;

    // Constructor with NamedParameterJdbcTemplate injection
    public DishRepository(NamedParameterJdbcTemplate jdbc) {
        super();
        this.jdbc = jdbc;
    }

    // Get all dishes
    public ArrayList<Dish> getDishes() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM dish";  // Replace with your actual table name
        ArrayList<Dish> dishes = (ArrayList<Dish>) jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Dish.class));
        return dishes;
    }

    // Get a specific dish by ID
    public Dish getDishById(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM dish WHERE id = :id";  // Replace with your actual table name
        parameters.addValue("id", id);
        List<Dish> dishes = jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Dish.class));
        if (!dishes.isEmpty()) {
            return dishes.get(0);
        } else {
            return null;  // Return null if no dish is found
        }
    }

    // Add a new dish
    public void addDish(Dish dish) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO dish (name, description, price, image_path) "
                     + "VALUES (:name, :description, :price, :imagePath)";
        parameters.addValue("name", dish.getName());
        parameters.addValue("description", dish.getDescription());
        parameters.addValue("price", dish.getPrice());
        parameters.addValue("imagePath", dish.getImagePath());
        jdbc.update(query, parameters);  // Executes the insert query
    }

    // Edit an existing dish
    public void editDish(Dish dish) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "UPDATE dish SET name = :name, description = :description, "
                     + "price = :price, image_path = :imagePath WHERE id = :id";
        parameters.addValue("id", dish.getId());
        parameters.addValue("name", dish.getName());
        parameters.addValue("description", dish.getDescription());
        parameters.addValue("price", dish.getPrice());
        parameters.addValue("imagePath", dish.getImagePath());
        jdbc.update(query, parameters);  // Executes the update query
    }

    // Delete a dish by its ID
    public void deleteDish(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "DELETE FROM dish WHERE id = :id";  // Replace with your actual table name
        parameters.addValue("id", id);
        jdbc.update(query, parameters);  // Executes the delete query
    }
}
