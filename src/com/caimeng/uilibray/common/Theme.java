package com.caimeng.uilibray.common;


import javax.microedition.lcdui.Display;

public class Theme
{
  private static Theme theme;
  private Display display;
  public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;
  public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;
  public static final int COLOR_FOREGROUND = 1;
  public static final int COLOR_BACKGROUND = 0;
  public static final int COLOR_BORDER = 4;
  public static final int COLOR_HIGHLIGHTED_BORDER = 5;
  public static final int COLOR_TITLE_BACKGROUND = 123;
  public static final int COLOR_TITLE_FOREGROUND = 124;
  public static final int COLOR_A_LIGHT = 125;
  public static final int COLOR_A_DARK = 126;
  public static final int COLOR_B_LIGHT = 127;
  public static final int COLOR_B_DARK = 128;
  public static final int COLOR_C_LIGHT = 129;
  public static final int COLOR_C_DARK = 130;

  public static Theme getTheme()
  {
    if (theme == null) {
		theme = new Theme();
	}

    return theme;
  }

  public int getColor(int code)
  {
    return getColorBlue(code);
  }

  public int getColorBlue(int code)
  {
    switch (code) {
    case 0:
      return 14478329;
    case 1:
      return 107;
    case 3:
      return 2040193;
    case 2:
      return 12573684;
    case 4:
      return 0;
    case 5:
      return 16777215;
    case 123:
      return 3773666;
    case 124:
      return 107;
    case 125:
      return 8173292;
    case 126:
      return 3773666;
    case 127:
      return 14478329;
    case 128:
      return 12573684;
    case 129:
      return 16777215;
    case 130:
      return 4219008; }
    return 0;
  }

  public int getColorDefault(int code)
  {
    switch (code) {
    case 0:
      return this.display.getColor(0);
    case 1:
      return this.display.getColor(1);
    case 3:
      return this.display.getColor(3);
    case 2:
      return this.display.getColor(2);
    case 4:
      return this.display.getColor(4);
    case 5:
      return this.display.getColor(5);
    case 123:
      return this.display.getColor(3);
    case 124:
      return this.display.getColor(2);
    case 125:
      return this.display.getColor(3);
    case 126:
      return this.display.getColor(2);
    case 127:
      return this.display.getColor(0);
    case 128:
      return this.display.getColor(1);
    case 129:
      return this.display.getColor(5);
    case 130:
      return this.display.getColor(4); }
    return 0;
  }

  public int getColorUbuntu(int code)
  {
    switch (code) {
    case 0:
      return 14535867;
    case 1:
      return 16772778;
    case 3:
      return 16755285;
    case 2:
      return 15623680;
    case 4:
      return 0;
    case 5:
      return 16777215;
    case 124:
      return 13408631;
    case 123:
      return 13373696;
    case 125:
      return 5579264;
    case 126:
      return 5583650;
    case 127:
      return 16772778;
    case 128:
      return 16755285;
    case 129:
      return 15654365;
    case 130:
      return 6684672; }
    return 0;
  }

  public Display getDisplay()
  {
    return this.display;
  }

  public void setDisplay(Display display)
  {
    this.display = display;
  }
}