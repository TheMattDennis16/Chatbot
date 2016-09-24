
package alie;

public class ALIE 
{

    public static SongLibrary songLib;
    public static Playing players = new Playing();

    public static void main(String[] args) {
        Lexicon lex = new Lexicon();
        songLib = new SongLibrary(Lexicon.AUDIO_LIBRARY);
        
        //System.out.print(lex.parseSentence("Hello ALIE"));
        System.out.print(lex.parseSentence("Play Bullet Ride"));
    }
}
