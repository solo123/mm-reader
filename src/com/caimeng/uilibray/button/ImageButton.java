package com.caimeng.uilibray.button;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.Themeable;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.form.NineGrid;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.Utils;



public class ImageButton 
{
  public static final int STYLE_ROUND = 0;
  public static final int STYLE_SQUARE = 1;
  public static int OUTSET = 6;//(net.sourceforge.mewt.WidgetUtils.isBlackberry()) ? 10 : 6;
  private Image sizedImage;
  private Image sizedImageSel;
  private String imageRes;
  private boolean selected;
  private Command command;
  private Themeable parent;
  private int style;
  private boolean stretchIcon;
  private int index;
  private boolean needsetoffset=false;
  private int boxWidth=0;
  private int boxHeight=0;
  public void setBoxWidth(int width){
	  boxWidth=width;
  }
  
public void setBoxHeight(int height){
	boxHeight=height;
}
  private ImageButton()
  {
    setSelected(false);
    setStyle(0);
    setStretchIcon(true);
  }

  public ImageButton(String image)
  {
    setImage(image);
  }

  public int getStyle()
  {
    return this.style;
  }

  public void setStyle(int style)
  {
    this.style = style;
  }

  public boolean isSelected()
  {
    return this.selected;
  }

  public void setSelected(boolean s)
  {
    this.selected = s;
  }

  public void paint(Graphics gx, int x, int y, int width, int height)
  {
	  
	  
	  if(NineGrid.mode==1){
		   Graphics gc=gx;
		    if (!isSelected()) {
		      if (this.sizedImageSel == null) {
		        this.sizedImageSel = Image.createImage(width, height);
		        gc = this.sizedImageSel.getGraphics();
		      }
		      render(gx, x, y, width, height);
		    } else {
		      if (this.sizedImage == null) {
		        this.sizedImage = Image.createImage(width, height);
		        gc = this.sizedImage.getGraphics();
		      }
		      render(gx, x, y, width, height);
		    }
	  }else{
		    Graphics gc;
		    if (!isSelected()) {
		      if (this.sizedImageSel == null) {
		        this.sizedImageSel = Image.createImage(width, height);
		        gc = this.sizedImageSel.getGraphics();
		        render(gc, 0, 0, width, height);
		      }
		      gx.drawImage(this.sizedImageSel, x, y, 20);
		    }
		    else {
		      if (this.sizedImage == null) {
		        this.sizedImage = Image.createImage(width, height);
		        gc = this.sizedImage.getGraphics();
		        render(gc, 0, 0, width, height);
		      }
		    
		      gx.drawImage(this.sizedImage, x, y, 20);
		      
		    }
	  }
  }
  
  /*public boolean needoffset(){
	  return needsetoffset;
  }*/

