package javaio.day1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyTest {
	public static void main(String[] args) {
		try ( FileInputStream fis = new FileInputStream(new File("./sss.txt"));
				FileOutputStream fos = new FileOutputStream(new File("./ttt.txt")); ) {
			int read;
			while ( (read = fis.read()) != -1 ) {
				fos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
