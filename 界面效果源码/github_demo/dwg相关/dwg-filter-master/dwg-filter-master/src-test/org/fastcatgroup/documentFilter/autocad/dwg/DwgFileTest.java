package org.fastcatgroup.documentFilter.autocad.dwg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.fastcatgroup.documentFilter.autocad.dwg.DwgFile;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgObject;
import org.fastcatgroup.documentFilter.autocad.dwg.DwgVersionNotSupportedException;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgBlockHeader;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgMText;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgPFacePolyline;
import org.fastcatgroup.documentFilter.autocad.dwg.objects.DwgText;

import junit.framework.TestCase;


public class DwgFileTest extends TestCase {
	private File baseDataPath;

	protected void setUp() throws Exception {
		super.setUp();
		URL url = this.getClass().getResource("DwgFileTest_data");
		if (url == null)
			throw new Exception("Can't find 'DwgFileTest_data' dir");

		baseDataPath = new File(url.getFile());
		if (!baseDataPath.exists())
			throw new Exception("Can't find 'DwgFileTest_data' dir");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() throws IOException, DwgVersionNotSupportedException {
		// String fileName = baseDataPath.getAbsolutePath()+"/Un punto.dwg";
		// DwgFile dwg = new DwgFile(fileName);
		//
		// dwg.read();
		// dwg.calculateGisModelDwgPolylines();
		// dwg.blockManagement();
		// LinkedList dwgObjects = dwg.getDwgObjects();
	}

	

	// test of DWG 12 format
	public void test4() throws IOException, DwgVersionNotSupportedException {
//		String fileName = baseDataPath.getAbsolutePath() + "/TORRE03.DWG";
//		DwgFile dwg = new DwgFile(fileName);
//		dwg.read();
//		dwg.calculateGisModelDwgPolylines();
//		//antes de los bloques
//		List dwgObjects = dwg.getDwgObjects();
//		dwg.blockManagement2();
//		//despues de los bloques
//		dwgObjects = dwg.getDwgObjects();
//		for(int i = 0; i < dwgObjects.size(); i++){
//			DwgObject o = (DwgObject) dwgObjects.get(i);
//			if(o instanceof IDwg2FMap){
//				((IDwg2FMap)o).toFMapGeometry(true);
//			}
//		}
	}
	
	public void test5() throws IOException, DwgVersionNotSupportedException{
		 String fileName = "/Users/swsong/Desktop/dxf_bin_target2/truetype.dwg";
		 System.out.println(new File(fileName).getAbsolutePath());
		 DwgFile dwg = new DwgFile(fileName);
		
		 dwg.read();
		 List dwgObjects = dwg.getDwgObjects();
		 for(int i = 0; i < dwgObjects.size(); i++){
			 DwgObject obj = (DwgObject) dwgObjects.get(i);
//			 System.out.println(obj +": " +obj.toString());
//			 if(obj instanceof DwgBlockHeader){
//				 DwgBlockHeader blockHeader = (DwgBlockHeader)obj;
//				 if(blockHeader.isBlkIsXRef()){
//					 System.out.println("bloque "+blockHeader.getName()+" es referencia externa");
//					 System.out.println("path="+blockHeader.getXRefPName());
//				 }
//			 }
//			 if(obj instanceof DwgBlockHeader){
//				 DwgBlockHeader blockHeader = (DwgBlockHeader)obj;
//				 if(blockHeader.isBlkIsXRef()){
//					 System.out.println("bloque "+blockHeader.getName()+" es referencia externa");
//					 System.out.println("path="+blockHeader.getXRefPName());
//				 }
//			 }
			 if(obj instanceof DwgMText){
				 DwgMText blockHeader = (DwgMText)obj;
				 System.out.println("mtext > "+blockHeader.getText()
//						 .replaceAll("\\\\P", "\n")
//						 .replaceAll("\\\\.*;", "")
						 );
			 }
//			 if(obj instanceof DwgText){
//				 DwgText blockHeader = (DwgText)obj;
//				 System.out.println("text > "+blockHeader.getText());
//			 }
		 }
	}

}