  private void render(Graphics gc, int x, int y, int width, int height)
  {
//    gc.setColor(this.parent.getBackground());
//    gc.fillRect(x, y, width, height);

    int size = height - 1;
    int angle = size / 4;

    int half_w = width / 2;
    int half_s = size / 2;

   /* if (isSelected()) {
      gc.setColor(this.parent.getButtonSelLight());
      gc.fillRoundRect(x + half_w - half_s, y, size, size, angle, angle);
      gc.setColor(this.parent.getButtonSelDark());
      gc.fillRoundRect(x + half_w - half_s, y + half_s, size, half_s, angle, angle);
      gc.setColor(this.parent.getButtonSelDark());
      gc.fillRoundRect(x + half_w - half_s , y + angle, half_s, half_s, angle, angle);
      //gc.setColor(this.parent.getButtonSelLight());
      //gc.fillRoundRect(x + half_w - half_s, y + half_s - angle, half_s, half_s, angle, angle);
    }
    else {
      gc.setColor(this.parent.getButtonBgLight());
      gc.fillRoundRect(x + half_w - half_s, y, size, size, angle, angle);
      gc.setColor(this.parent.getButtonBgDark());
      gc.fillRoundRect(x + half_w - half_s, y + half_s, size, half_s, angle, angle);
      gc.setColor(this.parent.getButtonBgDark());
      gc.fillRoundRect(x + half_w - half_s + half_s, y + angle, half_s, half_s, angle, angle);
     // gc.setColor(this.parent.getButtonBgLight());
      //gc.fillRoundRect(x + half_w - half_s, y + half_s - angle, half_s, half_s, angle, angle);
    }*/

    int INSET = CMForm.frm_Height>220?width/20:0;


//    Image image = Utils.loadImage(this.imageRes);
    if (isStretchIcon()) {
//    	if(index<4)
    	int w=0;
    	if(CMForm.frm_Height>220){
    		w=58;
    	}else{
    		w=44;
    	}
    	ImageBuilder.drawSmallImage(gc, UIManager.review_home, x + width/2 - w/2, y + INSET+5, w, w, w*index, 0);
//    	ImageBuilder.drawSmallImage(gc, UIManager.review_home, x + half_w - half_s + INSET, y + INSET+5, w, w, w*index, 0);
    	gc.setClip(0, 0, CMForm.frm_Width, CMForm.frm_Height);
//    	else
//    		gc.drawImage(UIManager.ico_list,  x + half_w - half_s + INSET, y + INSET, 0);
    	//      gc.drawImage(Utils.scale(image, size - INSET * 2, size - INSET * 2), x + half_w - half_s + INSET, y + INSET, 20);
    } else if(imageRes.substring(0).equals("/")){
   	 Image image = Utils.loadImage(this.imageRes);
		gc.drawImage(image, x + half_w - image.getWidth() / 2, y + half_s - image.getHeight() / 2, 20);
	}else
	{
	    gc.setColor(this.parent.getBackground());
	    gc.fillRect(x, y, width, height);
		if (isSelected()) {
		      gc.setColor(this.parent.getButtonSelLight());
		      gc.fillRoundRect(x + half_w - half_s, y, size, size, angle, angle);
		      gc.setColor(this.parent.getButtonSelDark());
		      gc.fillRoundRect(x + half_w - half_s, y + half_s, size, half_s, angle, angle);
		      gc.setColor(this.parent.getButtonSelDark());
		      gc.fillRoundRect(x + half_w - half_s , y + angle, half_s, half_s, angle, angle);
		      //gc.setColor(this.parent.getButtonSelLight());
		      //gc.fillRoundRect(x + half_w - half_s, y + half_s - angle, half_s, half_s, angle, angle);
		    }
		    else {
		      gc.setColor(this.parent.getButtonBgLight());
		      gc.fillRoundRect(x + half_w - half_s, y, size, size, angle, angle);
		      gc.setColor(this.parent.getButtonBgDark());
		      gc.fillRoundRect(x + half_w - half_s, y + half_s, size, half_s, angle, angle);
		      gc.setColor(this.parent.getButtonBgDark());
		      gc.fillRoundRect(x + half_w - half_s + half_s, y + angle, half_s, half_s, angle, angle);
		     // gc.setColor(this.parent.getButtonBgLight());
		      //gc.fillRoundRect(x + half_w - half_s, y + half_s - angle, half_s, half_s, angle, angle);
		    }

		    switch (getStyle())
		    {
		    case 1:
		      gc.setColor(this.parent.getBorder1());
		      gc.drawRoundRect(x + half_w - half_s, y, size, size, angle, angle);
		      gc.setColor(this.parent.getBorder2());
		      gc.drawRoundRect(x + half_w - half_s + 1, y + 1, size - 2, size - 2, angle, angle);
		      break;
		    case 0:
		      gc.setColor(this.parent.getBorder2());
		      gc.drawArc(x + half_w - half_s, y, size - 2, size - 2, 0, 360);
		      gc.setColor(this.parent.getBorder1());
		      gc.drawArc(x + half_w - half_s + 1, y + 1, size - 4, size - 4, 0, 360);
		      clearOuterCircle(gc, this.parent.getBackground(), x, y, width, height, x + half_w - half_s - 1, y - 1, size, size);
		    }
		    
		Font font = Font.getFont(0, 0, 8);
		gc.setFont(font);
		gc.setColor(0x00);
		int w = font.stringWidth(imageRes);
		if(w>=size)
		{
			String str = imageRes;
			int row = (w/size)+(w%size!=0?1:0);
			int num = imageRes.length()/row;
			int sy = (height-(font.getHeight()*row+((row-1)*2)))/2;
			for(int i=0;i<row;i++)
			{
				int l = str.length();
				String s = str.substring(0, num>l||i==row-1?l:num);
				gc.drawString(s, (width-font.stringWidth(s))/2, sy+i*(font.getHeight()+2), 0);
				if(num<l)
					str = str.substring(num);
			}
		}else
		{
			gc.drawString(imageRes, (width-font.stringWidth(imageRes))/2, (height-font.getHeight())/2, 0);
		}
	}
  }

  private void clearOuterCircle(Graphics gc, int color, int x, int y, int w, int h, int cx, int cy, int cw, int ch)
  {
    gc.setColor(color);
    gc.setClip(x, y, w, h);
    gc.setStrokeStyle(0);
    for (int i = 0; i < cw / 4; ++i) {
		gc.drawArc(cx - i, cy - i, cw + i * 2, ch + i * 2, 0, 360);
	}
  }
	public void setindex(int index){
		this.index=index;
		
	}
  public String getImage()
  {
    return this.imageRes;
  }

  public void setImage(String image)
  {
    this.imageRes = image;
  }

  public Command getCommand()
  {
    return this.command;
  }

  public void setCommand(Command command)
  {
    this.command = command;
  }

  public void setParent(Themeable formCommon)
  {
    this.parent = formCommon;
  }

  public Themeable getParent()
  {
    return this.parent;
  }

  public boolean isStretchIcon()
  {
    return this.stretchIcon;
  }

  public void setStretchIcon(boolean stretchIcon)
  {
    this.stretchIcon = stretchIcon;
  }
}