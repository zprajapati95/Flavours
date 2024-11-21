package ca.algomau.zprajapati;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DishRepository {
	private List<Dish> dishes = new ArrayList<>(

	);

	protected NamedParameterJdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public DishRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void addDish(Dish dish) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO dishes (name, price, description,category) "
				+ "VALUES (:name, :price, :description,:category)";
		parameters.addValue("name", dish.getName());
		parameters.addValue("price", dish.getPrice());
		parameters.addValue("description", dish.getDescription());
		parameters.addValue("category", dish.getCategory());

		jdbc.update(query, parameters);
		System.out.println("Dish added to database: " + dish); // Ensure this prints when a dish is added
	}

	// Get all dishes
	public List<Dish> getDishes() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM dishes";
		ArrayList<Dish> Dish = (ArrayList<Dish>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Dish>(Dish.class));
		return Dish;
	}

	 
	 public void deleteById(Long id) {
		    try {
		        String sql = "DELETE FROM dishes WHERE id = ?";
		        jdbcTemplate.update(sql, id);
		        System.out.println("Dish deleted with ID: " + id); // Add logging here
		    } catch (Exception e) {
		        System.out.println("Error deleting dish with ID: " + id);
		        e.printStackTrace(); // Print the exception to the console
		    }
		}



	// Get a specific dish by ID
	public Dish getDishById(int id) {
		String query = "SELECT * FROM dishes WHERE id = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		List<Dish> dishes = jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Dish.class));
		return dishes.isEmpty() ? null : dishes.get(0);
	}

	public void editDish(Dish dish) {
	    // Prepare the parameters for the SQL query
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    String query = "UPDATE dishes SET name = :name, price = :price, description = :description, category = :category WHERE id = :id";
	    
	    // Bind parameters
	    parameters.addValue("id", dish.getId());
	    parameters.addValue("name", dish.getName());
	    parameters.addValue("price", dish.getPrice());
	    parameters.addValue("description", dish.getDescription());
	    parameters.addValue("category", dish.getCategory());
	    
	    // Execute the update query
	    jdbc.update(query, parameters);

	    System.out.println("Dish updated: " + dish);  // Optional log message
	}

	public List<Dish> getDishesByCategory(String category) {
	    String sql = "SELECT * FROM dishes WHERE category = ?";
	    return jdbcTemplate.query(sql, new DishRowMapper(), category);
	}


}
