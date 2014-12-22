/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v15;

import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;

/**
 * @author alzabord
 */
public class DwgLayerControlReader15 extends AbstractDwg15Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) {
		//TODO Si no lo leemos, mejor ni considerarlo
		//Ver la especificaci�n de este objeto
	}

}
