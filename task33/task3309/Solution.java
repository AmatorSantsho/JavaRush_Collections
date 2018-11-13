package com.javarush.task.task33.task3309;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        String result="";
        try {

            StringWriter writer =new StringWriter();
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
           Marshaller marshaller= context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            marshaller.marshal(obj, writer);
            String all_text = writer.toString();
            String []lines = all_text.split("\n");
            StringBuffer stringBuffer=new StringBuffer();
            for (int i = 0; i <lines.length ; i++) {
                String next_line =lines[i];
                if (next_line.startsWith("<"+tagName+">")||next_line.startsWith("<"+tagName+"/>")&&!next_line.startsWith("<![CDATA[")){
                    stringBuffer.append("<!--"+comment+"-->");
                    stringBuffer.append("\n");
                    stringBuffer.append(next_line);
                    stringBuffer.append("\n");

                }else {
                    stringBuffer.append(next_line);
                    stringBuffer.append("\n");

                }
            }
 result =stringBuffer.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static void main(String[] args) {
        StringWriter writer =new StringWriter();
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<first>\n" +
                "<second>some string</second>\n" +
                "<second>some string</second>\n" +
                "<second><![CDATA[need CDATA because of < and >]]></second>\n" +
                "<second/>\n" +
                "</first>");

        String all_text = writer.toString();
        String []lines = all_text.split("\n");
        StringBuffer stringBuffer=new StringBuffer();
        String tagName="second";
        String comment = "it’s a comment";
        for (int i = 0; i <lines.length ; i++) {
            String next_line =lines[i];
            if (next_line.startsWith("<"+tagName+">")||next_line.startsWith("<"+tagName+"/>")&&!next_line.startsWith("<![CDATA[")){
                stringBuffer.append("<!--"+comment+"-->");
                stringBuffer.append("\n");
                stringBuffer.append(next_line);
                stringBuffer.append("\n");

            }else {
                stringBuffer.append(next_line);
                stringBuffer.append("\n");

            }
        }
        System.out.println(stringBuffer.toString());
    }
}
