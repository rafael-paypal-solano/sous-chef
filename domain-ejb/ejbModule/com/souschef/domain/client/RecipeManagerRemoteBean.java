package com.souschef.domain.client;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(name = "RecipeManagerRemoteBean", mappedName = "RecipeManagerRemoteBean")
@Remote(RecipeManagerRemote.class)
public class RecipeManagerRemoteBean  extends RecipeManagerImpl{

}
