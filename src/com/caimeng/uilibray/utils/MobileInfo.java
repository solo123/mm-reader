package com.caimeng.uilibray.utils;

public class MobileInfo{
    /**     
    * get the cell id in the phone     
    *      
    * @return     
    */    
    public static String getCellId(){
                String out = "";
                try{             
                    out = System.getProperty("Cell-ID");            
                    if(out== null || out.equals("null") || out.equals(""))            
                        out = System.getProperty("CellID");            
                    if(out== null || out.equals("null") || out.equals(""))            
                        System.getProperty("phone.cid");            
                //#if polish.Vendor == Nokia            
                    if(out== null || out.equals("null") || out.equals(""))
                    	out=  System.getProperty("com.nokia.mid.cellid");
                //#elif polish.Vendor == Sony-Ericsson            
                    if(out== null || out.equals("null") || out.equals(""))            
                        out = System.getProperty("com.sonyericsson.net.cellid");      
                //#elif polish.Vendor == Motorola            
                    if(out== null || out.equals("null") || out.equals(""))            
                        out = System.getProperty("phone.cid");//System.getProperty("CellID");            
                //#elif polish.Vendor == Samsung            
                    if(out== null || out.equals("null") || out.equals(""))            
                        out = System.getProperty("com.samsung.cellid");            
                //#elif polish.Vendor == Siemens            
                    if(out== null || out.equals("null") || out.equals(""))            
                        out = System.getProperty("com.siemens.cellid");            
                //#elif polish.Vendor == BlackBerry            
                //#= if(out== null || out.equals("null") || out.equals(""))           

     
                //#= out = GPRSInfo.getCellInfo().getCellId();            
                //#else            
                    if(out== null || out.equals("null") || out.equals(""))            

    
                        out = System.getProperty("cid");            
                //#endif         
                }catch(Exception e){    
                            
                    return out==null?"":out;        
                    
                }         
                return out==null?"":out;    
        }     
        
        
        
    /**     
     * get the lac sring from phone     
     */    
    public static String getLAC(){        
        
        String out = "";        
        
        try{             
            
            out = System.getProperty("phone.lac");             
            //#if polish.Vendor == Nokia            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.lac");            
            //#elif polish.Vendor == Sony-Ericsson            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.sonyericsson.net.lac");            
            //#elif polish.Vendor == Motorola            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("LocAreaCode");        
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("lac");  
            //#elif polish.Vendor == Samsung            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.samsung.cellid");            
            //#elif polish.Vendor == Siemens            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.siemens.cellid");            
            //#elif polish.Vendor == BlackBerry            
            //#= if(out== null ||out.equals("null")|| out.equals(""))                
            //#= out = GPRSInfo.getCellInfo().getLAC();            
            //#else            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("cid");            
            //#endif         
        }catch(Exception e){            
            
            return out==null?"":out;        
            
        } 
                
        return out==null?"":out;    
    }     
        
        
        
    /**     
     *    IMSI (International Mobile Subscriber Identity)
     * Example IMSI (O2 UK): 234103530089555        
     * String mcc = imsi.substring(0,3); // 234 (UK)        
     * String mnc = imsi.substring(3,5); // 10 (O2)     
     * @return     
     */    
    public static String getIMSI(){    
            
        String out = "";        
        
        try{             
            
            out = System.getProperty("IMSI");            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("phone.imsi") ;            
            //#if polish.Vendor == Nokia            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.imsi");        
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.mobinfo.IMSI");
            //#elif polish.Vendor == Sony-Ericsson
                        
            if(out== null ||out.equals("null")|| out.equals(""))                
                out = System.getProperty("com.sonyericsson.imsi");
                       
            //#elif polish.Vendor == Motorola            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("IMSI");            
            //#elif polish.Vendor == Samsung
                       
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.samsung.imei");
                       
            //#elif polish.Vendor == Siemens
                       
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.siemens.imei");
                       
            //#elif polish.Vendor == BlackBerry            
            //#= if(out== null || out.equals("null") || out.equals(" "))              

  
            //#= out = GPRSInfo.getCellInfo().getBSIC();            
            //#else            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("imsi");            
            //#endif         
            
        }catch(Exception e){
            
            return out==null?"":out;    
            
        }catch(Error e){
        	
        }
        
        return out==null?"":out;    
    }     
            
            
            
