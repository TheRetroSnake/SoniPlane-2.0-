package soni.plane.gs.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	public static String read(String file){
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.err.println(FileUtil.class +": Could not read file '"+ file +"'!");
		return null;
	}

}
