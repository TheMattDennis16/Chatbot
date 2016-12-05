package songlib;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Album 
{
    public List<Song> songs;
    public int year;
    public String name;
    
    public Album(String newName)
    {
        songs = new ArrayList<>();
        name = newName;
        year = 0;
    }
    
    //Pushes new Song onto album.
    public void addSong(Song song)
    {
        songs.add(song);
    }

    /**
     *
     * @param name
     * @return Optional based on whether or not the song was in this album
     */
    public Optional<String> getSongPath(String name)
    {
        try
        {
            String ret = null;
            for(Song song : songs)
            {
                if(song.name.equals(name))
                {
                    return Optional.of(new File(song.uri).toString());
                }               
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
