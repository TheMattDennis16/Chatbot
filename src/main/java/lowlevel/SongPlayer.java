package lowlevel;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class SongPlayer
{
    private AdvancedPlayer player = null;
    private int pausedOnFrame = 0;
    private boolean pauseRequested = false;
    private boolean isFinished = false;
    
    public void run()
    {}
    
    public SongPlayer(File uri)
    {
        try
        {
            player = new AdvancedPlayer(new FileInputStream(uri));
            
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt)
                {
                    if(pauseRequested)
                    {
                        pausedOnFrame = evt.getFrame();
                    }
                    else
                    {
                        isFinished = true;
                    }
                }
            });
            player.play();
            
        }   
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**  Acts as both the start and resume function.
     *
     */
    public void songStart()
    {
        try
        {
            if(pausedOnFrame == 0)
            {
                player.play();
                pausedOnFrame = 0;
            }
            else
            {
                player.play((int)pausedOnFrame, Integer.MAX_VALUE);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**  Pause the currently playing song
     *
     */
    public void pause()
    {
        pauseRequested = true;
        player.stop();
        //pausePosition = clip.getMicrosecondPosition();
    }
    
    /*
     * true means current ms position >= length.
     *
     */
    public boolean getClipState()
    {
        return isFinished;            
    }
    
    public void songStop()
    {
        player.stop();
        pausedOnFrame = 0;
    }
}
