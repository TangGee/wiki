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
package org.fastcatgroup.documentFilter.autocad.dwg.objects;

//import com.iver.cit.gvsig.fmap.core.FPoint2D;
//import com.iver.cit.gvsig.fmap.core.FPoint3D;
//import com.iver.cit.gvsig.fmap.core.IGeometry;
//import com.iver.cit.gvsig.fmap.core.ShapeFactory;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgHandleReference;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.IDwg2FMap;
import org.fastcatgroup.documentFilter.autocad.dwg.IDwg3DTestable;
import org.fastcatgroup.documentFilter.autocad.dwg.IDwgExtrusionable;
import org.fastcatgroup.documentFilter.autocad.util.AcadExtrusionCalculator;

/**
 * The DwgMText class represents a DWG MText
 * 
 * @author jmorell, azabala
 */
public class DwgMText extends DwgObject 
	implements IDwgExtrusionable, IDwg3DTestable, IDwg2FMap {
	public DwgMText(int index) {
		super(index);
		// TODO Auto-generated constructor stub
	}
	private double[] insertionPoint;
	private double[] extrusion;
	private double[] xAxisDirection;
	private double width;
	private double height;
	private int attachment;
	private int drawingDir;
	private double extHeight;
	private double extWidth;
	private String text;
	private int lineSpacingStyle;
	private double lineSpacingFactor;
	private DwgHandleReference styleHandle = null;
	
	
	/**
	 * @return Returns the height.
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return Returns the insertionPoint.
	 */
	public double[] getInsertionPoint() {
		return insertionPoint;
	}
	/**
	 * @param insertionPoint The insertionPoint to set.
	 */
	public void setInsertionPoint(double[] insertionPoint) {
		this.insertionPoint = insertionPoint;
	}
	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return Returns the width.
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(double width) {
		this.width = width;
	}
    /**
     * @return Returns the extrusion.
     */
    public double[] getExtrusion() {
        return extrusion;
    }

    /**
	 * @return Returns the attachment.
	 */
	public int getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment The attachment to set.
	 */
	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}
	/**
	 * @return Returns the drawingDir.
	 */
	public int getDrawingDir() {
		return drawingDir;
	}
	/**
	 * @param drawingDir The drawingDir to set.
	 */
	public void setDrawingDir(int drawingDir) {
		this.drawingDir = drawingDir;
	}
	/**
	 * @return Returns the extHeight.
	 */
	public double getExtHeight() {
		return extHeight;
	}
	/**
	 * @param extHeight The extHeight to set.
	 */
	public void setExtHeight(double extHeight) {
		this.extHeight = extHeight;
	}
	/**
	 * @return Returns the extWidth.
	 */
	public double getExtWidth() {
		return extWidth;
	}
	/**
	 * @param extWidth The extWidth to set.
	 */
	public void setExtWidth(double extWidth) {
		this.extWidth = extWidth;
	}
	/**
	 * @return Returns the lineSpacingFactor.
	 */
	public double getLineSpacingFactor() {
		return lineSpacingFactor;
	}
	/**
	 * @param lineSpacingFactor The lineSpacingFactor to set.
	 */
	public void setLineSpacingFactor(double lineSpacingFactor) {
		this.lineSpacingFactor = lineSpacingFactor;
	}
	/**
	 * @return Returns the lineSpacingStyle.
	 */
	public int getLineSpacingStyle() {
		return lineSpacingStyle;
	}
	/**
	 * @param lineSpacingStyle The lineSpacingStyle to set.
	 */
	public void setLineSpacingStyle(int lineSpacingStyle) {
		this.lineSpacingStyle = lineSpacingStyle;
	}
	/**
	 * @return Returns the styleHandle.
	 */
	public DwgHandleReference getStyleHandle() {
		return styleHandle;
	}
	/**
	 * @param styleHandle The styleHandle to set.
	 */
	public void setStyleHandle(DwgHandleReference styleHandle) {
		this.styleHandle = styleHandle;
	}
	/**
	 * @return Returns the xAxisDirection.
	 */
	public double[] getXAxisDirection() {
		return xAxisDirection;
	}
	/**
	 * @param axisDirection The xAxisDirection to set.
	 */
	public void setXAxisDirection(double[] axisDirection) {
		xAxisDirection = axisDirection;
	}
	/**
	 * @param extrusion The extrusion to set.
	 */
	public void setExtrusion(double[] extrusion) {
		this.extrusion = extrusion;
	}
	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.IDwgExtrusionable#applyExtrussion()
	 */
	public void applyExtrussion() {
		 double[] mtextPoint = getInsertionPoint();
         double[] mtextExt = getExtrusion();
         mtextPoint = AcadExtrusionCalculator.extrude2(mtextPoint, mtextExt);
         setInsertionPoint(mtextPoint);
	}
	/* (non-Javadoc)
	 * @see com.iver.cit.jdwglib.dwg.IDwg3DTestable#has3DData()
	 */
	public boolean has3DData() {
		return (getInsertionPoint()[2] != 0.0);
	}
	public double getZ() {
		return getInsertionPoint()[2];
	}
	
//	public IGeometry toFMapGeometry(boolean is3DFile) {
//		FPoint2D fPoint = null;
//		double[] p = getInsertionPoint();
//		if (is3DFile) {
//			fPoint = new FPoint3D(p[0], p[1], p[2]);
//		} else {
//			fPoint = new FPoint2D(p[0], p[1]);
//		}
//		return ShapeFactory.createGeometry(fPoint);
//	}
	public String toFMapString(boolean is3DFile) {
		if(is3DFile)
			return "FPoint3D";
		else
			return "FPoint2D";
	}
	
	public String toString(){
		return "MText";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone(){
		DwgMText obj = new DwgMText(index);
		this.fill(obj);
		return obj;
	}
	
	protected void fill(DwgObject obj){
		super.fill(obj);
		DwgMText myObj = (DwgMText)obj;

		myObj.setAttachment(attachment);
		myObj.setDrawingDir(drawingDir);
		myObj.setExtHeight(extHeight);
		myObj.setExtrusion(extrusion);
		myObj.setExtWidth(extWidth);
		myObj.setHeight(extHeight);
		myObj.setInsertionPoint(insertionPoint);
		myObj.setLineSpacingFactor(lineSpacingFactor);
		myObj.setLineSpacingStyle(lineSpacingStyle);
		myObj.setStyleHandle(styleHandle);
		myObj.setText(text);
		myObj.setWidth(width);
		myObj.setXAxisDirection(xAxisDirection);
	}

}
