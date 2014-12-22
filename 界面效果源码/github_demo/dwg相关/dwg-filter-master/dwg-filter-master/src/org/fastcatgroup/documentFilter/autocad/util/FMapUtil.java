package org.fastcatgroup.documentFilter.autocad.util;
///*
// * Created on 18-ene-2007 by azabala
// *
// */
//package com.iver.cit.jdwglib.util;
//
//import java.awt.geom.Point2D;
//import java.util.List;
//
//import com.iver.cit.gvsig.fmap.core.FPolygon2D;
//import com.iver.cit.gvsig.fmap.core.FPolygon3D;
//import com.iver.cit.gvsig.fmap.core.FPolyline2D;
//import com.iver.cit.gvsig.fmap.core.FPolyline3D;
//import com.iver.cit.gvsig.fmap.core.GeneralPathX;
//import com.iver.cit.jdwglib.dwg.objects.DwgVertexPFace;
//
///**
// * @author alzabord
// *
// */
//public class FMapUtil {
//	/**
//	 * Method that changes a Point3D array to a FPolyline3D. Is useful to
//	 * convert a polyline given by it points to a FPolyline3D, a polyline 3D in
//	 * the FMap model object
//	 *
//	 * @param pts
//	 *            Array of Point3D that defines the polyline 3D that will be
//	 *            converted in a FPolyline3D
//	 * @return FPolyline3D This FPolyline3D is build using the array of Point3D
//	 *         that is the argument of the method
//	 */
//	public static FPolyline3D points3DToFPolyline3D(List pts) {
//		GeneralPathX genPathX = getGeneralPathX(pts);
//		double[] elevations = new double[pts.size()];
//		for (int i = 0; i < pts.size(); i++) {
//			elevations[i] = ((double[])pts.get(i))[2];
//		}
//		return new FPolyline3D(genPathX, elevations);
//	}
//
//	private static  GeneralPathX getGeneralPathX(List pts){
//		GeneralPathX genPathX = new GeneralPathX();
//
//		Object firstVertex = pts.get(0);
//		double[] coordinate = null;
//		if(firstVertex instanceof double[])
//			coordinate = (double[])firstVertex;
//		else if(firstVertex instanceof DwgVertexPFace){
//			DwgVertexPFace v = (DwgVertexPFace)firstVertex;
//			coordinate = v.getPoint();
//		}else if(firstVertex instanceof Point2D){
//			Point2D point = (Point2D)firstVertex;
//			coordinate = new double[]{point.getX(), point.getY()};
//		}
//		genPathX.moveTo(coordinate[0],
//						coordinate[1]);
//
//		//TODO ESTE LIO SE DEBE A QUE EN ALGUNOS CASOS SE GUARDAN
//		//double[] y en otros el IDwgVertex UNIFICAR
//		for (int i = 1; i < pts.size(); i++) {
//			Object vertex = pts.get(i);
//			double[] vertexCoordinate = null;
//			if(vertex instanceof double[])
//				vertexCoordinate = (double[])vertex;
//			else if(vertex instanceof DwgVertexPFace){
//				DwgVertexPFace v = (DwgVertexPFace)vertex;
//				vertexCoordinate = v.getPoint();
//			}else if(vertex instanceof Point2D){
//				Point2D point = (Point2D)vertex;
//				vertexCoordinate = new double[]{point.getX(), point.getY()};
//			}
//			genPathX.lineTo(vertexCoordinate[0],
//							vertexCoordinate[1]);
//		}
//		return genPathX;
//	}
//
//	public static FPolygon3D ptsTo3DPolygon(List pts){
//		GeneralPathX genPathX = getGeneralPathX(pts);
//		double[] elevations = new double[pts.size()];
//		for (int i = 0; i < pts.size(); i++) {
////			TODO ESTE LIO SE DEBE A QUE EN ALGUNOS CASOS SE GUARDAN
//			//double[] y en otros el IDwgVertex UNIFICAR
//			Object vertex = pts.get(i);
//			double[] vertexCoordinate = null;
//			if(vertex instanceof double[])
//				vertexCoordinate = (double[])vertex;
//			else{
//				DwgVertexPFace v = (DwgVertexPFace)vertex;
//				vertexCoordinate = v.getPoint();
//			}
//			elevations[i] = vertexCoordinate[2];
//		}
//		return new FPolygon3D(genPathX, elevations);
//	}
//	/**
//	 * Method that changes a Point2D array to a FPolyline2D. Is useful to
//	 * convert a polyline given by it points to a FPolyline2D, a polyline in the
//	 * FMap model object
//	 *
//	 * @param pts
//	 *            Array of Point2D that defines the polyline that will be
//	 *            converted in a FPolyline2D
//	 * @return FPolyline2D This FPolyline2D is build using the array of Point2D
//	 *         that is the argument of the method
//	 */
//	public static FPolyline2D points2DToFPolyline2D(List pts) {
//		GeneralPathX genPathX = getGeneralPathX(pts);
//		return new FPolyline2D(genPathX);
//	}
//
//	public static FPolygon2D ptsTo2DPolygon(List pts){
//		GeneralPathX genPathX = getGeneralPathX(pts);
//		return new FPolygon2D(genPathX);
//	}
//}
