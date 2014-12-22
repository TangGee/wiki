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
package org.gvsig.dwg.lib;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.gvsig.dwg.lib.util.HexUtil;



/**
 * The DwgUtil class contains the essential set of functions for reading bitstreams
 * in DWG files
 *
 * @author jmorell
 */
public final class DwgUtil {

	/**
	 * Read the extended data for a DWG object
	 * Don't use in this version. Serious bugs detected
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the extended data of a DWG object
	 */
	public static ArrayList readExtendedData(int[] data, int offset) throws RuntimeException {
		int bitPos = offset;
		ArrayList extData = new ArrayList();
		while (true) {
			int newBitPos = ((Integer)getBitShort(data, bitPos).get(0)).intValue();
			int size = ((Integer) getBitShort(data, bitPos).get(1)).intValue();
			bitPos = newBitPos;
			if (size == 0) {
				break;
			}
			newBitPos = ((Integer)((ArrayList)getHandle(data, bitPos)).get(0)).intValue();
			// TODO: Esto no es correcto. Repasar ...
			int handle = ((Integer)((ArrayList)getHandle(data, bitPos)).get(1)).intValue();
			bitPos = newBitPos;
			ArrayList eedata = new ArrayList();
			while (size > 0) {
				newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
				int cb = ((Integer) getRawChar(data, bitPos).get(1)).intValue();
				bitPos = newBitPos;
				size = size - 1;
				if (cb == 0x0) {
					newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
					int len = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					newBitPos = ((Integer)getRawShort(data, bitPos).get(0)).intValue();
					int cp = ((Integer)getRawShort(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					ArrayList chars = new ArrayList();
					for (int i = 0; i < len; i++) {
						newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
						int charr = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
						bitPos = newBitPos;
						// �int o char?
						chars.add(new Integer(charr));
					}
					// Incorrecto. Repasar ...
					eedata.add(chars);
					size = size - len - 3;
				} else if (cb == 0x1) {
					System.out.println("Invalid EXX code byte: 0x1");
				} else if (cb == 0x2) {
					newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
					int charr = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					if (charr == 0x0) {
						eedata.add("{");
					} else if (charr == 0x1) {
						eedata.add("}");
					} else {
						System.out.println("Unexpected EXX char: " + charr);
					}
					size = size - 1;
				} else if (cb == 0x3 || cb == 0x5) {
					ArrayList chars = new ArrayList();
					for (int i = 0; i < 8; i++) {
						newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
						int charr = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
						bitPos = newBitPos;
						chars.add(new Integer(charr));
					}
					eedata.add(chars);
					size = size - 8;
				} else if (cb == 0x4) {
					newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
					int len = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					ArrayList chars = new ArrayList();
					for (int i = 0; i < len; i++) {
						newBitPos = ((Integer)getRawChar(data, bitPos).get(0)).intValue();
						int charr = ((Integer)getRawChar(data, bitPos).get(1)).intValue();
						bitPos = newBitPos;
						chars.add(new Integer(charr));
					}
					eedata.add(chars);
					size = size - len - 1;
				} else if (0xa <= cb && cb <= 0xd) {
					newBitPos = ((Integer)((ArrayList)getRawDouble(data, bitPos)).get(0)).intValue();
					double d1 = ((Double)((ArrayList)getRawDouble(data, bitPos)).get(1)).doubleValue();
					bitPos = newBitPos;
					newBitPos = ((Integer)((ArrayList)getRawDouble(data, bitPos)).get(0)).intValue();
					double d2 = ((Double)((ArrayList)getRawDouble(data, bitPos)).get(1)).doubleValue();
					bitPos = newBitPos;
					newBitPos = ((Integer)((ArrayList)getRawDouble(data, bitPos)).get(0)).intValue();
					double d3 = ((Double)((ArrayList)getRawDouble(data, bitPos)).get(1)).doubleValue();
					bitPos = newBitPos;
					eedata.add(new double[] { d1, d2, d3 });
					size = size - 24;
				} else if (0x28 <= cb && cb <= 0x2a) {
					newBitPos = ((Integer)((ArrayList)getRawDouble(data, bitPos)).get(0)).intValue();
					double d = ((Double)((ArrayList)getRawDouble(data, bitPos)).get(1)).doubleValue();
					bitPos = newBitPos;
					eedata.add(new Double(d));
					size = size - 8;
				} else if (cb == 0x46) {
					newBitPos = ((Integer)getRawShort(data, bitPos).get(0)).intValue();
					int shortt = ((Integer)getRawShort(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					eedata.add(new Integer(shortt));
					size = size - 2;
				} else if (cb == 0x47) {
					newBitPos = ((Integer)getRawLong(data, bitPos).get(0)).intValue();
					int longg = ((Integer)getRawLong(data, bitPos).get(1)).intValue();
					bitPos = newBitPos;
					eedata.add(new Integer(longg));
					size = size - 4;
				} else {
					System.out.println("Unexpected code byte: " + cb);
				}
			}
			ArrayList v = new ArrayList();
			// v.add(handle, eedata);
			extData.add(v);
		}
		ArrayList v = new ArrayList();
		v.add(new Integer(bitPos));
		v.add(extData);
		return v;
	}

	/**
	 * Read a double value from a group of unsigned bytes and a default double
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @param defVal Default double value
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the double value
	 */
	public static ArrayList getDefaultDouble(int[] data, int offset, double defVal) throws RuntimeException {
		int flags = ((Integer) getBits(data, 2, offset)).intValue();
		int read = 2;
		double val;
		if (flags == 0x0) {
			val = defVal;
		} else {
			int _offset = offset + 2;
			if (flags == 0x3) {
				byte[] bytes = (byte[]) getBits(data, 64, _offset);
				ByteBuffer bb = ByteBuffer.wrap(bytes);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				val = bb.getDouble();
				read = 66;
			} else {
				byte[] dstrArrayAux = new byte[8];
				int[] doubleOffset = new int[] { 0 };
				ByteUtils.doubleToBytes(defVal, dstrArrayAux, doubleOffset);
				byte[] dstrArrayAuxx = new byte[8];
				dstrArrayAuxx[0] = dstrArrayAux[7];
				dstrArrayAuxx[1] = dstrArrayAux[6];
				dstrArrayAuxx[2] = dstrArrayAux[5];
				dstrArrayAuxx[3] = dstrArrayAux[4];
				dstrArrayAuxx[4] = dstrArrayAux[3];
				dstrArrayAuxx[5] = dstrArrayAux[2];
				dstrArrayAuxx[6] = dstrArrayAux[1];
				dstrArrayAuxx[7] = dstrArrayAux[0];
				int[] dstrArrayAuxxx = new int[8];
				for (int i = 0; i < dstrArrayAuxxx.length; i++) {
					dstrArrayAuxxx[i] = ByteUtils.getUnsigned(dstrArrayAuxx[i]);
				}
				byte[] dstrArray = new byte[8];
				for (int i = 0; i < dstrArray.length; i++) {
					dstrArray[i] = (byte) dstrArrayAuxxx[i];
				}
				if (flags == 0x1) {
					byte[] ddArray = (byte[]) getBits(data, 32, _offset);
					dstrArray[0] = ddArray[0];
					dstrArray[1] = ddArray[1];
					dstrArray[2] = ddArray[2];
					dstrArray[3] = ddArray[3];
					read = 34;
				} else {
					byte[] ddArray = (byte[]) getBits(data, 48, _offset);
					dstrArray[4] = ddArray[0];
					dstrArray[5] = ddArray[1];
					dstrArray[0] = ddArray[2];
					dstrArray[1] = ddArray[3];
					dstrArray[2] = ddArray[4];
					dstrArray[3] = ddArray[5];
					read = 50;
				}
				ByteBuffer bb = ByteBuffer.wrap(dstrArray);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				val = bb.getDouble();
			}
		}
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + read));
		v.add(new Double(val));
		return v;
	}


	/**
	 * Read a double value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the double value
	 */
	public static ArrayList getBitDouble(int[] data, int offset) throws RuntimeException {
		ArrayList v = new ArrayList();
		int type = ((Integer) getBits(data, 2, offset)).intValue();
		int read = 2;
		double val = 0.0;
		if (type == 0x00) {
			byte[] bytes = (byte[]) getBits(data, 64, (offset + 2));
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			val = bb.getDouble();
			read = 66;
		} else if (type == 0x01) {
			val = 1.0;
		} else if (type == 0x02) {
			val = 0.0;
		} else {
			System.out.println("Bad type " + type + " at bit offset: " + offset);
		}
		v.add(new Integer(offset + read));
		v.add(new Double(val));
		return v;
	}


	/**
	 * Read a double value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the double value
	 */
	public static ArrayList getRawDouble(int[] data, int offset) throws RuntimeException {
		byte[] bytes = (byte[]) getBits(data, 64, offset);
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		double val = bb.getDouble();
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + 64));
		v.add(new Double(val));
		return v;
	}

