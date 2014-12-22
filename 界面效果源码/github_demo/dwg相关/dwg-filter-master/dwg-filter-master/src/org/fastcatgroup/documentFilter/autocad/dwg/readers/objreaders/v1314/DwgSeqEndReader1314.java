/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v1314;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgSeqend;

/**
 * @author alzabord
 */
public class DwgSeqEndReader1314 extends AbstractDwg1314Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgSeqend))
	    	throw new RuntimeException("ArcReader 14 solo puede leer DwgSeqEnd");

		int bitPos = offset;
		bitPos = headTailReader.readObjectHeader(data, offset, dwgObj );
		bitPos = headTailReader.readObjectTailer(data, bitPos, dwgObj);
	}
}
