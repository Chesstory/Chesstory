/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/*
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.

see http://www.wtfpl.net/txt/copying/ for no more detail.
*/

/**
 *
 * @author Samuel Delepoulle <delepoulle@lisic.univ-littoral>
 */
public class Main {
    public static void main(String[] args){
        Echiquier e = new Echiquier();
        e.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");        
        //e.setFEN("rn1q1rk1/pb1p1pnp/2pP2p1/2p1P1P1/2P5/3Q3B/PP1NK2P/5RR1 b KQkq");
        System.out.println("représentation de l'échiquier");
        System.out.println(e);
        
        System.out.println("son code FEN");
        System.out.println(e.getFEN());
        
        e.executeDeplacement("c2-c4");
        System.out.println("après déplacement");
        System.out.println(e);
        System.out.println(e.getFEN());
        
        e.construitPositionsAccessibles();
        //e.affichePositionAccessibles();
    }
}