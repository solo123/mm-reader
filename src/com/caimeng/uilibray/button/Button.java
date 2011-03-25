package com.caimeng.uilibray.button;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;



public class Button extends AbstructButton {

	public Button() {
		super();
	}

	private String ID;
	
	private int type;
	
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Button(int x, int y, int width, int height, String label) {
		super(x, y, width, height);
		this.text = label;
		int charsWidth = smallFont.stringWidth(label);
		if (charsWidth > this.width) {
			this.width = charsWidth;
		}
		int charsHeight = smallFont.getHeight();
		if (charsHeight > this.height) {
			this.height = charsHeight;
		}
	}

	public void setLabel(String label) {
		this.text = label;
		int charsWidth = smallFont.stringWidth(label);
		if (charsWidth > width) {
			width = charsWidth;
		}
		int charsHeight = smallFont.getHeight();
		if (charsHeight > height) {
			height = charsHeight;
		}
	}

	public void paint(Graphics g) {
		//g.setClip(0, 0, this.ui.xWForm.getWidth(), this.ui.xWForm.getHeight());
		if (focus) {
			if (keyPressed) {
			/*	ImageDiv.drawJiuGong(g, UIManager.btn_1, x+ ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width,
						height);*/
				g.setColor(0);
				g.drawRect(x+ ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);
			} else {
				/*ImageDiv.drawJiuGong(g, UIManager.button_focus, x + ui.offsetX
						+ btn_offx, y + ui.offsetY + btn_offy, width, height);*/
				g.drawRect(x+ ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);
			}
		} else {
			g.setColor(18,32,203);
			/*ImageDiv.drawJiuGong(g, UIManager.button_normal, x + ui.offsetX
					+ btn_offx, y + ui.offsetY + btn_offy, width, height);*/
			g.drawRect(x+ ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);
		}
		if (text != null) {
			g.setFont(smallFont);
			g.drawString(text, x + width / 2 + ui.offsetX + btn_offx, y
					+ (height - smallFontHeight) / 2 + smallFontHeight
					+ ui.offsetY + btn_offy, Graphics.BOTTOM | Graphics.HCENTER);
		}
		// paint shadow
	}

	private int btn_offx = 0;

	private int btn_offy = 0;

	public void keyPressed(int keyCode) {
		if (!ui.selector.button) {
			ui.selector.button = true;
		}
		if (keyCode == Key.FIREKEY(keyCode)) {
			btn_offx++;
			btn_offy++;
			super.keyPressed(keyCode);
			// System.out.print("here");
		}
	}

	public void keyReleased(int keyCode) {
		if (keyCode == Key.FIREKEY(keyCode)) {
			btn_offx--;
			btn_offy--;
			super.keyReleased(keyCode);
			ui.selector.button = false;
			//System.out.print("here again");
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
