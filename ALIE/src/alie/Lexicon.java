
package alie;

import alie.types.Tuple;
import alie.types.IntStringPair;
import alie.types.GrammarLine;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Lexicon 
{
    protected static String AUDIO_LIBRARY = "C:\\Users\\Matt\\Music\\Sorted Music\\";
    private ArrayList<GrammarLine> grammars;
    private ArrayList<IntStringPair> words;
    private ALIEDB db;
    
    public Lexicon()
    {
        db = new ALIEDB();
        grammars = new FileHandling().getGrammars();
        words = new ArrayList<>();
    }

    public boolean isPlural(String word)
    {
        return false;
    }

    private String getTokenEnd(int index, String[] sentenceWords)
    {
        String results = "";
        for(int i = index; i < sentenceWords.length; i++)
        {
            results += " " + sentenceWords[i];
        }
        return results.trim();
    }
    
    private ArrayList<Tuple> getTokenMiddle(int index, String[] sentenceWords, String[] grammarWords)
    {
        int toAdd = 0;
        ArrayList<Tuple> results = new ArrayList<>();
        for(int i = index; i < sentenceWords.length; i++)
        {
            if(!grammarWords[i - toAdd - results.size()].equals("*"))
                continue;
                
            Tuple tup = new Tuple();
            tup.left = i;
            tup.right = 0;
            tup.centre = "";
            for(int a = i; a < sentenceWords.length; a++)
            {
                //Two statements can be combined, leave seperate for debugging
                // purposes for now.
                try
                {
                    if (grammarWords[i + 1].equals(sentenceWords[a]))
                    {
                        //Hit end
                        i = a;
                        break;
                    }
                    else if(a + 1 >= sentenceWords.length || i - toAdd + 1 >= grammarWords.length)
                    {
                        //Hit end.
                        String result = getTokenEnd(a, sentenceWords);
                        results.add(new Tuple(a, result, result.split(" ").length));
                        return results;
                    }
                    else
                    {
                        //Not end
                        toAdd++;
                        tup.centre += " " + sentenceWords[a];
                        tup.right = Integer.parseInt(tup.right.toString()) + 1;
                    }
                }
                catch(Exception e)
                {
                    if(i + 1 > grammarWords.length)
                    {
                        String result = getTokenEnd(a, sentenceWords);
                        results.add(new Tuple(a, result, result.split(" ").length));
                        return results; 
                    }
                    return null;
                }
            }
            tup.centre = tup.centre.toString().trim();
            results.add(tup);
        }
        return results;
    }
    
    private String getWordAfterToken(String[] sentence)
    {
        boolean hasPassedToken = false;
        String returnWord = "";
        for(String word : sentence)
        {
            if(hasPassedToken)
            {
                returnWord = word; 
                break;
            }
            if(word.equals('*'))
                hasPassedToken = true;
        }
        return returnWord;
    }
    
    public String parseSentence(String sentence)
    {
        sentence = sentence.toLowerCase();
        String output = "";
        String[] sentenceWords = sentence.split(" ");

        for (GrammarLine newSentence : grammars)
        {
            String cpy = newSentence.grammar.toLowerCase();
            ArrayList<IntStringPair> wildcards = new ArrayList<>();
            if (cpy.charAt(0) != sentence.charAt(0))
                continue;

            String[] grammarWords =  cpy.split(" ");
            boolean isValid = true;
            
            for (int i = 0; i < sentenceWords.length; i++)
            {
                if (grammarWords[i].equals("*"))
                {                 
                    if(i+1 == grammarWords.length)
                    {
                        String result = getTokenEnd(i, sentenceWords);
                        wildcards.add(new IntStringPair(i, result));
                        break;
                    }
                    else
                    {
                        ArrayList<Tuple> results = getTokenMiddle(i, sentenceWords, grammarWords);
                        if(results == null) 
                        {
                            isValid = false; 
                            break;
                        }
                        
                        //Arrays.asList not working - Uncompilable code? No error found in IDE.
                        ArrayList<String> sentenceCpy = new ArrayList<>();
                        for(String word : sentenceWords)
                        {
                            sentenceCpy.add(word);
                        }
                        int wordsRemoved = 0;
                        for (Tuple tuple : results)
                        {
                            try
                            {
                                int originalWhere = (Integer.parseInt(tuple.left.toString()) + Integer.parseInt(tuple.right.toString()) - wordsRemoved);
                                int posShift = 0;
                                for(int a = Integer.parseInt(tuple.left.toString()) - wordsRemoved;
                                        a < originalWhere;
                                        a++)
                                {
                                    sentenceCpy.remove(a - posShift);
                                    posShift++;
                                    wordsRemoved++;
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        wordsRemoved = 0;
                        int wordsAdded   = 0;
                        for (Tuple tuple : results)
                        {
                            sentenceCpy.add(Integer.parseInt(tuple.left.toString()) - wordsRemoved + wordsAdded, "*");
                            wordsRemoved += Integer.parseInt(tuple.right.toString());
                            wordsAdded++;
                        }
                        if(Arrays.deepEquals(grammarWords, sentenceCpy.toArray()))
                        {
                            isValid = true;
                            for(Tuple tups : results)
                            {
                                wildcards.add(new IntStringPair(Integer.parseInt(tups.left.toString()), tups.centre.toString()));
                            }
                            break;
                        }
                        else
                        {
                            isValid = false;
                        }
                    }                    
                }
                else if (!grammarWords[i].equals(sentenceWords[i]))
                {
                    //Sentences do not match.
                    isValid = false;
                    break;
                }
            }
            
            if (isValid)
            {
                if(newSentence.function.length() > 0)
                {
                    try
                    {
                        ArrayList<String> wildwords = new ArrayList<>();

                        if(wildcards.size() > 0)
                        {
                            for(IntStringPair pairs : wildcards)
                            {
                                wildwords.add(pairs.right);
                            }
                        }
                        
                        Class[] cArg = new Class[1];
                        cArg[0] = ArrayList.class;
                        Method m = db.getClass().getMethod(newSentence.function, cArg);
                        output += (Object)m.invoke(db, (Object)wildwords).toString();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    output = insertWildcards(newSentence.response.split(" "), wildcards);
                }
                break;
            }
        }

        if (output.equals(""))
            output = "Sorry, I didn't understand that.";

        return output;
    }

    private String insertWildcards(String[] sentence, ArrayList<IntStringPair> wildcards)
    {
        String ret = "";
        for (IntStringPair position : wildcards)
        {
            sentence[position.left] = position.right;
        }

        for (String word : sentence)
        {
            ret += (word + " ");
        }

        return ret;
    }
}
