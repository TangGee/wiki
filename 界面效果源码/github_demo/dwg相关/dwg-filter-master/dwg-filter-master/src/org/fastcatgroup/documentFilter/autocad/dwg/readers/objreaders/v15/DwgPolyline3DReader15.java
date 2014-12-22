/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v15;

import java.util.ArrayList;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgHandleReference;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgPolyline3D;


/**
 * @author alzabord
 */
public class DwgPolyline3DReader15 extends AbstractDwg15Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgPolyline3D))
			throw new RuntimeException("ArcReader 15 solo puede leer DwgPolyline3D");
		DwgPolyline3D ln = (DwgPolyline3D) dwgObj;
		int bitPos = offset;
		bitPos = headTailReader.readObjectHeader(data, bitPos, ln);
		ArrayList v = DwgUtil.getRawChar(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		int sflags = ((Integer)v.get(1)).intValue();
		ln.setSplineFlags(sflags);
		v = DwgUtil.getRawChar(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		int cflags = ((Integer)v.get(1)).intValue();
		ln.setClosedFlags(cflags);
		bitPos = headTailReader.readObjectTailer(data, bitPos, ln);
		
		DwgHandleReference hr = new DwgHandleReference();
		bitPos = hr.read(data, bitPos);
		ln.setFirstVertexHandle(hr);

		hr = new DwgHandleReference();
		bitPos = hr.read(data, bitPos);
		ln.setLastVertexHandle(hr);

		hr = new DwgHandleReference();
		bitPos = hr.read(data, bitPos);
	    ln.setSeqendHandle(hr);
	}
}
