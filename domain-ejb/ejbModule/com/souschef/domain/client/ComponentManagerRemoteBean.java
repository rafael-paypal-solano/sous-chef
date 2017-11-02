package com.souschef.domain.client;

import javax.ejb.Remote;
import javax.ejb.Stateless;
//

@Stateless(name = "ComponentManagerRemoteBean", mappedName = "ComponentManagerRemoteBean")
@Remote(ComponentManagerRemote.class)

public class ComponentManagerRemoteBean  extends ComponentManagerImpl implements ComponentManagerRemote{
	
	
	
}
