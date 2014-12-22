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
package org.fastcatgroup.documentFilter.autocad.dwg;

import java.io.IOException;

import org.fastcatgroup.documentFilter.autocad.dwg.DwgFile;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgVersionNotSupportedException;


/**
 * Test class for jdwglib
 * 
 * @author jmorell
 */
public class Test {
    
    /**
     * Main method of the jdwglib test class
     * 
     * @param args Parameters list
     */
	public static void main(String[] args) {
		new Test();
	}
	
	/**
	 * This method is useful to test jdwglib with sets of DWG files
	 */
	public Test() {
		try {
			//dwgFile.read("C:/jmorell/data/dwgtests/dwg2000.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/dwg2000arc.dwg");
			//dwgFile.read("c:/jmorell/data/dwgtests/dwg2000lwpline.dwg");
			//dwgFile.read("c:/jmorell/data/dwgtests/sabretest2000.dwg");
			//dwgFile.read("c:/jmorell/data/dwgtests/lwpline2000.dwg");
			//dwgFile.read("c:/jmorell/data/dwgtests/sabretest2000lwp.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/ycad/hexhouse_lns4.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/894/e89414_2000_pol.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/846/e84644_2000.dwg");
			//dwgFile.read("D:/jmorell/data/dwgtest/carto/e61443.dwg");
			//dwgFile.read("D:/jmorell/data/dwgtest/carto/lwpline1.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/ycad/fixt3_lns3.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/lwpline.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/894/e89414_2000.dwg");
			//dwgFile.read("C:/jmorell/data/dwgtests/ycad/blk3.dwg");
			//DwgFile dwg = new DwgFile("/media/ntfs/jmorell/data/dwgtest/carto/e72231.dwg");
            DwgFile dwg = new DwgFile("/Users/swsong/Desktop/dwg_source/testDWG2000.dwg");
			long t1 = System.currentTimeMillis();
			dwg.read();
			long t2 = System.currentTimeMillis();
			System.out.println("Tiempo empleado por la librería en leer el fichero dwg = " + (t2-t1));
			t1 = System.currentTimeMillis();
//			dwg.initializeLayerTable();
			dwg.calculateGisModelDwgPolylines();
//            dwg.applyExtrusions();
//            dwg.testDwg3D();
            dwg.blockManagement2();
			t2 = System.currentTimeMillis();
			System.out.println("Tiempo empleado por la librería en tratar el fichero dwg = " + (t2-t1));
			System.out.println("Java!");
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		} catch (DwgVersionNotSupportedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}
