/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alie.songlib;

import java.net.URI;

/**
 *
 * @author Matt
 */
public class Song 
{
    public String name;
    //public URI uri;
    public String uri;
    
    public Song(String newName, String newURI)
    {
        name = newName;
        try
        {
            //uri = new URI(newURI);
            uri = newURI;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}