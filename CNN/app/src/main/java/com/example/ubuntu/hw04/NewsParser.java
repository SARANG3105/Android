package com.example.ubuntu.hw04;

import android.util.Xml;
import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
HW04
Sarangdeep Singh
Ishan Agarwal
Group05
 */

public class NewsParser {
    public static class NewsSAXparser extends DefaultHandler{
        ArrayList<News> newsList;
        News news;
        StringBuilder builder;
        boolean copy=false;

        public static ArrayList<News> parseNews(InputStream inputStream) throws IOException, SAXException {
            NewsSAXparser parser= new NewsSAXparser();
            Xml.parse(inputStream, Xml.Encoding.ISO_8859_1,parser);
            return parser.newsList;

        }
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            this.newsList= new ArrayList<>();
            this.builder=new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            Log.d("item",localName);
            if(localName.equals("copyright")){
                copy=true;
                Log.d("item","true");
            }
            if(localName.equals("item")){
                news= new News();

            }if(localName.equals("thumbnail") || localName.equals("content")){
                String url= attributes.getValue("url");
                news.imageUrl=url;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            Log.d("item",uri);
            String text = "";
            if (builder.toString() != null) {
                text = builder.toString().trim();
            }
            if (news != null) {
                if (localName.equals("title")) {
                    news.title = text;
                    Log.d("demo", text);
                }else if(localName.equals("link")){
                    news.newsLink=text;
                }
                else if (localName.equals("description")) {
                    String[] s;
                    if(text.contains("<div")) {
                       s = text.split("<div");
                    }else{
                      s = text.split("<img");
                    }
                    news.description = s[0];
                } else if (localName.equals("pubDate")) {
                    news.pubdate = text;
                } else if (localName.equals("item")) {
                    newsList.add(news);
                }
            }
                builder.setLength(0);
            }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

                    builder.append(ch, start, length);
                    Log.d("bu", String.valueOf(ch));


        }
    }
}
