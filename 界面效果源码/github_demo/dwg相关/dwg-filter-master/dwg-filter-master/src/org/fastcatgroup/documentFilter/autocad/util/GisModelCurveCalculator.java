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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class allows to obtain arcs and circles given by the most usual parameters, in a
 * Gis geometry model. In this model, an arc or a circle is given by a set of points that
 * defines it shape
 * 
 * @author jmorell
 */
public class GisModelCurveCalculator {
    
    /**
     * This method calculates an array of Point2D that represents a circle. The distance
     * between it points is 1 angular unit 
     * 
     * @param c Point2D that represents the center of the circle
     * @param r double value that represents the radius of the circle
     * @return Point2D[] An array of Point2D that represents the shape of the circle
     */
	public static List calculateGisModelCircle(Point2D c, double r) {
		List pts = new ArrayList();
		int angulo = 0;
		for (angulo=0; angulo<360; angulo++) {
			double[] pt = new double[]{c.getX(), c.getY()};
			pt[0] = pt[0] + r * Math.sin(angulo*Math.PI/(double)180.0);
			pt[1] = pt[1] + r * Math.cos(angulo*Math.PI/(double)180.0);
			
			pts.add(pt);
		}
		return pts;
	}
	
    /**
     * This method calculates an array of Point2D that represents a ellipse. The distance
     * between it points is 1 angular unit 
     * 
     * @param center Point2D that represents the center of the ellipse
     * @param majorAxisVector Point2D that represents the vector for the major axis
     * @param axisRatio double value that represents the axis ratio
	 * @param initAngle double value that represents the start angle of the ellipse arc
	 * @param endAngle double value that represents the end angle of the ellipse arc
     * @return Point2D[] An array of Point2D that represents the shape of the ellipse
     */
	public static List calculateGisModelEllipse(Point2D center, Point2D majorAxisVector, double axisRatio, double initAngle, double endAngle) {
		Point2D majorPoint = new Point2D.Double(center.getX()+majorAxisVector.getX(), center.getY()+majorAxisVector.getY());
		double orientation  = Math.atan(majorAxisVector.getY()/majorAxisVector.getX());
	    double semiMajorAxisLength = center.distance(majorPoint);
		double semiMinorAxisLength = semiMajorAxisLength*axisRatio;
	    double eccentricity = Math.sqrt(1-((Math.pow(semiMinorAxisLength, 2))/(Math.pow(semiMajorAxisLength, 2))));
		int isa = (int)initAngle;
		int iea = (int)endAngle;
		double angulo;
		List pts = new ArrayList();
		if (initAngle <= endAngle) {
			angulo = initAngle;
			double r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
		    double x = r*Math.cos(angulo*Math.PI/(double)180.0);
		    double y = r*Math.sin(angulo*Math.PI/(double)180.0);
		    double xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
		    double yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
			
			double[] pt = new double[]{center.getX() + xrot, center.getY() + yrot };
			pts.add(pt);
			
			for (int i=1; i<=(iea-isa)+1; i++) {
				angulo = (double)(isa+i);
				r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
			    x = r*Math.cos(angulo*Math.PI/(double)180.0);
			    y = r*Math.sin(angulo*Math.PI/(double)180.0);
			    xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
			    yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
			    
			    pt = new double[]{center.getX() + xrot, center.getY() + yrot};
			    pts.add(pt);
			}
			
			angulo = endAngle;
			r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
		    x = r*Math.cos(angulo*Math.PI/(double)180.0);
		    y = r*Math.sin(angulo*Math.PI/(double)180.0);
		    xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
		    yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
		    
		    
		    pt = new double[]{center.getX() + xrot, center.getY() + yrot};
		    pts.add(pt);
		    
		} else {
			angulo = initAngle;
			double r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
		    double x = r*Math.cos(angulo*Math.PI/(double)180.0);
		    double y = r*Math.sin(angulo*Math.PI/(double)180.0);
		    double xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
		    double yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
		    
		  
			double[] pt = new double[]{center.getX() + r*Math.cos(angulo*Math.PI/(double)180.0), center.getY() + r*Math.sin(angulo*Math.PI/(double)180.0)};
		    pts.add(pt);
		    
		    for (int i=1; i<=(360-isa); i++) {
				angulo = (double)(isa+i);
				r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
			    x = r*Math.cos(angulo*Math.PI/(double)180.0);
			    y = r*Math.sin(angulo*Math.PI/(double)180.0);
			    xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
			    yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
			    
			    pt = new double[]{center.getX() + xrot, center.getY() + yrot};
			    pts.add(pt);
			}
		    
		    
			for (int i=(360-isa)+1; i<=(360-isa)+iea; i++) {
				angulo = (double)(i-(360-isa));
				r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
			    x = r*Math.cos(angulo*Math.PI/(double)180.0);
			    y = r*Math.sin(angulo*Math.PI/(double)180.0);
			    xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
			    yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
			    
			    pt = new double[]{center.getX() + xrot, center.getY() + yrot};
			    pts.add(pt);
			    
			}
			
			angulo = endAngle;
			r = semiMinorAxisLength/Math.sqrt(1-((Math.pow(eccentricity, 2))*(Math.pow(Math.cos(angulo*Math.PI/(double)180.0), 2))));
		    x = r*Math.cos(angulo*Math.PI/(double)180.0);
		    y = r*Math.sin(angulo*Math.PI/(double)180.0);
		    xrot = x*Math.cos(orientation) - y*Math.sin(orientation);
		    yrot = x*Math.sin(orientation) + y*Math.cos(orientation);
		    
		    pt = new double[]{center.getX() + xrot, center.getY() + yrot};
		    pts.add(pt);
		}
		return pts;
	}
	
