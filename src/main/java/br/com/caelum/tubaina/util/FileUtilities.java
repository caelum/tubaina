package br.com.caelum.tubaina.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class FileUtilities {

	static final Logger logger = Logger.getLogger(FileUtilities.class);

	public static void copy(final File src, final File dest) throws IOException {

		OutputStream stream = new FileOutputStream(dest);
		FileInputStream fis = new FileInputStream(src);
		byte[] buffer = new byte[16384];
		while (fis.available() != 0) {
			int read = fis.read(buffer);
			stream.write(buffer, 0, read);
		}

		stream.flush();
	}

	public static void copyRecursive(final File fromDirectory, final File toDirectory) throws IOException {
		if (!fromDirectory.isDirectory() || !toDirectory.isDirectory()) {
			throw new IllegalArgumentException("Both fromDirectory and toDirectory must be directories");
		}
		File newDirectory = new File(toDirectory, fromDirectory.getName());
		newDirectory.mkdir();

		for (File f : fromDirectory.listFiles()) {
			if (f.isDirectory()) {
				copyRecursive(f, newDirectory);
			} else {
				File newFile = new File(newDirectory, f.getName());
				copy(f, newFile);
			}
		}
	}

	public static boolean contentEquals(final File file1, final File file2, final FilenameFilter filter)
			throws IOException {
		if (!file1.isDirectory() || !file2.isDirectory()) {
			throw new IllegalArgumentException("Both files must be directories");
		}
		List<String> subfiles1 = new ArrayList<String>();
		List<String> subfiles2 = new ArrayList<String>();
		Collections.addAll(subfiles1, file1.list(filter));
		Collections.addAll(subfiles2, file2.list(filter));

		for (String f : subfiles1) {
			if (!subfiles2.contains(f)) {
				return false;
			}
			File f1 = new File(file1, f);
			File f2 = new File(file2, f);
			if (f1.isDirectory() && !contentEquals(f1, f2, filter)) {
				return false;
			} else if (!f1.isDirectory() && !FileUtils.contentEquals(f1, f2)) {
				return false;
			}

		}
		return true;
	}

	public static void copyDirectoryToDirectory(final File srcDir, final File destDir, final FilenameFilter filter)
			throws IOException {
		String srcName = FilenameUtils.getName(srcDir.getPath());

		File copied = new File(destDir, srcName);
		copied.mkdir();

		for (File f : srcDir.listFiles(filter)) {
			if (f.isDirectory()) {
				copyDirectoryToDirectory(f, copied, filter);
			} else {
				FileUtils.copyFileToDirectory(f, copied);
			}
		}
	}
}
