/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songlib;

public class Song {
    public String name;
    public String uri;

    public Song(String newName, String newURI) {
        name = newName;
        try {
            uri = newURI;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}