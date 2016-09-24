package alie.songlib;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Album 
{
    public ArrayList<Song> songs;
    public int year;
    public String name;
    
    public Album(String newName)
    {
        songs = new ArrayList<>();
        name = newName;
        year = 0000;
    }
    
    //Pushes new Song onto album.
    public void addSong(Song song)
    {
        songs.add(song);
    }
    
    //Returns NULL if not found.
    public String getSongPath(String name)
    {
        try
        {
            String ret = null;
            for(Song song : songs)
            {
                if(song.name.equals(name))
                {
                    ret = new File(song.uri).toString();
                    break;
                }               
            }
            return ret;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
