package com.caimeng.uilibray.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class URLEncoder {
    private static final int MAX_BYTES_PER_CHAR = 10; // rather arbitrary limit, but safe for now
    private static boolean[] dontNeedEncoding;
    private static String defaultEncName = "";
    private static final int caseDiff = ('a' - 'A');

    static 
    {
        dontNeedEncoding = new boolean[256];
        for (int i='a'; i<='z'; i++) 
        {
            dontNeedEncoding[i] = true;
        }
        for (int i='A'; i<='Z'; i++) 
        {
            dontNeedEncoding[i] = true;
        }
        for (int i='0'; i<='9'; i++) 
        {
            dontNeedEncoding[i] = true;
        }
        dontNeedEncoding[' '] = true;
        dontNeedEncoding['-'] = true;
        dontNeedEncoding['_'] = true;
        dontNeedEncoding['.'] = true;
        dontNeedEncoding['*'] = true;
        //defaultEncName = System.getProperty("microedition.encoding");
        if(defaultEncName == null || defaultEncName.trim().length() == 0){
            defaultEncName = "UTF-8";
        }
    }
    public static final int MIN_RADIX = 2;
    public static final int MAX_RADIX = 36;
    private URLEncoder() {}

    public static String encode(String s) 
    {
        String str = null;
        str = encode(s, defaultEncName);
        return str;
    }
    
    public static String encode(String s,String enc) 
    {
        boolean needToChange = false;
        boolean wroteUnencodedChar = false;
        StringBuffer out = new StringBuffer(s.length());
        ByteArrayOutputStream buf = new ByteArrayOutputStream(MAX_BYTES_PER_CHAR);
        OutputStreamWriter writer = null;
        try 
        {
            writer = new OutputStreamWriter(buf, enc);
        } 
        catch (UnsupportedEncodingException ex) 
        {
            try 
            {
                writer = new OutputStreamWriter(buf,defaultEncName);
            } 
            catch (UnsupportedEncodingException e) 
            {
                //never reach
            }
        }
        for (int i = 0; i < s.length(); i++) 
        {
            int c = (int) s.charAt(i);
            if (c<256 && dontNeedEncoding[c]) 
            {
                out.append((char) (c==' ' ? '+' : c));
                wroteUnencodedChar = true;
            } 
            else 
            {
                // convert to external encoding before hex conversion
                try 
                {
                    if (wroteUnencodedChar) 
                    { // Fix for 4407610
                        writer = new OutputStreamWriter(buf,enc);
                        wroteUnencodedChar = false;
                    }
                    if(writer != null)
                        writer.write(c);
                    /*
                    * If this character represents the start of a Unicode
                    * surrogate pair, then pass in two characters. It's not
                    * clear what should be done if a bytes reserved in the
                    * surrogate pairs range occurs outside of a legal surrogate
                    * pair. For now, just treat it as if it were any other
                    * character.
                    */
                    if (c >= 0xD800 && c <= 0xDBFF) 
                    {
                        if ((i + 1) < s.length()) 
                        {
                            int d = (int) s.charAt(i + 1);
                            if (d >= 0xDC00 && d <= 0xDFFF) 
                            {
                                writer.write(d);
                                i++;
                            }
                        }
                    }
                    writer.flush();
                } 
                catch (IOException e) 
                {
                    buf.reset();
                    continue;
                }
                byte[] ba = buf.toByteArray();
                for (int j = 0; j < ba.length; j++) 
                {
                    out.append('%');
                    char ch = toHex((ba[j] >> 4) & 0xF,16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (isLetter(ch)) 
                    {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = toHex(ba[j] & 0xF,16);
                    if (isLetter(ch)) 
                    {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                buf.reset();
                needToChange = true;
            }
        }
        return (needToChange? out.toString() : s);
    }

    private static char toHex(int digit,int radix) 
    {
        if ((digit >= radix) || (digit < 0)) 
        {
            return '\0';
        }
        if ((radix < MIN_RADIX) || (radix > MAX_RADIX)) 
        {
            return '\0';
        }

        if (digit < 10) 
        {
            return (char)('0' + digit);
        }
        return (char)('a' - 10 + digit);
    }

    private static boolean isLetter(char c) 
    {
        if( (c >= 'a' && c <= 'z') || (c >='A' && c <= 'Z'))
            return true;
        return false;
    }
}


