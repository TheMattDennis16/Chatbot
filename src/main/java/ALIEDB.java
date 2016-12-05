import java.util.ArrayList;
import java.util.List;

import types.Tuple;
import types.StringPair;

public class ALIEDB 
{
    public String lookupSong(ArrayList<String> song)
    {
        if(song.size() <= 0)
            return null;
        String output = "";
        List<Tuple> tuples = ALIE.songLib.getSong(song.get(0));
        if(tuples.size() == 1)
        {
            ALIE.players.queuePlaying.add(tuples.get(0).right.toString());
            output = song.get(0) + " by " + tuples.get(0).left + " is now playing.";
        }
        else if(tuples.size() > 1)
        {
            //Multiple URI's found.
            output = "Multiple results found, please select the one you want: ";
            for (Tuple tuple : tuples)
            {
                output += tuple.centre + " by " + tuple.left + ", ";
            }
        }
        else
        {
            output = "The song: " + song + " wasn't found. Please try again.";
        }
        return output;
    }
    
    public String lookupSongBy(ArrayList<String> details)
    {
        if(details.size() <= 1)
            return null;
        String output = "";
        List<StringPair> tuples = ALIE.songLib.getSong(details.get(0), details.get(1));
        if(tuples.size() == 1)
        {
            ALIE.players.queuePlaying.add(tuples.get(0).right);
            output = details.get(0) + " by " + details.get(1) + " is now playing.";
        }
        else if(tuples.size() > 1)
        {
            output = "Multiple found, please select the one you want: ";
            for(StringPair pair : tuples)
            {
                output += pair.left + " from " + pair.right + ", ";
            }
        }
        else
        {
            output = details.get(0) + " by " + details.get(1) + " was not found. Please try again.";
        }
        return output;
    }
}
