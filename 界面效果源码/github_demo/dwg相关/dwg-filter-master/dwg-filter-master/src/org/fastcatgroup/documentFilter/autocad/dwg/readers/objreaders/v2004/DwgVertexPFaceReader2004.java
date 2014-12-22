/*
 * Created on 19-mar-2007
 *
 * gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
/* CVS MESSAGES:
*
* $Id: DwgVertexPFaceReader15.java,v 1.1.2.1 2007/03/21 19:49:16 azabala Exp $
* $Log: DwgVertexPFaceReader15.java,v $
* Revision 1.1.2.1  2007/03/21 19:49:16  azabala
* implementation of dwg 12, 13, 14.
*
* Revision 1.1  2007/03/20 19:57:08  azabala
* source code cleaning
*
*
*/
package org.fastcatgroup.documentFilter.autocad.dwg.readers.objreaders.v2004;

import java.util.List;

import org.fastcatgroup.documentFilter.autocad.dwg.CorruptedDwgEntityException;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgUtil;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgVertexPFace;


public class DwgVertexPFaceReader2004 extends AbstractDwg2004Reader {
	//similar al reader de la versión 13-14
	public void readSpecificObj(int[] data, int offset, DwgObject dwgObj)
			throws RuntimeException, CorruptedDwgEntityException {

		 if(! (dwgObj instanceof DwgVertexPFace))
		    	throw new RuntimeException("DwgVertexPFaceReader2004 solo puede leer DwgVertexPFace");
		 DwgVertexPFace v = (DwgVertexPFace) dwgObj;

		 int bitPos = offset;
		 bitPos = readObjectHeader(data, bitPos, v);

		 List val = DwgUtil.getRawChar(data, bitPos);
		 bitPos = ((Integer) val.get(0)).intValue();
		 int flags = ((Integer) val.get(1)).intValue();
		 v.setFlags(flags);

		 val = DwgUtil.getBitDouble(data, bitPos);
		 bitPos = ((Integer) val.get(0)).intValue();
		 double x = ((Double) val.get(1)).doubleValue();

		 val = DwgUtil.getBitDouble(data, bitPos);
		 bitPos = ((Integer) val.get(0)).intValue();
		 double y = ((Double) val.get(1)).doubleValue();

		 val = DwgUtil.getBitDouble(data, bitPos);
		 bitPos = ((Integer) val.get(0)).intValue();
		 double z = ((Double) val.get(1)).doubleValue();
		 v.setPoint(new double[]{x, y, z});

		 bitPos = readObjectTailer(data, bitPos, v);
	}

}



