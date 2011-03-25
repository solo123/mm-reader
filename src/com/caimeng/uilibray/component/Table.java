package com.caimeng.uilibray.component;

import java.util.Vector;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.Theme;
import com.caimeng.uilibray.common.Themeable;
import com.caimeng.uilibray.event.TableListener;


public class Table extends CustomItem
  implements Themeable
{
  private boolean rowSelect;
  private int rows = 1;
  private int cols = 5;
  private int cellWidth ;
  private int cellHeight ;
  private int selectedColIndex = 0;
  private int selectedRowIndex = 0;
  private String[][] data;
  private Vector listeners;
  private int foreground;
  private int background;
  private int buttonBgLight;
  private int buttonBgDark;
  private int buttonSelLight;
  private int buttonSelDark;
  private int border1;
  private int border2;
  private int titleForeground;
  private int titleBackground;
  boolean horizontal;
  boolean vertical;
  private int selectedBackground;
  
  private int selectedForeground;

  private int width;
  
  public Table(Form parent, String[] titles,int cellWidth,int cellHeight)
  {
    super(null);
    setRowSelect(true);
    applyTheme(Theme.getTheme());
    this.cols = titles.length;
    //this.rows = num;
    this.data = new String[this.rows][this.cols];
    this.data[0] = titles;
    this.cellWidth=cellWidth;
    this.cellHeight=cellHeight;
    int interactionMode = super.getInteractionModes();
    this.horizontal = ((interactionMode & 0x1) != 0);//设置输入方式
    this.vertical = ((interactionMode & 0x2) != 0);

    //this.cellWidth = (parent.getHeight() / titles.length);
  }

 

public void notifyLayout()
  {
    super.invalidate();
  }

  public void addTableListener(TableListener tl)
  {
    if (this.listeners == null)
      this.listeners = new Vector();

    this.listeners.addElement(tl);
  }

  public void removeTableListener(TableListener tl)
  {
    if (this.listeners != null)
      this.listeners.removeElement(tl);
  }

  public void keyPressed(int keyCode)
  {
    if (this.listeners != null)
      for (int i = 0; i < this.listeners.size(); ++i)
        ((TableListener)this.listeners.elementAt(i)).keyPressed(this.selectedColIndex, this.selectedRowIndex, keyCode);
   /* switch (keyCode)
    {
    case 6://down
      if (this.selectedRowIndex < this.rows - 1) {
        this.selectedRowIndex += 1;
        //System.out.print("row"+rows);
        super.repaint(); 
      }
    case 1://up
      if (this.selectedRowIndex > 1) {
        this.selectedRowIndex -= 1;
        super.repaint(); 
      }
    case 2://left
      if (this.selectedColIndex > 0) {
        this.selectedColIndex -= 1;
        super.repaint(); 
      }
    case 5://right
      if (this.selectedColIndex < this.cols - 1) {
        this.selectedColIndex += 1;          
        super.repaint(); 
        }
    case 3:
    case 4:
    }*/
    //System.out.print("col"+selectedColIndex+"row"+selectedRowIndex);
  }

  public synchronized void addRow(String[] row)
  {
    String[][] ndata = new String[this.rows + 1][this.cols];
    for (int r = 0; r < this.rows; ++r)
      ndata[r] = this.data[r];

    ndata[this.rows] = row;
    this.data = ndata;
    this.rows += 1;
    notifyLayout();
  }

  public synchronized void deleteRow(int row)
  {
    ++row;
    if (row <= 0)
      return;
    String[][] ndata = new String[this.rows - 1][this.cols];
    for (int r = 0; r < this.rows - 1; ++r)
      if (r != row)
        ndata[r] = this.data[r];

    this.data = ndata;

    this.rows -= 1;
    setSelectedRow(1);
    notifyLayout();
  }

  public void emptyTable()
  {
    while (getRowCount() > 0)
      deleteRow(0);
  }

  protected int getMinContentHeight()
  {
    return (this.rows * this.cellHeight + 1);
  }

  protected int getMinContentWidth()
  {
    return (this.cols * this.cellWidth + 1);
  }

  protected int getPrefContentHeight(int width)
  {
    return getMinContentHeight();
  }

  protected int getPrefContentWidth(int height)
  {
    return getMinContentWidth();
  }

  
  protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout)
  {
	  
		  
		    visRect_inout[0] = this.selectedColIndex;
		    visRect_inout[1] = this.selectedRowIndex+1;
		    visRect_inout[2] = this.cellWidth;
		    visRect_inout[3] = this.cellHeight*2;
		   
	  label352:  
		 
    if ((this.horizontal) && (this.vertical))
      switch (dir)
      {
      case 6://down
        if (this.selectedRowIndex < this.rows - 1) {
          this.selectedRowIndex += 1;
          //System.out.print("row"+rows);
          //super.repaint(0,0,viewportWidth,viewportHeight);
          super.repaint();
          if(selectedRowIndex*cellHeight>viewportHeight-cellHeight*2)
		    	return false;
          break label352;
        }
        return false;
      case 1://up
        if (this.selectedRowIndex > 1) {
          this.selectedRowIndex -= 1;
          super.repaint();
          break label352;
        }
        return false;
      case 2://left
        if (this.selectedColIndex > 0) {
          this.selectedColIndex -= 1;
          super.repaint();
          break label352;
        }

        return false;
      case 5://right
        if (this.selectedColIndex < this.cols - 1) {
          this.selectedColIndex += 1;          
          super.repaint();
          break label352; }

        return false;
      case 3:
      case 4:
      }
    else if ((this.horizontal) || (this.vertical))
      switch (dir)
      {
      case 1://up
      case 2://left
        if (this.selectedColIndex > 0) {
          this.selectedColIndex -= 1;
          super.repaint();
        } else if (this.selectedRowIndex > 1) {
          this.selectedRowIndex -= 1;
          this.selectedColIndex = (this.cols - 1);
          super.repaint();
        } else {
          return false;
        }

      case 5://right
      case 6://down
        if (this.selectedColIndex < this.cols - 1) {
          this.selectedColIndex += 1;
          super.repaint();
        }
        else if (this.selectedRowIndex < this.rows - 1) {
          this.selectedRowIndex += 1;
          this.selectedColIndex = 0;
          super.repaint();
        } else {
          return false;
        }
      case 3:
      case 4:
      }
		   

    return true;
  }

  public void setText(String text)
  {
    this.data[this.selectedRowIndex][this.selectedColIndex] = text;
    super.repaint(this.selectedRowIndex * this.cellWidth, this.selectedColIndex * this.cellHeight, this.cellWidth, this.cellHeight);
  }

  public String getText()
  {
    return this.data[this.selectedRowIndex][this.selectedColIndex];
  }

  public boolean isRowSelect()
  {
    return this.rowSelect;
  }

  public void setRowSelect(boolean rowSelect)
  {
    this.rowSelect = rowSelect;
  }

  public int getSelectedColIndex()
  {
    return this.selectedColIndex;
  }

  public void setSelectedCol(int selectedCol)
  {
    this.selectedColIndex = selectedCol;
  }

  public int getSelectedRowIndex()
  {
    return (this.selectedRowIndex - 1);
  }

  public void setSelectedRow(int selectedRow)
  {
    this.selectedRowIndex = selectedRow;
  }

  public String getData(int col, int row)
  {
    return this.data[row][col];
  }

  public synchronized int getRowCount()
  {
    return (this.rows - 1);
  }

  public int getColCount()
  {
    return this.cols;
  }

  public String[] getRow(int row)
  {
    return this.data[row];
  }

  public void setBackground(int i)
  {
    this.background = i;
  }

  public int getForeground()
  {
    return this.foreground;
  }

  public void setForeground(int foreground)
  {
    this.foreground = foreground;
  }

  public int getBackground()
  {
    return this.background;
  }

  public int getTitleForeground()
  {
    return this.titleForeground;
  }

  public void setTitleForeground(int titleForeground)
  {
    this.titleForeground = titleForeground;
  }

  public int getTitleBackground()
  {
    return this.titleBackground;
  }

  public void setTitleBackground(int titleBackground)
  {
    this.titleBackground = titleBackground;
  }

  public void applyTheme(Theme t)
  {
    setSelectedForeground(t.getColor(3));
    setSelectedBackground(t.getColor(2));
    setTitleBackground(t.getColor(123));
    setTitleForeground(t.getColor(124));
    setForeground(t.getColor(1));
    setBackground(t.getColor(0));
    setBorder1(4);
  }

  protected synchronized void paint(Graphics g, int w, int h)
  {
    g.setColor(getBackground());
    g.fillRect(0, 0, w, h);
    //g.setClip(0, 0, w, h);
    Font f = Font.getFont(0, 0, 0);
    this.cellHeight = f.getHeight();
    g.setFont(f);
    if(selectedColIndex*cellWidth>w-cellWidth)
    {
    	g.translate(-((selectedColIndex+1)*cellWidth-w), 0);
    	//super.repaint();
    }
    
    if(selectedRowIndex*cellHeight>h-cellHeight*2)
    {
    	g.translate(0, -((selectedRowIndex+2)*cellHeight-h));
    }
    
    g.setColor(getSelectedForeground());
    if (isRowSelect()) {
      g.fillRect(0, this.selectedRowIndex * this.cellHeight + 1, this.cols * this.cellWidth - 1, this.cellHeight - 1);
    }
    else {
      g.fillRect(this.selectedColIndex * this.cellWidth + 1, this.selectedRowIndex * this.cellHeight + 1, this.cellWidth - 1, this.cellHeight - 1);
    }

    g.setColor(getBorder1());

    for (int i = 0; i <= this.rows; ++i) {
      g.drawLine(0, i * this.cellHeight, this.cols * this.cellWidth, i * this.cellHeight);
      
    }

    for (int i = 0; i <= this.cols; ++i) {
      g.drawLine(i * this.cellWidth, 0, i * this.cellWidth, this.rows * this.cellHeight);
      //System.out.print("error2");
    }

    for (int i = 0; i < this.rows; ++i)
    {
    	
      for (int j = 0; j < this.cols; ++j)
      {
    	  //System.out.print("error3"+i);
        if (i == 0) {
          g.setColor(getTitleBackground());
          g.fillRect(j * this.cellWidth + 1, 1, this.cellWidth - 1, this.cellHeight - 1);
          g.setColor(getTitleForeground());
        }
       // System.out.print("error1"+j);
        if (this.data[i][j] != null)
        {
          int oldClipX = g.getClipX();
          int oldClipY = g.getClipY();
          int oldClipWidth = g.getClipWidth();
          int oldClipHeight = g.getClipHeight();         
          g.setClip(j * this.cellWidth + 1, i * this.cellHeight, this.cellWidth - 1, this.cellHeight - 1);

          if ((this.selectedColIndex == j) && (this.selectedRowIndex == i)) {
            g.setColor(getSelectedBackground());
            g.fillRect(this.selectedColIndex * this.cellWidth, this.selectedRowIndex * this.cellHeight + 1, this.cellWidth, this.cellHeight);
            g.setColor(getSelectedForeground());
          }
          else if (this.selectedRowIndex == i) {
            g.setColor(getSelectedBackground());
          }
          else {
            g.setColor(getForeground());
          }

          g.drawString(this.data[i][j], j * this.cellWidth + 2, (i + 1) * this.cellHeight - 2, 
            36);

          g.setClip(oldClipX, oldClipY, oldClipWidth, oldClipHeight);
        }
      }
    }
    
  }

  public int getButtonBgLight()
  {
    return this.buttonBgLight;
  }

  public void setButtonBgLight(int buttonBgLight)
  {
    this.buttonBgLight = buttonBgLight;
  }

  public int getButtonBgDark()
  {
    return this.buttonBgDark;
  }

  public void setButtonBgDark(int buttonBgDark)
  {
    this.buttonBgDark = buttonBgDark;
  }

  public int getButtonSelLight()
  {
    return this.buttonSelLight;
  }

  public void setButtonSelLight(int buttonSelLight)
  {
    this.buttonSelLight = buttonSelLight;
  }

  public int getButtonSelDark()
  {
    return this.buttonSelDark;
  }

  public void setButtonSelDark(int buttonSelDark)
  {
    this.buttonSelDark = buttonSelDark;
  }

  public int getBorder1()
  {
    return this.border1;
  }

  public void setBorder1(int border1)
  {
    this.border1 = border1;
  }

  public int getBorder2()
  {
    return this.border2;
  }

  public void setBorder2(int border2)
  {
    this.border2 = border2;
  }

  public int getSelectedBackground()
  {
    return this.selectedBackground;
  }

  public int getSelectedForeground()
  {
    return this.selectedForeground;
  }

  public void setSelectedBackground(int selectColor)
  {
    this.selectedBackground = selectColor;
  }

  public void setSelectedForeground(int selectColor)
  {
    this.selectedForeground = selectColor;
  }
}