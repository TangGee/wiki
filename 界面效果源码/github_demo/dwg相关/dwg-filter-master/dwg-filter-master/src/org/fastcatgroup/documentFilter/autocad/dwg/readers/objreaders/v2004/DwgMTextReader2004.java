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
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgMText;


/**
 * @author alzabord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DwgMTextReader2004 extends AbstractDwg2004Reader{

	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.readers.IDwgObjectReader#readSpecificObj(int[], int, com.iver.cit.jdwglib.dwg.DwgObject)
	 */
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj) throws RuntimeException, CorruptedDwgEntityException {
		if(! (dwgObj instanceof DwgMText))
			throw new RuntimeException("ArcReader 2004 solo puede leer DwgMText");

		DwgMText txt = (DwgMText) dwgObj;
		int bitPos = offset;
		ArrayList v;


		bitPos =readObjectHeader(data, bitPos, txt);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double y = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double z = ((Double)v.get(1)).doubleValue();
		double[] coord = new double[]{x, y, z};
		txt.setInsertionPoint(coord);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		y = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		z = ((Double)v.get(1)).doubleValue();
		coord = new double[]{x, y, z};
		txt.setExtrusion(coord);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		x = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		y = ((Double)v.get(1)).doubleValue();
		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		z = ((Double)v.get(1)).doubleValue();
		coord = new double[]{x, y, z};
		txt.setXAxisDirection(coord);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		double val = ((Double)v.get(1)).doubleValue();
		txt.setWidth(val);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		val = ((Double)v.get(1)).doubleValue();
		txt.setHeight(val);

		v = DwgUtil.getBitShort(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		int ival = ((Integer)v.get(1)).intValue();
		txt.setAttachment(ival);

		v = DwgUtil.getBitShort(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		ival = ((Integer)v.get(1)).intValue();
		txt.setDrawingDir(ival);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		val = ((Double)v.get(1)).doubleValue();
		txt.setExtHeight(val);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		val = ((Double)v.get(1)).doubleValue();
		txt.setExtWidth(val);

		v = DwgUtil.getTextString(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		String text = (String)v.get(1);
		txt.setText(text);

		v = DwgUtil.getBitShort(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		ival = ((Integer)v.get(1)).intValue();
		txt.setLineSpacingStyle(ival);

		v = DwgUtil.getBitDouble(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		val = ((Double)v.get(1)).doubleValue();
		txt.setLineSpacingFactor(val);

		v = DwgUtil.testBit(data, bitPos);
		bitPos = ((Integer)v.get(0)).intValue();
		boolean flag = ((Boolean)v.get(1)).booleanValue(); //unknow

		v = DwgUtil.getBitLong(data, bitPos); //UNKNOWN LONG DE MOMENTO NO SALE
		bitPos = ((Integer)v.get(0)).intValue();
		int Unknown  = ((Integer)v.get(1)).intValue();
		bitPos = readObjectTailer(data, bitPos, txt);

		DwgHandleReference hr = new DwgHandleReference();
		bitPos = hr.read(data, bitPos);
		txt.setStyleHandle(hr);

		txt.inserta();

	}
}