    /**     
     *      
     * For moto, Example IMSI (O2 UK): 234103530089555        
     String mcc = imsi.substring(0,3); // 234 (UK)     
     * @return     
     */    
    public static String getMCC(){    
            
        String out = "";    
            
        try{             
            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("phone.mcc") ;            
            //#if polish.Vendor == Nokia            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.countrycode");            
            //#elif polish.Vendor == Sony-Ericsson            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.sonyericsson.net.cmcc");            
            //#elif polish.Vendor == Motorola            
            if(out== null || out.equals("null") || out.equals("")){                
                out = getIMSI().equals("")?"": getIMSI().substring(0,3);            
            }            
            //#elif polish.Vendor == Samsung
                      
            if(out== null ||out.equals("null")|| out.equals(""))                
            out = System.getProperty("com.samsung.imei");
                       
            //#elif polish.Vendor == Siemens
                      
            if(out== null ||out.equals("null")|| out.equals(""))                
            out = System.getProperty("com.siemens.imei");
                       
            //#elif polish.Vendor == BlackBerry    
            if(out== null || out.equals("null") || out.equals(""))//getMNC()          

      
            //#= out = GPRSInfo.getCellInfo().getMCC();             
            //#else            
            if(out== null || out.equals("null") || out.equals(""))                
                out = System.getProperty("mcc");            
            //#endif         
            }catch(Exception e){
                        
                return out==null?"":out;    
                
            }catch(Error e){
            	
            }  
            
            return out==null?"":out;    
    }     
                    
                    
                    
   /**     
    *      
    * For moto, Example IMSI (O2 UK): 234103530089555        
    * String mnc = imsi.substring(3,5); // 10 (O2)     
    * @return     
    */    
    
    public static String getMNC(){        
        String out = "";        
        try{             
            if(out== null ||out.equals("null")|| out.equals(""))                
                out = System.getProperty("phone.mnc");            
            //#if polish.Vendor == Nokia            
            if(out== null ||out.equals("null")|| out.equals(""))
                out = System.getProperty("com.nokia.mid.networkid");
            if(out== null ||out.equals("null")|| out.equals(""))                
                out = getIMSI().equals("")?"": getIMSI().substring(3,5);            
            //#elif polish.Vendor == Sony-Ericsson            
            if(out== null ||out.equals("null")|| out.equals(""))                
                out = System.getProperty("com.sonyericsson.net.mnc");            
            //#elif polish.Vendor == Motorola            
            if(out== null ||out.equals("null")|| out.equals("")){                
                out = getIMSI().equals("")?"": getIMSI().substring(3,5);            
            }            
            //#elif polish.Vendor == Samsung
            if(out== null ||out.equals("null")|| out.equals(""))                
            out = System.getProperty("com.samsung.imei");
            //#elif polish.Vendor == Siemens
            if(out== null ||out.equals("null")|| out.equals(""))                
            out = System.getProperty("com.siemens.imei");
            //#elif polish.Vendor == BlackBerry            
            if(out== null ||out.equals("null")|| out.equals(""))//getMNC()            

    
            //#= out = GPRSInfo.getCellInfo().getMCC();             
            //#else            
            if(out== null ||out.equals("null")|| out.equals(""))                
                out = System.getProperty("mnc");            
            //#endif         
        }catch(Exception e){    
                    
            return out == null?"":out;    
                
        }catch(Error e){
        	
        }         
        return out == null?"":out;    
    }     
                        
    /**     
     *      
     * get the IMEI (International Mobile Equipment Identity (IMEI)) in the phone     
     *      
     * @return     
     */    
    public static String getIMEI(){        
        String out = "";        
        try{             
            out = System.getProperty("com.imei");            
            //#if polish.Vendor == Nokia            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("phone.imei");            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.IMEI");            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.imei");            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.nokia.mid.imei");            
            //#elif polish.Vendor == Sony-Ericsson            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.sonyericsson.imei");            
            //#elif polish.Vendor == Motorola            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("IMEI");            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.motorola.IMEI");            
            //#elif polish.Vendor == Samsung            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.samsung.imei");            
            //#elif polish.Vendor == Siemens            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("com.siemens.imei");            
            //#else            
            if(out == null || out.equals("null") || out.equals(""))                
                out = System.getProperty("imei");            
            //#endif         
            }catch(Exception e){    
                        
                return out == null?"":out;        
            }catch(Error e){
            	
            }         
            return out == null?"":out;    
    }
    public static String getCNETERNUMBER(){
    	String str="";
    	try{
    		str=System.getProperty( "wireless.messaging.sms.smsc");
    	}catch(Exception e){
    		
    	}catch(Error e){
        	
        }
    	return str==null?"":str;
    }
    
} 