	/**
	 * Read a short value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the short value
	 */
	public static ArrayList getBitShort(int[] data, int offset) throws RuntimeException {
		ArrayList v = new ArrayList();
		int type = ((Integer) getBits(data, 2, offset)).intValue();
		int read = 2;
		int val = 0;
		if (type == 0x00) {
			byte[] bytes = (byte[]) getBits(data, 16, (offset + 2));
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			val = bb.getShort();
			read = 18;
		} else if (type == 0x01) {
			val = ((Integer) getBits(data, 8, (offset + 2))).intValue();
			read = 10;
		} else if (type == 0x02) {
			val = 0;
		} else if (type == 0x03) {
			val = 256;
		}
		v.add(new Integer(offset + read));
		v.add(new Integer(val));
		return v;
	}

	/**
	 * Read a short value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the short value
	 */
	public static ArrayList getRawShort(int[] data, int offset) throws RuntimeException {
		byte[] bytes = (byte[]) getBits(data, 16, offset);
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		int val = bb.getShort();
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + 16));
		v.add(new Integer(val));
		return v;
	}

	/**
	 * Read a long value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the long value
	 */
	public static ArrayList getBitLong(int[] data, int offset) throws RuntimeException {
		int type = ((Integer) getBits(data, 2, offset)).intValue();
		int read = 2;
		int val = 0;
		if (type == 0x0) {
			byte[] bytes = (byte[]) getBits(data, 32, (offset + 2));
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			val = bb.getInt();
			read = 34;
		} else if (type == 0x01) {
			val = ((Integer) getBits(data, 8, (offset + 2))).intValue();
			read = 10;
		} else if (type == 0x02) {
			val = 0;
		} else {
			System.out.println("Bad type at bit offset: " + offset);
		}
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + read));
		v.add(new Integer(val));
		return v;
	}

	/**
	 * Read a long value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the long value
	 * @throws RuntimeException
	 */
	public static ArrayList getRawLong(int[] data, int offset) throws RuntimeException {
		ArrayList v = new ArrayList();
		// _val = struct.unpack('<l', _long)[0]
		byte[] bytes = (byte[]) getBits(data, 32, offset);
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		int val = bb.getInt();
		v.add(new Integer(offset + 32));
		v.add(new Integer(val));
		return v;
	}

	/**
	 * Read a char value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the char value
	 */
	public static ArrayList getRawChar(int[] data, int offset) throws RuntimeException {
		int charr = ((Integer) getBits(data, 8, offset)).intValue();
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + 8));
		v.add(new Integer(charr));
		return v;
	}

	/**
	 * Read a char value from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the char value
	 */
	public static ArrayList getModularChar(int[] data, int offset) throws RuntimeException {
		int val = 0;
		ArrayList bytes = new ArrayList();
		boolean read = true;
		int offsett = offset;
		int fac = 1;
		while (read) {
			int bytee = ((Integer) getBits(data, 8, offsett)).intValue();
			offsett = offsett + 8;
			if ((bytee & 0x80) == 0) {
				read = false;
				if ((bytee & 0x40) > 0) {
					fac = -1;
					bytee = bytee & 0xbf;
				}
			}
			bytes.add(new Integer(bytee & 0x7f));
		}
		if (bytes.size()==1) val = ((Integer)bytes.get(0)).intValue();
		else if (bytes.size()==2) val = ((Integer)bytes.get(0)).intValue() | (((Integer)bytes.get(1)).intValue() << 7);
		else if (bytes.size()==3) val = ((Integer)bytes.get(0)).intValue() | (((Integer)bytes.get(1)).intValue() << 7) | (((Integer)bytes.get(2)).intValue() << 14);
		else if (bytes.size()==4) val = ((Integer)bytes.get(0)).intValue() | (((Integer)bytes.get(1)).intValue() << 7) | (((Integer)bytes.get(2)).intValue() << 14) | (((Integer)bytes.get(3)).intValue() << 21);
		else System.out.println("Unexpected byte array length: " + bytes.size());
		ArrayList v = new ArrayList();
		v.add(new Integer(offsett));
		v.add(new Integer(fac * val));
		return v;
	}

	/**
	 * Read a String from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is the String
	 */
	public static ArrayList getTextString(int[] data, int offset) throws RuntimeException {
		int bitPos = offset;
		ArrayList bitShort = DwgUtil.getBitShort(data, bitPos);
		int newBitPos = ((Integer) bitShort.get(0)).intValue();
		int len = ((Integer) bitShort.get(1)).intValue();
		bitPos = newBitPos;
		int bitLen = len * 8;
		Object cosa = DwgUtil.getBits(data, bitLen, bitPos);
		String string;
		if (cosa instanceof byte[]) {
			string = new String((byte[]) cosa);
		} else {
			// string = ((Integer)cosa).toString();
			byte[] bytes = new byte[] { ((Integer) cosa).byteValue() };
			string = new String((byte[]) bytes);
		}
		bitPos = bitPos + bitLen;
		ArrayList v = new ArrayList();
		v.add(new Integer(bitPos));
		v.add(string);
		return v;
	}

	/**
	 * Read a CmColor from a group of unsigned bytes
	 *
	 * @param data
	 *            Array of unsigned bytes obtained from the DWG binary file
	 * @param offset
	 *            The current bit offset where the value begins
	 * @throws RuntimeException
	 *             If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value
	 *         that represents the new offset, and second is the int value
	 *         (handle of a DWG object)
	 */
	public static ArrayList getCmColor(int[] intData, int offset, String version) throws RuntimeException {

		ArrayList v = new ArrayList();
		int bitPos = offset;
		List val = getBitShort(intData, bitPos);
		bitPos = ((Integer) val.get(0)).intValue();
		v.add((Integer) val.get(1));		//color index

		//FIXME: para versiones superiores habrá que ampliar la condición
		if (version.equalsIgnoreCase("Autocad R2004, R2005, R2006")){

			val = getBitLong(intData, bitPos);
			bitPos = ((Integer) val.get(0)).intValue();
			v.add((Integer) val.get(1));	//RGB value

			val = getRawChar(intData, bitPos);
			bitPos = ((Integer) val.get(0)).intValue();
			Integer colorByte = ((Integer) val.get(1)); //.intValue();
			v.add(colorByte);				//Color Byte

			// Según la documentacion:
			// Color Byte & 1 ==> color name follows
			// Color Byte & 2 ==> book name follows
			if (((colorByte.intValue() & 0x01) != 0) || ((colorByte.intValue() & 0x02) != 0)) {
				val = getTextString(intData, bitPos);
				bitPos = ((Integer) val.get(0)).intValue();
				v.add(val.get(1));//color name or book name
			} else {
				v.add("");//color name or book name
			}
		}

		v.add(0, new Integer(bitPos));
		return v;
	}

	/**
	 * Read a int value (the handle of a DWG object) from a group of unsigned
	 * bytes
	 *
	 * @param data
	 *            Array of unsigned bytes obtained from the DWG binary file
	 * @param offset
	 *            The current bit offset where the value begins
	 * @throws RuntimeException
	 *             If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return ArrayList This ArrayList has two parts. First is an int value
	 *         that represents the new offset, and second is the int value
	 *         (handle of a DWG object)
	 */
	public static ArrayList getHandle(int[] data, int offset)
			throws RuntimeException {
		ArrayList v = new ArrayList();
		int code = ((Integer) DwgUtil.getBits(data, 4, offset)).intValue();
	    int counter = ((Integer)DwgUtil.getBits(data, 4, (offset + 4))).intValue();
		int read = 8;
		ArrayList hlist = new ArrayList();
		if (counter > 0) {
			int hlen = counter * 8;
			Object handle = DwgUtil.getBits(data, hlen, (offset + 8));
			read = read + hlen;
			if (hlen > 8) {
				byte[] handleBytes = (byte[]) handle;
				int[] handleInts = new int[handleBytes.length];
				// Hacerlos unsigned ...
				for (int i = 0; i < handleBytes.length; i++) {
					handleInts[i] = ByteUtils.getUnsigned(handleBytes[i]);
				}
				for (int i = 0; i < handleInts.length; i++) {
					hlist.add(new Integer(handleInts[i]));
				}
			} else {
				hlist.add(handle);
			}
		}
		v.add(new Integer(offset + read));
		v.add(new Integer(code));
		v.add(new Integer(counter));
		for (int i = 0; i < hlist.size(); i++) {
			v.add(hlist.get(i));
		}
		return v;
	}

	/**
	 * Read a int value (the size of a modular short) from a ByteBuffer
	 *
	 * @param bb Data given as a ByteBuffer
	 * @return int Size of the modular short
	 */
	public static int getModularShort(ByteBuffer bb) {
		ArrayList shorts = new ArrayList();
		bb.order(ByteOrder.BIG_ENDIAN);
		short shortt = bb.getShort();
		int size = 0;
		while ((shortt & 0x80) > 0) {
			shorts.add(new Short(shortt));
			shortt = bb.getShort();
		}
		shorts.add(new Short(shortt));
		for (int i = 0; i < shorts.size(); i++) {
			shortt = ((Short) shorts.get(i)).shortValue();
			shorts.set(i, new Integer(((shortt & 0xff00) >> 8) | ((shortt & 0xff) << 8)));
		}
		int slen = shorts.size();
		if (slen == 1) {
			size = (((Integer)shorts.get(0)).intValue()) & 0x7fff;//(new Integer(((Integer)shorts.get(0)).shortValue() & 0x7fff)).byteValue();
		} else if (slen == 2) {
			int tmp = ((Integer) shorts.get(0)).intValue();
			shorts.set(0, shorts.get(1));
			shorts.set(1, new Integer(tmp));
			size = (((((Integer)shorts.get(0)).intValue()) & 0x7fff) << 15) | (((((Integer)shorts.get(1)).intValue()) & 0x7fff));//(new Integer(((Integer)shorts.get(0)).shortValue() & 0x7fff)).byteValue();
		} else {
			System.out.println("Unexpected array length: " + slen);
		}
		return size;
	}

	/**
	 * Returns a set of bits from a group of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param count Bit counter
	 * @param offset The current bit offset where the value begins
	 * @throws RuntimeException If an unexpected bit value is found in the DWG file. Occurs
	 *             when we are looking for LwPolylines.
	 * @return This method returns an array of bytes or an int value
	 */
	public static Object getBits(int[] data, int count, int offset) throws RuntimeException {
		int idx = offset / 8;
		int bitidx = offset % 8;
		/**
		 * mask1: bit mask to apply to the current byte
		 * lshift: left shift amount of mask results
		 * mask2: bit mask to apply to the next byte
		 * rshift: right shift amount of the mask results
		 */
		int[][] maskTable = new int[][]{
		    {0xff, 0, 0x00, 0}, // bit offset == 0
				{ 0x7f, 1, 0x80, 7 }, // bit offset == 1
				{ 0x3f, 2, 0xc0, 6 }, // bit offset == 2
				{ 0x1f, 3, 0xe0, 5 }, // bit offset == 3
				{ 0x0f, 4, 0xf0, 4 }, // bit offset == 4
				{ 0x07, 5, 0xf8, 3 }, // bit offset == 5
				{ 0x03, 6, 0xfc, 2 }, // bit offset == 6
				{ 0x01, 7, 0xfe, 1 }, // bit offset == 7
		};
		int mask1 = maskTable[bitidx][0];
		int lsh = maskTable[bitidx][1];
		int mask2 = maskTable[bitidx][2];
		int rsh = maskTable[bitidx][3];
		int binc = 8 - bitidx;
		int read = 0;
		int rem = count;
		int bytee = 0x0;
		ArrayList bytes = new ArrayList();
		while (read < count) {
			int b1 = 0;
			if (rem > binc) {
				b1 = (data[idx] & mask1);
				read = read + binc;
				rem = rem - binc;
			} else {
				b1 = ((data[idx] & mask1) >> (8 - bitidx - rem));
				bytee = b1;
				read = read + rem;
				rem = 0;
			}
			if (read < count) {
				idx = idx + 1;
				if (rem > bitidx) {
					int b2 = (data[idx] & mask2);
					bytee = (b1 << lsh) | (b2 >> rsh);
					read = read + bitidx;
					rem = rem - bitidx;
				} else {
					int mask = maskTable[rem][2];
					int b2 = data[idx] & mask;
					bytee = (b1 << rem) | (b2 >> (8 - rem));
					read = read + rem;
					rem = 0;
				}
			}
			if (count > 8) {
				bytes.add(new Integer(bytee));
			}
		}
		if (bytes.size() > 0) {
			byte[] newBytes = new byte[bytes.size()];
			for (int i = 0; i < newBytes.length; i++) {
				newBytes[i] = ((Integer) bytes.get(i)).byteValue();
			}
			return newBytes;
		}
		return new Integer(bytee);
	}

	/**
	 * Test a bit obtained from a set of unsigned bytes
	 *
	 * @param data Array of unsigned bytes obtained from the DWG binary file
	 * @param offset The current bit offset where the value begins
	 * @return ArrayList This ArrayList has two parts. First is an int value that represents
	 * 		   the new offset, and second is a bit flag
	 */
	public static ArrayList testBit(int[] data, int offset) {
		int idx = offset / 8;
		int bitidx = offset % 8;
		int mask = 0x1 << (7 - bitidx);
		boolean val = false;
		if ((data[idx] & mask) > 0) val = true;
		ArrayList v = new ArrayList();
		v.add(new Integer(offset + 1));
		v.add(new Boolean(val));
		return v;
	}

	/**
	 * Convert bytes to machine value bytes
	 *
	 * @param data Input of array of bytes
	 * @return int[] Output of array of machine bytes
	 */
	public static int[] bytesToMachineBytes(byte[] data) {
		String[] dataString = new String[data.length];
		int[] dataOut = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			dataString[i] = HexUtil.bytesToHex(new byte[] { data[i] });
			Integer dataInt = Integer.decode("0x" + dataString[i]);
			dataOut[i] = dataInt.intValue();
		}
		return dataOut;
	}

	/**
	 * It receives the list procedent from getHandle method, and return the
	 * handle as int
	 *
     * */
	public static int handleToInt(List fromGetHandle) {

		int[] handle = new int[fromGetHandle.size() - 1];
		for (int j = 1; j < fromGetHandle.size(); j++) {
			handle[j - 1] = ((Integer) fromGetHandle.get(j)).intValue();
		}
		ArrayList handleVect = new ArrayList();
		for (int i = 0; i < handle.length; i++) {
			handleVect.add(new Integer(handle[i]));
		}
		return DwgUtil.handleBinToHandleInt(handleVect);


    }
	/**
	 * Obtain the int value of a handle given in binary format
	 *
	 * @param layerHandle Handle in binary format
	 * @return int Int value of the handle
	 */
	public static int handleBinToHandleInt(ArrayList layerHandle) {
		byte[] layerBytes = new byte[] { 0, 0, 0, 0 };
		if (layerHandle.size()>2) layerBytes[3] = (byte)((Integer)layerHandle.get(2)).intValue();
		if (layerHandle.size() > 3) {
			layerBytes[3] = (byte) ((Integer) layerHandle.get(3)).intValue();
			layerBytes[2] = (byte) ((Integer) layerHandle.get(2)).intValue();
		}
		if (layerHandle.size() > 4) {
			layerBytes[3] = (byte) ((Integer) layerHandle.get(4)).intValue();
			layerBytes[2] = (byte) ((Integer) layerHandle.get(3)).intValue();
			layerBytes[1] = (byte) ((Integer) layerHandle.get(2)).intValue();
		}
		if (layerHandle.size() > 5) {
			layerBytes[3] = (byte) ((Integer) layerHandle.get(5)).intValue();
			layerBytes[2] = (byte) ((Integer) layerHandle.get(4)).intValue();
			layerBytes[1] = (byte) ((Integer) layerHandle.get(3)).intValue();
			layerBytes[0] = (byte) ((Integer) layerHandle.get(2)).intValue();
		}
		int layer = ByteUtils.bytesToInt(layerBytes, new int[] { 0 });
		return layer;
	}

	/*
	 * TODO Ver si esto es necesario
	 * */
	public static int[] toIntArray(byte[] bytes) {
		String[] dataMachValString = new String[bytes.length];
		int[] data = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			dataMachValString[i] = HexUtil.bytesToHex(new byte[] { bytes[i] });
			Integer dataMachValShort = Integer.decode("0x" + dataMachValString[i]);
			data[i] = dataMachValShort.byteValue();
			data[i] = ByteUtils.getUnsigned((byte) data[i]);
		}// for
		return data;

	}
}
