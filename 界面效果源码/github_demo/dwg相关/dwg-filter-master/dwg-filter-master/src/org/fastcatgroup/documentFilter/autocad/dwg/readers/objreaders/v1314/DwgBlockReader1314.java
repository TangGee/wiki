/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v1314;

import java.util.List;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgBlock;


/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DwgBlockReader1314 extends AbstractDwg1314Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		 if(! (dwgObj instanceof DwgBlock))
		    	throw new RuntimeException("ArcReader 14 solo puede leer DwgBlock");
		 DwgBlock blk = (DwgBlock) dwgObj;
		 int bitPos = offset;
		 bitPos = headTailReader.readObjectHeader(data, offset, blk );
		 
		 List val = DwgUtil.getTextString(data, bitPos);
		 bitPos = ((Integer) val.get(0)).intValue();
		 String name = (String) val.get(1);
		 blk.setName(name);
		 
		 bitPos = headTailReader.readObjectTailer(data, bitPos, blk);
	}

}
