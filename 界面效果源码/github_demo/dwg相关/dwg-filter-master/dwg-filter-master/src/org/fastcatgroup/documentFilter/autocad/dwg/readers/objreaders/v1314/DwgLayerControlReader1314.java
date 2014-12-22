/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v1314;

import java.util.ArrayList;
import java.util.List;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgHandleReference;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgLayerControl;


/**
 * @author alzabord
 */
public class DwgLayerControlReader1314 extends AbstractDwg1314Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgLayerControl))
	    	throw new RuntimeException("ArcReader 14 solo puede leer DwgLayer");
		DwgLayerControl lyr = (DwgLayerControl) dwgObj;
		int bitPos = offset;
		
		List val = DwgUtil.getBitLong(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		int numReactors = ((Integer) val.get(1)).intValue();
		lyr.setNumReactors(numReactors);
		
		val = DwgUtil.getBitShort(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		int ne = ((Integer) val.get(1)).intValue();
		
		DwgHandleReference handle = new DwgHandleReference();
		bitPos = handle.read(data, bitPos);
		lyr.setNullHandle(handle);
		
		handle = new DwgHandleReference();
		bitPos = handle.read(data, bitPos);
		lyr.setXDicObjHandle(handle);
		
		if(ne > 0){
			List handles = new ArrayList();
			for (int i = 0; i < ne; i++){
				handle = new DwgHandleReference();
				bitPos = handle.read(data, bitPos);
				handles.add(handle);
			}
			lyr.setCode2Handles(handles);
		}
	}
}
