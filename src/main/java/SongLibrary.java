import java.util.ArrayList;
import java.util.List;

import songlib.Artist;
import types.Tuple;
import types.StringPair;

public class SongLibrary 
{
    public List<Artist> artists;
    
    public SongLibrary(String url)
    {
        artists = new ArrayList<>();
        FileHandling fh = new FileHandling();
        artists = fh.buildMusicLibrary(url);
    }
    
    public List<Tuple> getSong(String song)
    {
        ArrayList<Tuple> results = new ArrayList<>();
        for (Artist artist : artists)
        {
            List<StringPair> albumResults = artist.hasSongName(song);
            for(StringPair pair : albumResults)
            {
                results.add(new Tuple(artist.name, pair.left, pair.right));
            }
        }
        return results;
    }
    
    public List<StringPair> getSong(String song, String artist)
    {
        List<StringPair> results = new ArrayList<>();
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
