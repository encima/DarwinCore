package com.encima.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {
	
	public Unzipper() {
		
	}

	public String unzip(String zip) {
		byte[] buffer = new byte[1024];
		String newPath = null;
		try {
			// Create output folder from name of zip
		 	newPath = zip.substring(0, zip.length() - 4);

		 	File zipFile = new File(zip);
		 	newPath = zipFile.getParent().toString();
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			ZipEntry ze;
			while((ze = zis.getNextEntry()) != null) {
				// Ignore all the shite that osx bundles it with, seriously WTF?
				File newFile = new File(newPath + File.separator + ze.getName());
				if(!newFile.getName().startsWith(".") && !newFile.toString().contains("__") && !newFile.getName().endsWith("sorted")) {
					System.out.println(newFile);
					// Only create if it is not a directory
					if(!ze.isDirectory()) {
						FileOutputStream fos = new FileOutputStream(newFile);
						int len;
						while ((len = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
						fos.close();
					}else{
						System.out.println("Making Path: " + newFile.getAbsolutePath());
						newFile.mkdirs();
					}
			}
		}
			zis.closeEntry();
			zis.close();
			new File(zip).delete();
			System.out.println("Unzipped!");
			return zipFile.getAbsolutePath().replace(".zip", "");
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Unzipper uz = new Unzipper();
		System.out.println(uz.unzip("/Users/encima/Dropbox/Projects/PhD/Java/GSN_DGFC/webapp/dwc_arch.zip"));
	}
}
