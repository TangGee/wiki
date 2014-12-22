/* jdwglib. Java Library for reading Dwg files.
 * 
 * Author: Jose Morell Rama (jose.morell@gmail.com).
 * Port from the Pythoncad Dwg library by Art Haas.
 *
 * Copyright (C) 2005 Jose Morell, IVER TI S.A. and Generalitat Valenciana
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
 * Jose Morell (jose.morell@gmail.com)
 * 
 * or
 *
 * IVER TI S.A.
 *  C/Salamanca, 50
 *  46005 Valencia
 *  Spain
 *  +34 963163400
 *  dac@iver.es
 */
package org.fastcatgroup.documentFilter.autocad.util;

import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * This class calculates an arc given by a start and end points and a bulge
 * 
 * @author jmorell
 */
public class ArcFromBulgeCalculator {
	private double[] coord1, coord2;
	private double[] center;
	private double radio, empieza, acaba;
	private double bulge;
	private double d, dd, aci;
	private double[] coordAux;
	
	/**
	 * This method calculates an arc given by a start and end points and a bulge
	 * 
	 * @param p1 Start point of the arc given by a Point2D
	 * @param p2 End point of the arc given by a Point2D
	 * @param bulge Bulge of the arc given by a double value 
	 */
	public ArcFromBulgeCalculator(double[] p1, double[] p2, double bulge) {
		this.bulge = bulge;
		if (bulge < 0.0) {
			coord1 = p2;
			coord2 = p1;
		} else {
			coord1 = p1;
			coord2 = p2;
		}
		calParams();
	}
	
	private void calParams() {
		d = Math.sqrt((coord2[0]-coord1[0])*(coord2[0]-coord1[0]) + 
				(coord2[1]-coord1[1])*(coord2[1]-coord1[1]));
		coordAux = new double[]{(coord1[0]+coord2[0])/2.0, (coord1[1]+coord2[1])/2.0};
		double b = Math.abs(bulge);
		double beta = Math.atan(b);
		double alfa = beta*4.0;
		double landa = alfa/2.0;
		dd = (d/2.0)/(Math.tan(landa));
		radio = (d/2.0)/(Math.sin(landa));		
		aci = Math.atan((coord2[0]-coord1[0])/(coord2[1]-coord1[1]));
		double aciDegree = aci*180.0/Math.PI;
		if (coord2[1] > coord1[1]) {
			aci += Math.PI;
			aciDegree = aci*180.0/Math.PI;
		}
		center = new double[]{coordAux[0] + dd*Math.sin(aci+(Math.PI/2.0)), coordAux[1] + dd*Math.cos(aci+(Math.PI/2.0))};
		calEA(alfa);
	}
	
	private void calEA(double alfa){
		empieza = Math.atan2(coord1[1]-center[1], coord1[0]-center[0]);
		acaba = (empieza + alfa);
		empieza = empieza*180.0/Math.PI;
		acaba = acaba*180.0/Math.PI;
	}
	
	/**
	 * This method calculates an arc in a Gis geometry model. This arc is represented in
	 * this model by a Vector of Point2D. The distance between points in the arc is given
	 * as an argument
	 * 
	 * @param inc Distance between points in the arc
	 * @return Vector Vector with the set of Point2D that represents the arc
	 */
	public Vector getPoints(double inc) {
		Vector arc = new Vector();
		double angulo;
		int iempieza = (int) empieza + 1;
		int iacaba = (int) acaba;
		if (empieza <= acaba) {
			addNode(arc, empieza);
			for (angulo = iempieza; angulo <= iacaba; angulo += inc) {
				addNode(arc, angulo);
			}
			addNode(arc, acaba);
		} else {
			addNode(arc, empieza);
			for (angulo = iempieza ; angulo <= 360; angulo += inc) {
				addNode(arc, angulo);
			}
			for (angulo = 1; angulo <= iacaba; angulo += inc) {
				addNode(arc, angulo);
			}
			addNode(arc, angulo);
		}
		Point2D aux = (Point2D)arc.get(arc.size()-1);
		double aux1 = Math.abs(aux.getX()-coord2[0]);
		double aux2 = Math.abs(aux.getY()-coord2[1]);
		return arc;
	}
	
	/**
	 * Method that allows to obtain a set of points located in the central zone of 
	 * this arc object
	 */
	public Vector getCentralPoint() {
		Vector arc = new Vector();
		if (empieza <= acaba) {
			addNode(arc, (empieza+acaba)/2.0);
		} else {
			addNode(arc, empieza);
			double alfa = 360-empieza;
			double beta = acaba;
			double an = alfa + beta;
			double mid = an/2.0;
			if (mid<=alfa) {
				addNode(arc, empieza+mid);
			} else {
				addNode(arc, mid-alfa);
			}
		}
		return arc;
	}
	
	private void addNode(Vector arc, double angulo) {
		double yy = center[1] + radio * Math.sin(angulo*Math.PI/180.0);
		double xx = center[0] + radio * Math.cos(angulo*Math.PI/180.0);		
		arc.add(new Point2D.Double(xx,yy));
	}
}
