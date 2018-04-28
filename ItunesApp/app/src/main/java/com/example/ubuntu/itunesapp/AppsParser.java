package com.example.ubuntu.itunesapp;

import android.content.Intent;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ubuntu on 3/11/18.
 */

public class AppsParser {
    public static class AppsSAXParser extends DefaultHandler{
        ArrayList<App> apps;
        StringBuilder builder;
        App app;
        boolean bool=false,bool1=false;
        public static ArrayList<App> parseApps(InputStream inputStream) throws IOException, SAXException {
            AppsSAXParser appsSAXParser = new AppsSAXParser();
            Xml.parse(inputStream, Xml.Encoding.ISO_8859_1,appsSAXParser);
            return appsSAXParser.apps;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            this.apps= new ArrayList<>();
            this.builder= new StringBuilder();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            String text = "";
            if (builder.toString() != null) {
                text = builder.toString().trim();
            }
            if(localName.equals("entry")){
                app= new App();
            }
            if(app!=null) {
                if (localName.equals("id")) {
                    String id = attributes.getValue("im:id");
                    app.appID = id;
                } else if (localName.equals("link")) {
                    String url = attributes.getValue("href");
                    app.appUrl = url;
                } else if (localName.equals("price")) {
                    String price = attributes.getValue("amount");
                    String currency = attributes.getValue("currency");
                    app.price = price;
                    app.currency = currency;
                } else if (localName.equals("image")) {
                    String val= attributes.getValue("height");
                    if (val.equals("53")) {
                       // app.smallPhoto=;
                    } else if (val.equals("100")) {
                        app.largePhoto=val;
                    }
                } else if (localName.equals("releaseDate")) {
                    String date = attributes.getValue("label");
                    app.releaseDate = date;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            String text = "";
            if (builder.toString() != null) {
                text = builder.toString().trim();
            }
            if (app != null) {
                if (localName.equals("title")) {
                    app.appTitle = text;
                }
                else if (localName.equals("artist")) {
                    app.devName = text;
                }
                else if(localName.equals("entry")){
                    apps.add(app);
                }
            }
            builder.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            builder.append(ch, start, length);
        }
    }

    public static class AppsPULLParser{
        public static ArrayList<App> parseApps(InputStream inputStream) throws IOException, XmlPullParserException {
            ArrayList<App> apps=new ArrayList<>();
            App app=null;
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser pullParser= factory.newPullParser();
            pullParser.setInput(inputStream,"UTF-8");
            int event =  pullParser.getEventType();

            while (event!=XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                    if(pullParser.getName().equals("entry")){
                        app= new App();
                        Log.d("app","intialize");
                    }
                    if(app!=null) {
                        if (pullParser.getName().equals("id")) {
                            app.appID = pullParser.getAttributeValue(null, "im:id");
                        } else if (pullParser.getName().equals("title")) {
                            app.appTitle = pullParser.nextText().trim();
                        } else if (pullParser.getName().equals("link")) {
                            app.appUrl = pullParser.getAttributeValue(null, "href");

                        } else if (pullParser.getName().equals("im:artist")) {
                            app.devName = pullParser.nextText().trim();
                        } else if (pullParser.getName().equals("im:price")) {
                            app.price = pullParser.getAttributeValue(null, "amount");
                            app.currency = pullParser.getAttributeValue(null, "currency");
                        } else if (pullParser.getName().equals("im:image")) {
                            String val = pullParser.getAttributeValue(null, "height");
                            Log.d("app",val);
                            if (val.equals("53")) {
                                app.smallPhoto = pullParser.nextText().trim();
                            } else if (val.equals("100")) {
                                app.largePhoto = pullParser.nextText().trim();
                            }
                        } else if (pullParser.getName().equals("im:releaseDate")) {
                            app.releaseDate = pullParser.getAttributeValue(null, "label");
                        }
                    }
                    break;

                    case XmlPullParser.END_TAG:
                        if(pullParser.getName().equals("entry")){
                            apps.add(app);
                        }
                        break;

                    default:
                        break;
                }
                event= pullParser.next();
            }

            return apps;
        }
    }
}
