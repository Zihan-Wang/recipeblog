package guru.springframework.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImp implements RecipeService{

	public RecipeServiceImp(RecipeRepository recipeReporitory) {
		this.recipeRepository = recipeReporitory;
	}
	private final RecipeRepository recipeRepository;
	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I am in the services");
		
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

}
