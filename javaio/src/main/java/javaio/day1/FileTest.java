package javaio.day1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FileTest {
	
	
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		File file = new File("./sss.txt");

		System.out.println(file.getName());
		System.out.println(file.length());

		System.out.println(File.separator);
		System.out.println(File.separatorChar);

		System.out.println(File.pathSeparator);
		System.out.println(File.pathSeparatorChar);

		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getCanonicalPath());

		System.out.println(new File("./javaio/sss.txt").getAbsolutePath());
		
		System.out.println(">>> " + new URL("file://\\./sss.txt").getPath() );
		System.out.println(">>> " + new File( new URL("file://\\./sss.txt").getPath() ).length() );
	}
}
