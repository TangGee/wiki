package org.fastcatgroup.documentFilter.autocad.dwg;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.iver.cit.jdwglib.dwg");
		//$JUnit-BEGIN$
		suite.addTestSuite(DwgFileTest.class);
		//$JUnit-END$
		return suite;
	}

}
