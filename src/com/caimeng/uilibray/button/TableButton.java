  package com.caimeng.uilibray.button;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.table.ColumnAttribute;
import com.caimeng.uilibray.table.KTable;
import com.caimeng.uilibray.table.TableModel;
import com.caimeng.uilibray.utils.ImageDiv;

public class TableButton extends AbstructButton {

	private int rowNum;
	
	private int colNum;
	
	private String name="xxxx";
	
	private static String [][] content;
	
	//private static String [] rowTitle;
	
	private static String [] colTitle;
		
	public TableButton(){
		super();
	}
	
	public TableButton(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public TableButton(int x, int y, int width, int height, String label) {
		super(x, y, width, height);
		this.text = label;
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
		
		if(focus){
			if(keyPressed){
				ImageDiv.drawJiuGong(g, UIManager.btn_1, x + ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);
			}else{
				ImageDiv.drawJiuGong(g, UIManager.btn_1, x + ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);				
			}
		}else {
			ImageDiv.drawJiuGong(g, UIManager.btn_2, x + ui.offsetX + btn_offx, y + ui.offsetY + btn_offy, width, height);
		}
		if (text != null) {
			g.setFont(smallFont);
			g.setColor(ColorAttribute.label_context);
			g.drawString(text, x + width / 2 + ui.offsetX + btn_offx,y + (height - smallFontHeight) / 2 + smallFontHeight	+ ui.offsetY + btn_offy, Graphics.BOTTOM | Graphics.HCENTER);
		}
		// paint shadow
	}
		
	private int btn_offx=0;
	
	private int btn_offy=0;
	
	//private KTable kt=null;
	
	//private String[] titles = { "Col 1", "Col 2", "Col 3" ,"Col 4","Col 5","Col 6","Col 7"};
    //private  String [] content ={ "Row 1-" , "Row 2-" , "Row 3-" ,"Row 4-","Row 5-","Row 6-","Row 7-"};
	
	public void initTable()
	{
		
	}
		
	public void keyPressed(int keyCode) {
		if(!ui.selector.button){
			ui.selector.button=true;
		}
		if (keyCode ==Key.FIREKEY(keyCode)) {
			btn_offx++;
			btn_offy++;
		super.keyPressed(keyCode);

		if(ui.selector.button)
		{	
			CMForm xWForm=(CMForm)CMForm.display.getCurrent();
			KTable kt=new KTable(xWForm,createrow(rowNum,colNum),name);
			//tf.init(titles, content, 13, 50, 20);
			CMForm.display.setCurrent(kt);
		}
		//System.out.print("here");
		}
		//super.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		if (keyCode ==Key.FIREKEY(keyCode)) {
			btn_offx--;
			btn_offy--;
		super.keyReleased(keyCode);	
		ui.selector.button=false;
		//System.out.print("here again");
		}
	}
	
	static int maxDisplayChar;
	public static TableModel createrow(int rowNumber, int colNumber) 
	{
		
		ColumnAttribute[] cols = new ColumnAttribute[colNumber];
		maxDisplayChar =5;
		for (int i = 0; i < colNumber; i++) {
			//int width = (i%2 == 0) ? maxDisplayChar :  maxDisplayChar;
			cols[i] = new ColumnAttribute(maxDisplayChar ,colTitle[i]);
		}
		
		TableModel tableModel = new TableModel(cols);
		
		
		for (int i = 0; i < rowNumber; i++) {
			String[] cellValues = createCellValues(i, colNumber);
			tableModel.addRow(cellValues);
		}
		/*for(int i=0;i<rowNumber;i++)
		{
			tableModel.setRowTitle(i, rowTitle[i]);
		}*/
		return tableModel;
		
	}
	
	public static String[] createCellValues(int row, int colNumber) 
	{
		String[] cellValues = new String[colNumber];
		for (int i = 0; i < colNumber; i++) {
			cellValues[i] = content[row][i];	
		}
		return cellValues;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[][] getContent() {
		return content;
	}

	public void setContent(String[][] content,int row,int col) {
		TableButton.content = content;
		this.rowNum=row;
		this.colNum=col;
	}


	public void setTitles(String [] colTitle) {
		//this.rowTitle = rowTitle;
		TableButton.colTitle =colTitle;
		
	}
	

	/*class TableForm extends Form implements CommandListener 
	{
		private String[] titles;

		private String[] content;

		private  Vector addRow; 
		
		private String [] blank=null;
		
		private int rowNum;

		private Table tb;

		private Frame frame;
		private Command back=new Command("返回",Command.BACK,1);
		private Command del =new Command("删除",Command.OK,1);
		private Command insert = new Command("增加",Command.OK,1);
		public TableForm(String title, Frame frame) {
			super(title);
			this.frame = frame;
			addCommand(back);
			addCommand(del);
			addCommand(insert);
			setCommandListener(this);
			addRow=new Vector();
		}

		public void init(String[] str1,String[] str2,int num1,int num2,int num3) 
		{
			
             this.titles=str1;           
             this.content=str2;
			 this.rowNum=num1;	       
			 tb=new Table(this,str1,num2,num3);
			 tb.setSelectedCol(0);
			 tb.setSelectedRow(0);
			 append(tb);
			 for (int i = 0; i < rowNum; ++i)
			    	tb.addRow(str2);
			 //System.out.print("error");
			 for(int i= 0;i<rowNum;i++)
			 {
				 addRow.addElement("");
			 }
			 //System.out.print("error");
			 blank=new String[addRow.size()];
			 for(int i=0;i<addRow.size();i++)
				{
					blank[i]=addRow.elementAt(i).toString();
				}
			 
		}

		public void commandAction(Command cmd, Displayable display) 
		{
			if(cmd.equals(back))
			{
				frame.setFullScreenMode(true);
				Frame.display.setCurrent(frame);
				ui.selector.button=false;
			}else if(cmd.equals(del))
			{
				tb.deleteRow(tb.getSelectedRowIndex());
			}else if(cmd.equals(insert))
			{				
				tb.addRow(blank);
			}
		}
	}*/
	
	
}
