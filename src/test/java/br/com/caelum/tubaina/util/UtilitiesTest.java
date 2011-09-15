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
	
	@Test
	public void testToDirectoryName() {
		//TODO: missing other bizarre case tests
		Assert.assertEquals("01-o-que-e-java", Utilities.toDirectoryName(1, "O que é Java?"));
		Assert.assertEquals("10-o-que-e-java", Utilities.toDirectoryName(10, "O    que\t é Java?"));
		Assert.assertEquals("05-c-que-e-java", Utilities.toDirectoryName(5, "Ç  %  que\t é Java?"));
		Assert.assertEquals("08-c-que-e-java", Utilities.toDirectoryName(8, " $  Ç  %  que\t é Java?"));
	}
}
