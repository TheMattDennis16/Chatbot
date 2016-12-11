package songlib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import types.StringPair;

public class Artist {
    public List<Album> albums;
    public String name;

    public Artist(String newName) {
        albums = new ArrayList<>();
        name = newName;
    }

    public void addAlbum(Album toAdd) {
        albums.add(toAdd);
    }

    public List<StringPair> hasSongName(String songName) {
        List<StringPair> results = new ArrayList<>();
        for (Album album : albums) {
            Optional<String> optionalPath = album.getSongPath(songName);
            if (optionalPath.isPresent()) {
                results.add(new StringPair(album.name, optionalPath.get()));
            }
        }
        return results;
    }
}
