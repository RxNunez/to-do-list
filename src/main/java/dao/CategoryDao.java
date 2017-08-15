package dao;
import models.Category;
import models.Task;

import java.util.List;

/**
 * Created by Guest on 8/14/17.
 */
public interface CategoryDao {

    //create
    void add (Category category);

    //read
    List<Category> getAll();
    List<Task> getAllTasksByCategory(int categoryId);

    Category findById(int id);

    //update
    void update(int id, String name);

    //delete
//    void deleteById(int id);
    void deleteCategoryById(int categoryId);
    void clearAllCategories();

}