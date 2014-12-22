/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v15;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgEndblk;

/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DwgEndBlkReader15 extends AbstractDwg15Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgEndblk))
			throw new RuntimeException("ArcReader 15 solo puede leer DwgEndBlk");
		DwgEndblk end = (DwgEndblk) dwgObj;
		int bitPos = offset;
		bitPos = headTailReader.readObjectHeader(data, bitPos, end);
		bitPos = headTailReader.readObjectTailer(data, bitPos, end);
	}
}
