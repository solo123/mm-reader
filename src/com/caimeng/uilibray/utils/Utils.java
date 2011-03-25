package com.caimeng.uilibray.utils;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Utils
{
  public static Image loadImage(String imageName)
  {
    try
    {
      return Image.createImage(imageName);
    } catch (IOException e) {
      e.printStackTrace(); }
    return null;
  }

  private static int[] rescalaArray(int[] ini, int x, int y, int x2, int y2)
  {
    int[] out = new int[x2 * y2];
    for (int yy = 0; yy < y2; ++yy) {
      int dy = yy * y / y2;
      for (int xx = 0; xx < x2; ++xx) {
        int dx = xx * x / x2;
        out[(x2 * yy + xx)] = ini[(x * dy + dx)];
      }
    }
    return out;
  }

  public static Image scale(Image temp, int maxX, int maxY)
  {
    int[] rgb = new int[temp.getWidth() * temp.getHeight()];

    temp.getRGB(
      rgb, 
      0, 
      temp.getWidth(), 
      0, 
      0, 
      temp.getWidth(), 
      temp.getHeight());

    int[] rgb2 = 
      rescalaArray(rgb, temp.getWidth(), temp.getHeight(), maxX, maxY);

    Image temp2 = Image.createRGBImage(rgb2, maxX, maxY, true);
    return temp2;
  }

  public static Image scale(String imagePath, int maxX, int maxY)
    throws IOException
  {
    return scale(Image.createImage(imagePath), maxX, maxY);
  }

  public static Image load(String imagePath)
  {
    try
    {
      return Image.createImage(imagePath);
    }
    catch (Throwable t) {
      t.printStackTrace(); }
    return null;
  }
}