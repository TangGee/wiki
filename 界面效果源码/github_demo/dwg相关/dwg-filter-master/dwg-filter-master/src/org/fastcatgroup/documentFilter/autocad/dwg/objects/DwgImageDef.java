/*
 * Created on 02-feb-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.objects;

import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;

/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DwgImageDef extends DwgObject {

	/**
	 * @param index
	 */
	public DwgImageDef(int index) {
		super(index);
		// TODO Auto-generated constructor stub
	}
	public Object clone(){
		DwgImageDef obj = new DwgImageDef(index);
		this.fill(obj);
		return obj;
	}
	
	protected void fill(DwgObject obj){
		super.fill(obj);
		//DwgImageDef myObj = (DwgImageDef)obj;

	}

}
