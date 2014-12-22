/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v2004;

import java.util.ArrayList;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgHandleReference;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgBlock;


/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DwgBlockReader2004 extends AbstractDwg2004Reader{

	/*
	 * (non-Javadoc)
	 *
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[],
	 *      int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException{
			if(! (dwgObj instanceof DwgBlock))
				throw new RuntimeException("DwgBlockReader2004 solo puede leer DwgBlock");
			DwgBlock blk = (DwgBlock) dwgObj;
			int bitPos = offset;
			ArrayList v;

			bitPos = readObjectHeader(data, bitPos, dwgObj);

			v = DwgUtil.getTextString(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			String text = (String)v.get(1);
			blk.setName(text);


			bitPos = readObjectTailer(data, bitPos, dwgObj);
			blk.inserta();
	}
}
