package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.exceptions.NotFoundException;

public class RecipeServiceImpTest {

	RecipeServiceImp recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImp(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}
	
    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
    
    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {

        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);
    }

	@Test
	public void getRecipes() throws Exception {
		Recipe recipe = new Recipe();
		
		Set<Recipe> recipesData = new HashSet<>();
		
		recipesData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
	}
	
    @Test
    public void testDeleteById() throws Exception {

        Long idToDelete = 2l;

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}