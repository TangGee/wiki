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
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgSolid;


/**
 * @author azabala
 */
public class DwgSolidReader2004 extends AbstractDwg2004Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgSolid))
			throw new RuntimeException("ArcReader 2004 solo puede leer DwgSolid");
		DwgSolid sol = (DwgSolid) dwgObj;
		int bitPos = offset;

		ArrayList v;

		bitPos = readObjectHeader(data, bitPos, sol);

		v = DwgUtil.testBit(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		boolean flag = ((Boolean)v.get(1)).booleanValue();
	    double val;
		if (flag) {
			val=0.0;
		} else {
			v = DwgUtil.getBitDouble(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			val = ((Double)v.get(1)).doubleValue();
		}
		sol.setThickness(val);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		val = ((Double)v.get(1)).doubleValue();
		sol.setElevation(val);

		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double y = ((Double)v.get(1)).doubleValue();
		double[] coord = new double[]{x, y, val};
		sol.setCorner1(coord);


		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		y = ((Double)v.get(1)).doubleValue();
		coord = new double[]{x, y, val};
		sol.setCorner2(coord);


		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		y = ((Double)v.get(1)).doubleValue();
		coord = new double[]{x, y, val};
		sol.setCorner3(coord);


		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getRawDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		y = ((Double)v.get(1)).doubleValue();
		coord = new double[]{x, y, val};
		sol.setCorner4(coord);


		v = DwgUtil.testBit(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		flag = ((Boolean)v.get(1)).booleanValue();
		double z;
	    if (flag) {
			 x = y = 0.0;
			 z = 1.0;
		} else {
			v = DwgUtil.getBitDouble(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			x = ((Double)v.get(1)).doubleValue();
			v = DwgUtil.getBitDouble(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			y = ((Double)v.get(1)).doubleValue();
			v = DwgUtil.getBitDouble(data, bitPos);
			bitPos = ((Integer)v.get(0)).intValue();
			z = ((Double)v.get(1)).doubleValue();
		}
		coord = new double[]{x, y, z};
		sol.setExtrusion(coord);

		sol.inserta();

		bitPos = readObjectTailer(data, bitPos, sol);
	}
}
