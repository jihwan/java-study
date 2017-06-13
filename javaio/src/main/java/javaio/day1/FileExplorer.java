package javaio.day1;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class FileExplorer {

	// File parent = new File("C:\\Users\\kosta");

	Scanner scanner = new Scanner(System.in);

	boolean hidden = false;

	public static void main(String[] args) throws IOException {

		FileExplorer fileExplorer = new FileExplorer();
		File parent = new File("/");
		fileExplorer.explore2(parent);
	}
	
	private void explore2(File parent) {
		
		File[] dirs = parent.listFiles((f) -> f.isHidden() == hidden && f.isDirectory()); //parent.listFiles(new DirectoryFilter(CONFIG_DISPLAY_HIDDEN));
		File[] files = parent.listFiles((f) -> f.isHidden() == hidden && f.isFile()); // parent.listFiles(new RealFileFilter(CONFIG_DISPLAY_HIDDEN));
		
		while (true) {
			int count = 1;
			
			System.out.println("--------------------------------------------------");
			System.out.println(" 0. back....");
			
			for (File dir: dirs) {
				String text = String.format("%2s. [D] %-50s - %s, %10s", count, dir.getName(), new Date(dir.lastModified()), dir.isHidden() ? "Hidden": "");
				System.out.println(text);
				count++;
			}
			for (File file: files) {
				String text = String.format("    %-50s - %s, %s bytes, %10s", file.getName(), new Date(file.lastModified()), file.length(), file.isHidden() ? "Hidden":"");
				System.out.println(text);
			}
			
			System.out.print(parent.getAbsolutePath() + " >> ");
			String input = scanner.nextLine();
			
			if ("exit".equals(input)) {
				System.exit(0);
			}
			else if ("0".equals(input)) {
				return;
			}
			explore2(dirs[Integer.valueOf(input) - 1]);
		}
		
		
	}

	private void explorer(File parent) {
		File[] dir = parent.listFiles((f) -> f.isHidden() == hidden && f.isDirectory());
		File[] fff = parent.listFiles((f) -> f.isHidden() == hidden && f.isFile());

		int count = 1;
		for (File file : dir) {
			System.out.println(count++ + " [D] " + file.getName());
		}

		for (File file : fff) {
			System.out.println(file.getName());
		}

		scan(dir, parent);
	}

	private void scan(File[] dir, File parent) {

		System.out.println("================================================================================== ");
		System.out.println(">>> ");

		// while (true) {
		String input = scanner.nextLine();

		if ("quit".equals(input)) {
			System.out.println("Bye~~~");
			System.exit(0);
		} else if ("0".equals(input)) {
			this.explorer(parent);
		} else {
			System.out.println(input);
			this.explorer(dir[Integer.parseInt(input) - 1]);
		}
		// }
	}

}
