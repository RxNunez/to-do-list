package dao;

import models.Category;
import models.Task;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao categoryDao; //ignore me for now. We'll create this soon.
    private Sql2oTaskDao taskDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2oCategoryDao(sql2o); //ignore me for now
        taskDao = new Sql2oTaskDao(sql2o);
        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingCourseSetsId() throws Exception {
        Category category = new Category ("Home");
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId()); //how does this work?
    }
    @Test
    public void existingCategorysCanBeFoundById() throws Exception {
        Category category = new Category ("Home");
        categoryDao.add(category); //add to dao (takes care of saving)
        Category foundCategory = categoryDao.findById(category.getId()); //retrieve
        assertEquals(category, foundCategory); //should be the same
    }

    @Test
    public void allCategorysAreFound_true() throws Exception {
        Category category = new Category("Home");
        categoryDao.add(category);
        assertEquals(1,categoryDao.getAll().size());
    }
    @Test
    public void noCategorysReturnsEmptyList() throws Exception {
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryContent() throws Exception {
        String initialName = "Home";
        Category category = new Category (initialName);
        categoryDao.add(category);

        categoryDao.update(category.getId(),"Work");
        Category updatedCategory = categoryDao.findById(category.getId()); //why do I need to refind this?
        assertNotEquals(initialName, updatedCategory.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectCategory() throws Exception {
        Category category = new Category ("Home");
        categoryDao.add(category);
        categoryDao.deleteById(category.getId());
        assertEquals(0, categoryDao.getAll().size());
    }
    @Test
    public void clearAllClearsAll() throws Exception {
        Category category = new Category ("Home");
        Category otherCategory = new Category("Work");
        categoryDao.add(category);
        categoryDao.add(otherCategory);
        int daoSize = categoryDao.getAll().size();
        categoryDao.clearAllCategories();
        assertTrue(daoSize > 0 && daoSize > categoryDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }
    @Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Category category = new Category ("Home");
        categoryDao.add(category);
        int categoryId = category.getId();
        Task newTask = new Task("mow the lawn", categoryId);
        Task otherTask = new Task("pull weeds", categoryId);
        Task thirdTask = new Task("trim hedge", categoryId);
        taskDao.add(newTask);
        taskDao.add(otherTask); //we are not adding task 3 so we can test things precisely.


        assertTrue(categoryDao.getAllTasksByCategory(categoryId).size() == 2);
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newTask));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherTask));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdTask)); //things are accurate!
    }
}