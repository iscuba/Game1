/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;


import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
/**
 *
 * @author Isabella
 */
public class Block {

    public int x;
    public int y;
//    public Posn position;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
//        this.position = new Posn(x, y);
    }
    
    public WorldImage drawBlock(){
        return new RectangleImage(new Posn(this.x, this.y),10,10, new Green());
    }

    // like equal? 
    public boolean isTaken(Block block) {
        return ((block.x == this.x) && (block.y == this.y));
    }

    public boolean isNotTop(Block block) {
        return !(this.x == block.x);
    }

    public boolean isTop(Block block) { 
        return (this.x == block.x);
    }
   
    public WorldImage makeBlock(){
        return new RectangleImage(new Posn(this.x*10, this.y*10),10,10,new Green());
    }
    

}
