package com.caimeng.uilibray.event;


import com.caimeng.uilibray.button.ImageButton;

public abstract interface NineGridListener
{
  public abstract void selected(ImageButton paramImageButton);

  public abstract void clicked(ImageButton paramImageButton);
}