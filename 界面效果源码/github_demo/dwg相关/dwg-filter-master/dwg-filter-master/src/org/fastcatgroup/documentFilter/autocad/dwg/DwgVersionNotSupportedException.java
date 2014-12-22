/*
 * Created on 07-sep-2006
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
* $Id: DwgVersionNotSupportedException.java 7116 2006-09-07 18:24:34Z azabala $
* $Log$
* Revision 1.1  2006-09-07 18:24:34  azabala
* throws a DwgNotSupportedVersion if dwg version is not supported
*
*
*/
package org.fastcatgroup.documentFilter.autocad.dwg;

public class DwgVersionNotSupportedException extends Exception {
	
	String dwgVersion;
	
	
	
	public DwgVersionNotSupportedException(String text){
		super(text);
	}
	
	public void setDwgVersion(String text){
		this.dwgVersion = text;
	}
	
	public String getDwgVersion(){
		return dwgVersion;
	}
}

