package javaio.day2;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetTest {

	public static void main(String[] args) {
		
		
		Charset charset = StandardCharsets.UTF_8;
		
		
		System.out.println( charset.isSupported( Charset.forName("UTF8").name() ) );
		charset.availableCharsets().values().forEach(System.out::println);
	}
}
