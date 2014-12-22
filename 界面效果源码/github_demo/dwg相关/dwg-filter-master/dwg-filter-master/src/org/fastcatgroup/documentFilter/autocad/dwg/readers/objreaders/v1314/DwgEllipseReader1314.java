/*
 * Created on 25-ene-2007 by azabala
 *
 */
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v1314;

import java.util.List;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgArc;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgEllipse;


/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DwgEllipseReader1314 extends AbstractDwg1314Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgEllipse))
	    	throw new RuntimeException("ArcReader 14 solo puede leer DwgEllipse");
		DwgEllipse ell = (DwgEllipse) dwgObj;
		int bitPos = offset;
		bitPos = headTailReader.readObjectHeader(data, bitPos, ell);
		
		List val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double x = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double y = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double z = ((Double) val.get(1)).doubleValue();
		ell.setCenter(new double[]{x, y, z});
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		x = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		y = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		z = ((Double) val.get(1)).doubleValue();
		ell.setMajorAxisVector(new double[]{x, y, z});
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		x = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		y = ((Double) val.get(1)).doubleValue();
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		z = ((Double) val.get(1)).doubleValue();
		ell.setExtrusion(new double[]{x, y, z});
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double axisRatio = ((Double) val.get(1)).doubleValue();
		ell.setAxisRatio(axisRatio);
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double startAngle = ((Double) val.get(1)).doubleValue();
		ell.setInitAngle(startAngle);
		
		val = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		double endAngle = ((Double) val.get(1)).doubleValue();
		ell.setEndAngle(endAngle);
		
		bitPos = headTailReader.readObjectTailer(data, bitPos, ell);
	}
}
