/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v15;

import java.util.ArrayList;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgBlock;


/**
 * @author alzabord
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DwgBlockReader15 extends AbstractDwg15Reader{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[],
	 *      int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException{
			if(! (dwgObj instanceof DwgBlock))
				throw new RuntimeException("DwgBlockReader15 solo puede leer DwgBlock");
			DwgBlock blk = (DwgBlock) dwgObj;
			int bitPos = offset;
			bitPos = headTailReader.readObjectHeader(data, bitPos, dwgObj);
			ArrayList v = DwgUtil.getTextString(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			String text = (String)v.get(1);
			blk.setName(text);
			bitPos = headTailReader.readObjectTailer(data, bitPos, dwgObj);
	}
}
