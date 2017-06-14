package kosta.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import kosta.java.io.user.domain.Gender;
import kosta.java.io.user.domain.User;


public class DataUserTest {
	public static void main(String[] args) {
//		new DataUserTest().testWrite();
		new DataUserTest().testRead();
	}
	
	
	void testWrite() {
		File file = new File("data-user");
		User user = new User("a@b.c", "a", 11, false, Gender.Female);
		
		try (
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				DataOutputStream dos = new DataOutputStream(fileOutputStream);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
		) {
			
//			br.write(user.getEmail());
//			br.write(",");
//			br.write(user.getName());
//			br.write(",");
			dos.writeInt(user.getAge());
			writer.write(",");
			dos.writeBoolean(user.isManager());
			writer.write(",");
			writer.newLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	void testRead() {
		File file = new File("data-user");
		
		try (
				FileInputStream fis = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		) {
			
//			String readLine = reader.readLine();
//			System.out.println(readLine);
			
			System.out.println( dis.readInt() );
			System.out.println( Byte.toString(dis.readByte()) );
			System.out.println( dis.readBoolean() );
			System.out.println( dis.readByte() );
			
			
					
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
