package com.caimeng.uilibray.event;

public abstract interface TableListener
{
  public abstract void cellChanged(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract void keyPressed(int paramInt1, int paramInt2, int paramInt3);
}