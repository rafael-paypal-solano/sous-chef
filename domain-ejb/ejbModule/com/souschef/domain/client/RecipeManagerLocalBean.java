package com.souschef.domain.client;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless(name = "RecipeManagerLocalBean", mappedName = "RecipeManagerLocalBean")
@Local(RecipeManagerLocal.class)
public class RecipeManagerLocalBean  extends RecipeManagerImpl{

}
