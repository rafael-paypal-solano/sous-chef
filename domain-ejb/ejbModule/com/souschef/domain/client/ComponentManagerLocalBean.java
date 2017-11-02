package com.souschef.domain.client;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless(name = "ComponentManagerLocalBean", mappedName = "ComponentManagerLocalBean")
@Local(ComponentManagerLocal.class)

public class ComponentManagerLocalBean  extends ComponentManagerImpl implements ComponentManagerLocal{


}
