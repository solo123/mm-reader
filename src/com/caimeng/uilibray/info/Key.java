package com.caimeng.uilibray.info;


public class Key {

	public static int UP = -1;

	public static int DOWN = -2;

	public static int LEFT = -3;

	public static int RIGHT = -4;

	public static int FIRE = -5;

	public static int LEFT_SOFT_KEY = -6;

	public static int RIGHT_SOFT_KEY = -7;

	public static int DELETE = -8;
	
	public static int START=42;
	
	public static int POUND=35;
	
	public static int DAILD=-10;//普通
	
	public static int LEFT_SOFT_KEY(int key){
		switch(key){
		case -6://普通
			LEFT_SOFT_KEY=-6;
			break;
		case -21://Alcatel OT557/556 /MOTO V600i 
			LEFT_SOFT_KEY=-21;
			break;
		case 21://moto v303
			LEFT_SOFT_KEY=21;
			break;			
//		case -7://波导S689 波导M19
//			LEFT_SOFT_KEY=-7;
//			break;
		case 105:// 飞利浦9@9e
			LEFT_SOFT_KEY=105;
			break;
		case -202://飞利浦568
			LEFT_SOFT_KEY=-202;
			break;
		}
		return LEFT_SOFT_KEY;
	}
	public static int RIGHT_SOFT_KEY(int key){
		switch(key){
		case -7://普通
			RIGHT_SOFT_KEY=-7;
			break;
		case -22://Alcatel OT557/556 /MOTO V600i 
			RIGHT_SOFT_KEY=-22;
			break;
		case 22://moto v303
			RIGHT_SOFT_KEY=21;
			break;			
//		case -6://波导S689 波导M19
//			LEFT_SOFT_KEY=-6;
//			break;
		case 106:// 飞利浦9@9e
			RIGHT_SOFT_KEY=106;
			break;
		case -203://飞利浦568
			RIGHT_SOFT_KEY=-202;
			break;
		}		
		return RIGHT_SOFT_KEY;
		
	}
	public static int UPKEY(int key){
		switch(key){
		case -1://普通
			UP=-1;
			break;
		case 1://moto v3
			UP=1;
			break;
		case -16://三菱 M900
			UP=-16;
			break;		
		}
		return UP;
	}
	public static int DOWNKEY(int key){
		switch(key){
		case -2://普通
			DOWN=-2;
			break;
		case 6://moto v3
			DOWN=6;
			break;
		case -17://三菱 M900
			DOWN=-17;
			break;	
		case -6:
			if(LEFT_SOFT_KEY==-21){//moto 
				DOWN=-6;
			}
			break;
		}
		return DOWN;
	}
	public static int LEFTKEY(int key){
		switch(key){
		case -3://普通
			LEFT=-3;
			break;
		case 2://moto v3
			LEFT=2;
			break;
		case -19://三菱 M900
			LEFT=-19;
			break;	
		case -2:
			if(LEFT_SOFT_KEY==-21){//moto 
				LEFT=-2;
			}
			break;
		}
		return LEFT;
	}
	public static int RIGHTKEY(int key){
		switch(key){
		case -4://普通
			RIGHT=-4;
			break;
		case 5://moto v3
			RIGHT=5;
			break;
		case -18://三菱 M900
			RIGHT=-18;
			break;	
		case -5:
			if(LEFT_SOFT_KEY==-21){//moto 
				RIGHT=-5;
			}
			break;
		}
		return RIGHT;
	}
	public static int FIREKEY(int key){
		switch(key){
		case -5://普通
			FIRE=-5;
			break;
		case 20://moto v3
			FIRE=20;
			break;
		case -20://松下 .明基Ｓ700Alcatel OT557/556 /MOTO V600i
			FIRE=-20;
			break;	
		case -27://三菱 M900
			FIRE=-27;
			break;
		}
		return FIRE;
	}
	/*public static void initKey(){
		UP=Canvas.UP;
		DOWN=Canvas.DOWN;
		LEFT=Canvas.LEFT;
		RIGHT=Canvas.RIGHT;
		FIRE=Canvas.FIRE;
		START=Canvas.KEY_STAR;
		POUND=Canvas.KEY_POUND;
		System.out.println("up=="+UP);
		System.out.println("DOWN=="+DOWN);
		System.out.println("LEFT=="+LEFT);
		System.out.println("RIGHT=="+RIGHT);
		System.out.println("FIRE=="+FIRE);
		System.out.println("START=="+START);
		
	}*/
}