	/**
     * This method calculates an array of Point2D that represents an arc. The distance
     * between it points is 1 angular unit 
	 * 
     * @param c Point2D that represents the center of the arc
     * @param r double value that represents the radius of the arc
	 * @param sa double value that represents the start angle of the arc
	 * @param ea double value that represents the end angle of the arc
     * @return Point2D[] An array of Point2D that represents the shape of the arc
	 */
	public static List calculateGisModelArc(double[] center, double r, double sa, double ea) {
		int isa = (int)sa;
		int iea = (int)ea;
		double angulo;
		List pts = new ArrayList();
		if (sa <= ea) {
			angulo = sa;
			pts.add(new double[]{center[0] + r * Math.cos(angulo*Math.PI/(double)180.0), 
					center[1] + r * Math.sin(angulo*Math.PI/(double)180.0)});
			for (int i=1; i <= (iea-isa)+1; i++) {
				angulo = (double)(isa+i);
				double x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
				double y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0);
				pts.add(new double[]{x, y});
			}
			angulo = ea;
			double x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
			double y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0);
			pts.add(new double[]{x, y});
		} else {
			angulo = sa;
			double x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
			double y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0);
			pts.add(new double[]{x, y});
			for (int i=1; i <= (360-isa); i++) {
				angulo = (double)(isa+i);
				x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
				y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0); 
				pts.add(new double[]{x, y});
			}
			
			for (int i=( 360-isa)+1; i <= (360-isa)+iea; i++) {
				angulo = (double)(i-(360-isa));
				x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
				y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0);
				pts.add(new double[]{x, y});
			}
			angulo = ea;
			x = center[0] + r * Math.cos(angulo*Math.PI/(double)180.0);
			y = center[1] + r * Math.sin(angulo*Math.PI/(double)180.0);
			pts.add(new double[]{x, y});
		}
		return pts;
	}
	
	/**
	 * This method applies an array of bulges to an array of Point2D that defines a
	 * polyline. The result is a polyline with the input points with the addition of the
	 * points that define the new arcs added to the polyline
	 * 
	 * @param newPts Base points of the polyline
	 * @param bulges Array of bulge parameters
	 * @return Polyline with a new set of arcs added and defined by the bulge parameters
	 */
	public static List calculateGisModelBulge(List newPts, double[] bulges) {
		Vector ptspol = new Vector();
		double[] init = null;
		double[] end = null;
		for (int j=0; j<newPts.size(); j++) {
			init = (double[]) newPts.get(j);
			if (j != newPts.size()-1) 
				end = (double[]) newPts.get(j+1);
			if (bulges[j]==0 || j== newPts.size()-1 || 
				(init[0] == end[0] && init[1] == end[1])) {
				ptspol.add(init);
			} else {
				ArcFromBulgeCalculator arcCalculator = new ArcFromBulgeCalculator(init, end, bulges[j]);
				Vector arc = arcCalculator.getPoints(1);
				if (bulges[j]<0) {
					for (int k=arc.size()-1; k>=0; k--) {
						ptspol.add(arc.get(k));
					}
					ptspol.remove(ptspol.size()-1);
				} else {
					for (int k=0;k<arc.size();k++) {
						ptspol.add(arc.get(k));
					}
					ptspol.remove(ptspol.size()-1);
				}
			}
		}
		List points = new ArrayList();
		for (int j=0;j<ptspol.size();j++) {
			points.add(ptspol.get(j));
		}
		return points;
	}
}
