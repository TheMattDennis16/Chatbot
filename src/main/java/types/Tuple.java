
package types;

public class Tuple
{
    public Object left;
    public Object centre;
    public Object right;

    public Tuple(Object l, Object c, Object r)
    {
        left = l;
        centre = c;
        right = r;
    }
    
    public Tuple()
    {
        left = new Object();
        centre = new Object();
        right = new Object();
    }
}
