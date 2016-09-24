/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alie.songlib;

import java.util.ArrayList;
import java.net.URI;
import alie.types.StringPair;
/**
 *
 * @author Matt
 */
public class Artist 
{
    public ArrayList<Album> albums;
    public String name;
    
    public Artist(String newName)
    {
        albums = new ArrayList<>();
        name = newName;
    }
    
    public void addAlbum(Album toAdd)
    {
        albums.add(toAdd);
    }
    
    public ArrayList<StringPair> hasSongName(String songName)
    {
        ArrayList<StringPair> results = new ArrayList<>();
        for(Album album : albums)
        {
            String uri = album.getSongPath(songName);
            if (uri != null)
            {
                results.add(new StringPair(album.name, uri));
            }
        }
        return results;
    }
}
