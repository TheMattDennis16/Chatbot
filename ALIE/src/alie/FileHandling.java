
package alie;

import alie.types.Tuple;
import alie.types.GrammarLine;
import alie.songlib.Artist;
import alie.songlib.Album;
import alie.songlib.Song;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandling 
{
    public ArrayList<GrammarLine> getGrammars()
    {
        ArrayList<String> lines = getLines();
        ArrayList<GrammarLine> pairs = new ArrayList<>();
        
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
    
    private ArrayList<String> getLines()
    {
        ArrayList<String> lines = new ArrayList<>();
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
    
    public ArrayList<Artist> buildMusicLibrary(String root)
    {
        ArrayList<Artist> results = new ArrayList<>();
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
