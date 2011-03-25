package com.caimeng.uilibray.common;


public abstract interface Themeable
{
  public abstract void applyTheme(Theme paramTheme);

  public abstract int getForeground();

  public abstract void setForeground(int paramInt);

  public abstract int getBackground();

  public abstract void setBackground(int paramInt);

  public abstract int getSelectedBackground();

  public abstract void setSelectedBackground(int paramInt);

  public abstract int getSelectedForeground();

  public abstract void setSelectedForeground(int paramInt);

  public abstract int getBorder1();

  public abstract void setBorder1(int paramInt);

  public abstract int getBorder2();

  public abstract void setBorder2(int paramInt);

  public abstract int getButtonBgDark();

  public abstract int getButtonBgLight();

  public abstract int getButtonSelDark();

  public abstract int getButtonSelLight();

  public abstract int getTitleBackground();

  public abstract int getTitleForeground();
}