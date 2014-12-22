/*
 * Created on 03-feb-2007
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
* $Id: DwgPFace.java 10539 2007-02-28 07:35:10Z jmvivo $
* $Log$
* Revision 1.2.2.1  2007-02-28 07:35:10  jmvivo
* Actualizado desde el HEAD.
*
* Revision 1.2  2007/02/07 12:44:27  fdiaz
* A�adido o modificado el metodo clone para que el DwgObject se encargue de las propiedades comunes a todos los objetos.
* A�adido el metodo fill.
*
* Revision 1.1  2007/02/05 07:03:22  azabala
* *** empty log message ***
*
*
*/
package org.fastcatgroup.documentFilter.autocad.dwg.objects;

import org.fastcatgroup.documentFilter.autocad.dwg.DwgHandleReference;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;

public class DwgPFace extends DwgObject{

	private int vertexCount;
	private int faceCount;
	private DwgHandleReference firstVertexHandle;
	private DwgHandleReference lastVertexHandle;
	private DwgHandleReference seqendHandle;

	public DwgPFace(int index) {
		super(index);
		// TODO Auto-generated constructor stub
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}

	public void setFirstVertexHandle(DwgHandleReference handle) {
		this.firstVertexHandle = handle;
	}

	public void setLastVertexHandle(DwgHandleReference handle) {
		this.lastVertexHandle = handle;
	}

	public void setSeqendHandle(DwgHandleReference handle) {
		this.seqendHandle = handle;
	}

	public int getFaceCount() {
		return faceCount;
	}

	public DwgHandleReference getFirstVertexHandle() {
		return firstVertexHandle;
	}

	public DwgHandleReference getLastVertexHandle() {
		return lastVertexHandle;
	}

	public DwgHandleReference getSeqendHandle() {
		return seqendHandle;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	public Object clone(){
		DwgPFace obj = new DwgPFace(index);
		this.fill(obj);
		return obj;
	}
	
	protected void fill(DwgObject obj){
		super.fill(obj);
		DwgPFace myObj = (DwgPFace)obj;
		
		myObj.setFaceCount(faceCount);
		myObj.setFirstVertexHandle(firstVertexHandle);
		myObj.setLastVertexHandle(lastVertexHandle);
		myObj.setSeqendHandle(seqendHandle);
		myObj.setVertexCount(vertexCount);

	}

}

