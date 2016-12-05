import types.Tuple;
import types.GrammarLine;
import songlib.Artist;
import songlib.Album;
import songlib.Song;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandling 
{
    public List<GrammarLine> getGrammars()
    {
        List<String> lines = getLines();
        List<GrammarLine> pairs = new ArrayList<>();
        
        for(String line : lines)
        {
            String[] words = line.split(",");
            int count = 0;
            for(char character : words[0].toCharArray())
            {
                if(character == '*')
                    count++;
            }
            pairs.add(new GrammarLine(count, words[0], words[1], words[2]));
        }
        
        Collections.sort(pairs, new Comparator<GrammarLine>() {
            @Override
            public int compare(GrammarLine l1, GrammarLine l2)
            {
                return l2.tokenCount - l1.tokenCount;
            }
        });
    
        return pairs;
    }
    
    private List<String> getLines()
    {
        List<String> lines = new ArrayList<>();
        try 
        {
            Files.lines(Paths.get("grammars.txt")).forEach(s -> lines.add(s));
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    
    public List<Artist> buildMusicLibrary(String root)
    {
        List<Artist> results = new ArrayList<>();
        File[] directories = new File(root).listFiles(File::isDirectory);
        for(File f : directories)
        {
            Artist a = new Artist(f.getName().toLowerCase());
            File[] albums = new File(root+"\\"+a.name).listFiles(File::isDirectory);
            for(File album : albums)
            {
                String[] name = album.getName().split(" ");
                Album al = new Album(name[0].toLowerCase());
                
                if(name.length > 1)
                {
                    al.year = Integer.parseInt(name[1].substring(1, 5));
                }
                
                File[] songs = new File(album.getAbsolutePath()+"\\").listFiles(File::isFile);
                for(File song : songs)
                {
                    Song newSong = new Song(
                        (song.getName().substring(0, song.getName().length() - 4)).toLowerCase(),
                        song.getAbsolutePath());
                    al.addSong(newSong);
                }
                
                a.addAlbum(al);
            }
            results.add(a);
        }
        //An artist has an album, which is a collection of songs.
        return results;
    }
}
