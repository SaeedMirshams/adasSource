/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

/**
 *
 * @author 8062439
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = null;
    private static AnimalList animalList = null;

    public static AnimalList getAnimalList() {
        return animalList;
    }

    public static void setAnimalList(AnimalList animalList) {
        MyXMLHandler.animalList = animalList;
    }

    /**
     * Called when tag starts ( ex:- <name>AndroidPeople</name>
     * -- <name> )
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        currentElement = true;

        if (localName.equals("data")) {
            /**
             * Start
             */
            animalList = new AnimalList();
        } else if (localName.equals("animal")) {
            /**
             * Get attribute value
             */
            String name = attributes.getValue("name");
            String farsiname = attributes.getValue("farsiname");
            String image = attributes.getValue("image");
            String sound = attributes.getValue("sound");

            Animal a=new Animal(name,farsiname,image,sound);
            animalList.AddAnimal(a);
        }

    }

    /**
     * Called when tag closing ( ex:- <name>AndroidPeople</name>
     * -- </name> )
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        /**
         * set value
         */
        /*if (localName.equalsIgnoreCase("name")) {
        
        }*/

    }

    /**
     * Called to get tag characters ( ex:- <name>AndroidPeople</name>
     * -- to get AndroidPeople Character )
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = new String(ch, start, length);
            currentElement = false;
        }

    }

}
