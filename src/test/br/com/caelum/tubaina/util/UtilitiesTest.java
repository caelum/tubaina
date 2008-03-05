package br.com.caelum.tubaina.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.junit.Assert;
import org.junit.Test;


public class UtilitiesTest {

	@Test
	public void testAddCaelumLogoInPngImage() throws IOException{
		File baseImage = new File("src/test/basePngImage.png");
		File destImage = new File("src/test/basePngDone.png");
		ImageOutputStream image = new FileImageOutputStream(destImage);
		Utilities.getImageWithLogo(baseImage, image, "PNG", new File ("src/templatesCaelum/logo.png"));
		image.close();
		Assert.assertTrue(destImage.exists());
	}
	
	@Test
	public void testAddCaelumLogoInJpgImage() throws IOException{
		File baseImage = new File("src/test/baseJpgImage.jpg");
		File destImage = new File("src/test/baseJpgDone.jpg");
		ImageOutputStream image = new FileImageOutputStream(destImage);
		Utilities.getImageWithLogo(baseImage, image, "JPG", new File ("src/templatesCaelum/logo.png"));
		image.close();
		Assert.assertTrue(destImage.exists());
	}
	
	@Test
	public void testAddCaelumLogoOnResizedImage() throws IOException{
		File baseImage = new File("src/test/basePngImage.png");
		File destImage = new File("src/test/basePngResized.png");
		ImageOutputStream image = new FileImageOutputStream(destImage);
		Utilities.resizeImage(baseImage, image, "PNG", 700, 0.70);
		image.close();
		image = new FileImageOutputStream(destImage);
		Utilities.getImageWithLogo(destImage, image, "PNG", new File ("src/templatesCaelum/logo.png"));
		image.close();
		Assert.assertTrue(destImage.exists());
	}
}
