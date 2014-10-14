/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

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

    // like equal? 
    public boolean equalBlock(Block block) {
        return ((block.x == this.x) && (block.y == this.y));
    }

    public boolean isNotTop(Block block) {
        return !(this.x == block.x);
    }

    public boolean isOnTop(Block block) { 
        return (this.x == block.x) && (this.y + 1 == block.y);
    }

    public FrameImage makeBlock(){
        return new FrameImage(new Posn(this.x*20, this.y*20),20,20,new Green());
    }
    
    

}
