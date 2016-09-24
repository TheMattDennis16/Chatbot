
package alie.types;

public class GrammarLine 
{
    public int tokenCount;
    public String grammar;
    public String function;
    public String response;

    public GrammarLine(int count, String gram, String func, String resp)
    {
        tokenCount = count;
        grammar = gram;
        function = func;
        response = resp;
    }
}
