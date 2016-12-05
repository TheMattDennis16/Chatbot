import lowlevel.SongPlayer;
import java.io.File;
import java.util.ArrayList;

public class Playing 
{
    public SongPlayer currentPlaying;
    public ArrayList<String> queuePlaying;
    private PlayingLoop loop = null;
    
    public Playing()
    {
        queuePlaying = new ArrayList<>();
        loop = new PlayingLoop();
        //loop.start();
        loop.start();
    }
    
    public void resumeCurrent()
    {
        currentPlaying.songStart();
    }
    
    public String startNext()
    {
        String toReturn = "";
        if(queuePlaying.size() > 0)
        {
            currentPlaying = new SongPlayer(new File(queuePlaying.get(0)));
            queuePlaying.remove(0);
        }
        else
            toReturn = "Nothing queued up to play.";
        return toReturn;
    }
    
    public void stopCurrent()
    {
        currentPlaying.songStop();
    }
    
    public void endLoop()
    {
        loop.end();
    }
    
    public class PlayingLoop extends Thread
    {
        private volatile boolean flag = false;
        
        public void run()
        {
            setName("PlayingLoop");
            while(!flag)
            {
                if(currentPlaying == null || currentPlaying.getClipState())
                {
                    startNext();
                }
            }
        }
        
        public void end()
        {
            flag = true;
        }
    }
}
