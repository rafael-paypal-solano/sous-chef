package com.souschef.domain.model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.*;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.ComponentCategory;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;
import com.souschef.domain.data.model.Unit;

@RunWith(Suite.class) //718-065-089
@SuiteClasses(
	{		
		ComponentManagerTest.class,
		RecipeManagerTest.class
	}
)
public class DomainModelTestSuite  {
	public static String PERSISTENCE_UNIT_NAME="domain-model-test";
	public static ComponentCategory[] componentCategories = {
		new ComponentCategory("meat", "Meat"),
		new ComponentCategory("poultry","Poultry"),
		new ComponentCategory("sausages","Sausages"),
		new ComponentCategory("fruit","Fruit"),
		new ComponentCategory("vegetables","Vegetables"),
		new ComponentCategory("dairy","Dairy"),		
		new ComponentCategory("fish","Fish"),
		new ComponentCategory("seafood","Seafood"),
		new ComponentCategory("juice","Juice"),
		new ComponentCategory("flour-grain","Flour and Grain"),
		new ComponentCategory("condiment","Condiment")
	};
	
	public static Map<String, Component[]> componentMap = new HashMap<String, Component[]>();

	private static void createComponentsData() {		
		componentMap.put(
			"Meat",
			new Component[]{
				new Component("Chuck", Unit.KILOGRAM, 12, 8),
				new Component("Rib", Unit.KILOGRAM, 15, 1),
				new Component("Short Loin", Unit.KILOGRAM, 12, 9),
				new Component("Sir Loin", Unit.KILOGRAM, 10, 12),
				new Component("Round", Unit.KILOGRAM, 12, 3)
			}				
		);

		componentMap.put(
			"Poultry",
			new Component[]{
				new Component("Wing", Unit.KILOGRAM, 12, 7),
				new Component("Breast", Unit.KILOGRAM, 15, 6),
				new Component("Thigh", Unit.KILOGRAM, 12, 5),
			}				
		);

		componentMap.put(
			"Sausages",
			new Component[]{
				new Component("Bratwurst", Unit.KILOGRAM, 9, 7),
				new Component("Chorizo", Unit.KILOGRAM, 8, 6)
			}				
		);
			
		componentMap.put(
			"Vegetables",
			new Component[]{
				new Component("Onion", Unit.KILOGRAM, 9, 7),
				new Component("Garlic", Unit.KILOGRAM, 8, 6),
				new Component("Potato", Unit.KILOGRAM, 8, 6),
				new Component("Parsley", Unit.KILOGRAM, 8, 6),
				new Component("Carrot", Unit.KILOGRAM, 8, 6),
				new Component("Jalapeño", Unit.KILOGRAM, 8, 6)
			}				
		);
			
			
		componentMap.put(
			"Dairy",
			new Component[]{
				new Component("Whole Milk", Unit.KILOGRAM, 9, 7),
				new Component("Yogurt", Unit.KILOGRAM, 8, 6),
				new Component("Cheddar Cheese", Unit.KILOGRAM, 8, 6),
				new Component("Blue cheese", Unit.KILOGRAM, 8, 6),
				new Component("Sour Cream", Unit.KILOGRAM, 8, 6)
			}				
		);
			
		componentMap.put(
			"Fish",
			new Component[]{
				new Component("Sardine", Unit.KILOGRAM, 9, 7),
				new Component("Salmon", Unit.KILOGRAM, 8, 6)
			}				
		);
		
		componentMap.put("Seafood",
			new Component[] {
				new Component("Shrimp", Unit.KILOGRAM, 8, 6),
				new Component("Octopus", Unit.KILOGRAM, 8, 6),
				new Component("Crab", Unit.KILOGRAM, 8, 6)					
			}
		);
		componentMap.put(
			"Juice",
			new Component[]{
				new Component("Clamato", Unit.KILOGRAM, 9, 7),
				new Component("V8", Unit.KILOGRAM, 8, 6),
				new Component("Snapple", Unit.KILOGRAM, 8, 6)
			}				
		);
			
		componentMap.put(
			"Flour and Grain",
			new Component[]{
				new Component("Rice", Unit.KILOGRAM, 9, 7),
				new Component("Wheat", Unit.KILOGRAM, 8, 6),
				new Component("Barley", Unit.KILOGRAM, 8, 6)
			}				
		);
		
			
		componentMap.put(
			"Condiment",
			new Component[]{
				new Component("Salt", Unit.KILOGRAM, 9, 7),
				new Component("Sugar", Unit.KILOGRAM, 8, 6),
				new Component("Ground Pepper", Unit.KILOGRAM, 8, 6),
				new Component("Coriander", Unit.KILOGRAM, 8, 6),
				new Component("Cinnamon", Unit.KILOGRAM, 8, 6),
				new Component("Saffron", Unit.KILOGRAM, 8, 6),
				new Component("Teriyaki Sausage", Unit.LITRE, 8, 6)
			}				
		);		
		
		componentMap.put(
				"Fruit",
				new Component[]{
					new Component("Banana", Unit.KILOGRAM, 9, 7),
					new Component("Apple", Unit.KILOGRAM, 8, 6),
					new Component("Orange", Unit.KILOGRAM, 8, 6),
					new Component("Strawberry", Unit.KILOGRAM, 8, 6)
				}				
			);
	}
	
	public static Recipe[] recipes;
	
	public static void createRecypes() throws IOException {
		class IngredientList extends ArrayList<Ingredient> {

			private static final long serialVersionUID = 4435788361663906076L;

			public IngredientList append(Ingredient e) {
				super.add(e);
				return this;
			}
			
		}
		recipes = new Recipe[] {
			new Recipe(
				"Roasted Ribs", 
				1, 
				new File("images/roasted-ribs.jpg"),
				(new IngredientList()).append(
					new Ingredient(2, new Component("Salt"), Unit.GRAM)
				).append(
					new Ingredient(1, new Component("Rib"), Unit.KILOGRAM)
				).append(
					new Ingredient(3, new Component("Ground Pepper"), Unit.GRAM)
				).append(
					new Ingredient(6, new Component("Garlic"), Unit.GRAM)
				).append(
					new Ingredient(0.25, new Component("Teriyaki Sausage"), Unit.LITRE)
				)
								
			)
		};
	}
    @BeforeClass
    public static void beforeClass() throws IOException {
    	createComponentsData();  
    	createRecypes();
    }
	
    
	public static void clearDatabase() throws ClassNotFoundException, SQLException {
		Connection connection;
		Statement statement;
		Class.forName(System.getProperty("database.driver"));
		
		connection = DriverManager.getConnection(
			System.getProperty("database.url"),
			System.getProperty("database.user"),
			System.getProperty("database.password")
		);
		statement = connection.createStatement();
		statement.addBatch("DELETE FROM COMPONENT");
		statement.addBatch("DELETE FROM COMPONENT_CATEGORY");
		statement.executeBatch();
		
		statement.close();
		connection.close();
	}
}
