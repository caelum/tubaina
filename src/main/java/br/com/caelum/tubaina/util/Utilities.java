package br.com.caelum.tubaina.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;

public class Utilities {

	private static final Logger LOG = Logger.getLogger(Utilities.class);

	public static void getImageWithLogo(final File srcImage, final ImageOutputStream destinationImage,
			final String format, final File logo) {
		try {
			BufferedImage image = ImageIO.read(srcImage);
			BufferedImage image2 = ImageIO.read(logo);

			int w = image2.getWidth();
			int h = image2.getHeight();
			if ((w < 1.5 * image.getWidth()) || (h < 1.5 * image.getHeight())) {
				return;
			}
			int rectangleWidth = Math.min(image.getWidth(), w);
			int rectangleHeigth = Math.min(image.getHeight(), h);

			Graphics2D graphics = image.createGraphics();
			graphics.drawImage(image2, image.getWidth() - w, image.getHeight() - h, rectangleWidth, rectangleHeigth,
					null);

			graphics.dispose();

			ImageIO.write(image, format, destinationImage);
		} catch (IOException e) {
			LOG.warn("Unable to add the logo to image: '" + destinationImage.toString());
		}
	}

	public static String getFormatName(final File srcImage) {
		return FilenameUtils.getExtension(srcImage.getPath()).toUpperCase();
	}

	public static int getImageWidth(final File srcImage) {
		try {
			BufferedImage src = ImageIO.read(srcImage);
			return src.getWidth();
		} catch (IOException e) {
			LOG.warn("Couldn't get image width for: " + srcImage.getPath());
			throw new TubainaException("Couldn't get image width");
		}
	}

	public static void resizeImage(final File srcImg, final ImageOutputStream destImg, final String format,
			final int pageWidth, final double ratio) {
		if (ratio <= 0 || ratio > 1) {
			throw new IllegalArgumentException("Ratio must be between 0 and 1");
		}
		try {
			BufferedImage src = ImageIO.read(srcImg);
			double newRatio = (pageWidth * ratio) / src.getWidth();
			int width = (int) Math.round(Math.ceil((newRatio * src.getWidth())));
			int height = (int) Math.round(Math.ceil(newRatio * src.getHeight()));
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			Graphics2D graphics = dest.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			AffineTransform transform = AffineTransform.getScaleInstance(newRatio, newRatio);
			graphics.drawRenderedImage(src, transform);
			ImageIO.write(dest, format, destImg);
		} catch (IOException e) {
			LOG.warn("Unable to resize image: '" + destImg.toString());
		}
	}

	public static int getMinIndent(final String string) {
		int min = Integer.MAX_VALUE;
		for (String s : string.split("\n")) {
			Pattern p = Pattern.compile("\\S");
			Matcher m = p.matcher(s);
			if (m.find() && m.start() < min) {
				min = m.start();
			}
		}
		return min;
	}

	public static int maxLineLength(final String string) {
		int max = 0;
		for (String s : string.split("\n")) {
			if (s.length() > max) {
				max = s.length();
			}
		}
		return max;
	}

	public String getFileType(final String fileName) {
		int index = fileName.length() - 1;
		while (index >= 0) {
			if (fileName.charAt(index) == '.') {
				return fileName.substring(index + 1).toLowerCase();
			}
			index--;
		}
		return null;
	}

	/**
	 * To replace accented characters in a String by unaccented equivalents.
	 * From org.apache.lucene.analysis.ISOLatin1AccentFilter, changed for UTF8
	 * support
	 */
	public final static String removeAccents(final String input) {
		final StringBuffer output = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			switch (input.charAt(i)) {
			case '\u00C0': // À
			case '\u00C1': // Á
			case '\u00C2': // Â
			case '\u00C3': // Ã
			case '\u00C4': // Ä
			case '\u00C5': // Å
				output.append("A");
				break;
			case '\u00C6': // Æ
				output.append("AE");
				break;
			case '\u00C7': // Ç
				output.append("C");
				break;
			case '\u00C8': // È
			case '\u00C9': // É
			case '\u00CA': // Ê
			case '\u00CB': // Ë
				output.append("E");
				break;
			case '\u00CC': // Ì
			case '\u00CD': // Í
			case '\u00CE': // Î
			case '\u00CF': // Ï
				output.append("I");
				break;
			case '\u00D0': // Ð
				output.append("D");
				break;
			case '\u00D1': // Ñ
				output.append("N");
				break;
			case '\u00D2': // Ò
			case '\u00D3': // Ó
			case '\u00D4': // Ô
			case '\u00D5': // Õ
			case '\u00D6': // Ö
			case '\u00D8': // Ø
				output.append("O");
				break;
			case '\u0152': // Œ
				output.append("OE");
				break;
			case '\u00DE': // Þ
				output.append("TH");
				break;
			case '\u00D9': // Ù
			case '\u00DA': // Ú
			case '\u00DB': // Û
			case '\u00DC': // Ü
				output.append("U");
				break;
			case '\u00DD': // Ý
			case '\u0178': // Ÿ
				output.append("Y");
				break;
			case '\u00E0': // à
			case '\u00E1': // á
			case '\u00E2': // â
			case '\u00E3': // ã
			case '\u00E4': // ä
			case '\u00E5': // å
				output.append("a");
				break;
			case '\u00E6': // æ
				output.append("ae");
				break;
			case '\u00E7': // ç
				output.append("c");
				break;
			case '\u00E8': // è
			case '\u00E9': // é
			case '\u00EA': // ê
			case '\u00EB': // ë
				output.append("e");
				break;
			case '\u00EC': // ì
			case '\u00ED': // í
			case '\u00EE': // î
			case '\u00EF': // ï
				output.append("i");
				break;
			case '\u00F0': // ð
				output.append("d");
				break;
			case '\u00F1': // ñ
				output.append("n");
				break;
			case '\u00F2': // ò
			case '\u00F3': // ó
			case '\u00F4': // ô
			case '\u00F5': // õ
			case '\u00F6': // ö
			case '\u00F8': // ø
				output.append("o");
				break;
			case '\u0153': // œ
				output.append("oe");
				break;
			case '\u00DF': // ß
				output.append("ss");
				break;
			case '\u00FE': // þ
				output.append("th");
				break;
			case '\u00F9': // ù
			case '\u00FA': // ú
			case '\u00FB': // û
			case '\u00FC': // ü
				output.append("u");
				break;
			case '\u00FD': // ý
			case '\u00FF': // ÿ
				output.append("y");
				break;
			default:
				output.append(input.charAt(i));
				break;
			}
		}
		return output.toString();
	}

	public static String toDirectoryName(final Integer num, String title) {

		title = titleSlug(title);

		if (num != null) {
			return String.format("%02d-%s", num, title);
		}
		return title;
	}
	public static String toDirectoryName(String title) {
	    return toDirectoryName(null, title);
	}

    public static String titleSlug(String title) {
        return new TitleSlug(title).toString();
    }
}
