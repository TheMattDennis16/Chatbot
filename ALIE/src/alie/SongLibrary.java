package alie;

import java.util.ArrayList;
import alie.songlib.Artist;
import alie.types.Tuple;
import alie.types.StringPair;

public class SongLibrary 
{
    public ArrayList<Artist> artists;
    
    public SongLibrary(String url)
    {
        artists = new ArrayList<>();
        FileHandling fh = new FileHandling();
        artists = fh.buildMusicLibrary(url);
    }
    
    public ArrayList<Tuple> getSong(String song)
    {
        ArrayList<Tuple> results = new ArrayList<>();
        for (Artist artist : artists)
        {
            ArrayList<StringPair> albumResults = artist.hasSongName(song);
            for(StringPair pair : albumResults)
            {
                results.add(new Tuple(artist.name, pair.left, pair.right));
            }
        }
        return results;
    }
    
    public ArrayList<StringPair> getSong(String song, String artist)
    {
        ArrayList<StringPair> results = new ArrayList<>();
        for(Artist artname : artists)
        {
            if(!artname.name.equals(artist))
                continue;
            results = artname.hasSongName(song);
            break;
        }
        return results;
    }
}
