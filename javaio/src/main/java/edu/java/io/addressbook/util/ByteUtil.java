package edu.java.io.addressbook.util;

public class ByteUtil {

	/**
	 * long 값을 바이트 배열로 변환한다. 
	 * 
	 * @param longValue
	 * @return
	 */
	public static byte[] toBytes(long longValue) {
		//
		return new byte[] { 
			(byte) ((longValue >> 56) & 0xff),
			(byte) ((longValue >> 48) & 0xff), 
			(byte) ((longValue >> 40) & 0xff),
			(byte) ((longValue >> 32) & 0xff), 
			(byte) ((longValue >> 24) & 0xff),
			(byte) ((longValue >> 16) & 0xff), 
			(byte) ((longValue >>  8) & 0xff),
			(byte) ((longValue >>  0) & 0xff) 
		};
	}

	/**
	 * 바이트 배열을 long 값으로 변환한다. 
	 * 
	 * @param longBytes
	 * @return
	 */
	public static long toLong(byte[] longBytes) {
		//
		if (longBytes == null || longBytes.length != 8) {
			throw new NumberFormatException("The byte array for long value conversion is not valid."); 
		}

		return (long) (
				(long) (0xff & longBytes[0]) << 56 | 
				(long) (0xff & longBytes[1]) << 48 | 
				(long) (0xff & longBytes[2]) << 40 | 
				(long) (0xff & longBytes[3]) << 32 | 
				(long) (0xff & longBytes[4]) << 24 | 
				(long) (0xff & longBytes[5]) << 16 | 
				(long) (0xff & longBytes[6]) << 8  | 
				(long) (0xff & longBytes[7]) << 0);
	}

	/**
	 * 바이트 배열을 int 값으로 변환한다. 
	 * 
	 * @param intBytes
	 * @return
	 */
	public static int toInt(byte[] intBytes) {
		//
		if (intBytes == null || intBytes.length != 4) {
			throw new NumberFormatException("The byte array for int value conversion is not valid."); 
		}

		return (int) (
			(int) (0xff & intBytes[0]) << 24 | 
			(int) (0xff & intBytes[1]) << 16 | 
			(int) (0xff & intBytes[2]) << 8  | 
			(int) (0xff & intBytes[3]) << 0
		);
	}

	/**
	 * int 값을 바이트 배열로 변환한다. 
	 * 
	 * @param intValue
	 * @return
	 */
	public static byte[] toBytes(int intValue) {
		//
		return new byte[] { 
			(byte) ((intValue >> 24) & 0xff),
			(byte) ((intValue >> 16) & 0xff), 
			(byte) ((intValue >>  8) & 0xff),
			(byte) ((intValue >>  0) & 0xff) 
		};
	}

	/**
	 * 바이트 배열을 short 값으로 변환한다. 
	 * 
	 * @param shortBytes
	 * @return
	 */
	public static short toShort(byte[] shortBytes) {
		//
		if (shortBytes == null || shortBytes.length != 2) {
			throw new NumberFormatException("The byte array for short value conversion is not valid."); 
		}

		return (short) (
			(short) (0xff & shortBytes[0]) << 8  | 
			(short) (0xff & shortBytes[1]) << 0
		);	
	}
	
	/**
	 * short 값을 바이트 배열로 변환한다. 
	 * 
	 * @param shortValue
	 * @return
	 */
	public static byte[] toBytes(short shortValue) {
		//
		return new byte[] { 
			(byte) ((shortValue >> 8) & 0xff),
			(byte) ((shortValue >> 0) & 0xff) 
		};
	}
}
