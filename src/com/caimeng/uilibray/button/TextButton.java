package com.caimeng.uilibray.button;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;


public class TextButton extends ImageButton
{
  private String label;

  public TextButton(String label, String imageRes)
  {
    super(imageRes);
    setLabel(label);
  }

  public String getLabel()
  {
    return this.label;
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

  public void paint(Graphics gc, int x, int y, int width, int height)
  {
    Font f = Font.getFont(0, 0, 8);
    int fontHeight = f.getHeight();
    int fontWidth = f.stringWidth(getLabel());
    int size = height - fontHeight+5;

    super.paint(gc, x, y, width, size);

    if(super.isStretchIcon()){
    	
    	if (super.isSelected()) {
    		ImageDiv.drawJiuGong(gc, UIManager.menu_bright, x + width / 2 - fontWidth / 2-10 , y + size-1,fontWidth+16, fontHeight+3);
    		gc.setColor(255, 255, 255);
    	} else {
    		gc.setColor(52, 52, 52);
    	}
    }else{
    	  if (super.isSelected()) {
    	      gc.setColor(super.getParent().getTitleForeground());
    	      gc.fillRect(x + width / 2 - fontWidth / 2, y + size, fontWidth, fontHeight);
    	      gc.setColor(super.getParent().getTitleBackground());
    	    }
    	    else {
    	      gc.setColor(super.getParent().getTitleForeground());
    	    }
    }
    gc.setFont(f);
    gc.drawString(getLabel(), x + width / 2 - fontWidth / 2, y + size-1, Graphics.LEFT|Graphics.TOP);
  }
}