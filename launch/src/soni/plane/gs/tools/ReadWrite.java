package soni.plane.gs.tools;

import java.io.*;
public class ReadWrite {
	/* read String from file */
	public static String read(File f) {
		try {
			/* create inputStreams and Scanners */
			InputStream is = new FileInputStream(f);
			java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
			/* read until done */
			String data = s.hasNext() ? s.next() : "";
			/* closer scanner and exit */
			s.close();
			return data;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/* return null */
		return null;
	}

	public static boolean write(File f, String data) {
		/* create PrintWriter to the file (no append) */
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, false)))) {
			/* write to the file */
			out.print(data);
			/* close the file */
			out.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		/* failed to writer */
		return false;
	}
}
