/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Info {

	static List<String> idiomaList = new ArrayList<String>();
	static{
		idiomaList.add("Português");
		idiomaList.add("Inglês");
		idiomaList.add("Espanhol");
			
		idiomaList = Collections.unmodifiableList(idiomaList);
	}
	
	public static List<String> getIdiomaList() {
		return idiomaList;
	}
}
