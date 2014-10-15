/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

import javalib.worldimages.*;
import javalib.colors.*;
import java.util.Random;
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

//    public boolean isNotTop(Block block) {
//        return !(this.x == block.x);
//    }

    public boolean isOnTop(Block block) { 
        return (this.x == block.x) && (this.y + 1 == block.y);
    }

    public FrameImage makeBlock(){
        return new FrameImage(new Posn(this.x*20, this.y*20),20,20,new Green());
    }
    
    public static Block makeRandomBlock(){
        int max = 11;
        int min = 0;
        Random rando = new Random();
        int randomX = rando.nextInt((max - min) + 1) + min;
        Random rand = new Random();
        int randomY = rand.nextInt((max - min) + 1) + min;
        return new Block(randomX,randomY);
    }
    
    public static Block makeBlockUnder(Block block){
        int ex = block.x ;
        int why = block.y + 1;
        return new Block(ex,why);
    }
    
    public static void testBlock(){
        
        //tests for equalblocks:
        Block b1 = makeRandomBlock();
        System.out.println("block at: ("+ b1.x +","+ b1.y+ ") should be equal to itself true is:"+ b1.equalBlock(b1));
        Block b2 = makeRandomBlock();
        System.out.println("block at: ("+ b2.x +","+ b2.y+ ") should be equal to itself true is:"+ b2.equalBlock(b2));
        Block b3 = makeRandomBlock();
        System.out.println("block at: ("+ b3.x +","+ b3.y+ ") should be equal to itself true is:"+ b3.equalBlock(b3));
        Block b4 = makeRandomBlock();
        System.out.println("block at: ("+ b4.x +","+ b4.y+ ") should be equal to itself true is:"+ b4.equalBlock(b4));
        Block b5 = makeRandomBlock();
        System.out.println("block at: ("+ b5.x +","+ b5.y+ ") should be equal to itself true is:"+ b5.equalBlock(b5));
        Block b6 = makeRandomBlock();
        System.out.println("block at: ("+ b6.x +","+ b6.y+ ") should be equal to itself true is:"+ b6.equalBlock(b6));
        Block b7 = makeRandomBlock();
        System.out.println("block at: ("+ b7.x +","+ b7.y+ ") should be equal to itself true is:"+ b7.equalBlock(b7));
        Block b8 = makeRandomBlock();
        System.out.println("block at: ("+ b8.x +","+ b8.y+ ") should be equal to itself true is:"+ b8.equalBlock(b8));
        Block b9 = makeRandomBlock();
        System.out.println("block at: ("+ b9.x +","+ b9.y+ ") should be equal to itself true is:"+ b9.equalBlock(b9));
        Block b10 = makeRandomBlock();
        System.out.println("block at: ("+ b10.x +","+ b10.y+ ") should be equal to itself true is:"+ b10.equalBlock(b10));
        
        //testing to see if isOnTop works:
        Block uB1 = makeBlockUnder(b1);
        System.out.println("block at (" + b1.x+ "," + b1.y + ") is on top of block at: ("+ uB1.x +"," + uB1.y +"): "+ b1.isOnTop(uB1));
        Block uB2 = makeBlockUnder(b2);
        System.out.println("block at (" + b2.x+ "," + b2.y + ") is on top of block at: ("+ uB2.x +"," + uB2.y +"): "+ b2.isOnTop(uB2));
        Block uB3 = makeBlockUnder(b3);
        System.out.println("block at (" + b3.x+ "," + b3.y + ") is on top of block at: ("+ uB3.x +"," + uB3.y +"): "+ b3.isOnTop(uB3));
        Block uB4 = makeBlockUnder(b4);
        System.out.println("block at (" + b4.x+ "," + b4.y + ") is on top of block at: ("+ uB4.x +"," + uB4.y +"): "+ b4.isOnTop(uB4));
        Block uB5 = makeBlockUnder(b5);
        System.out.println("block at (" + b5.x+ "," + b5.y + ") is on top of block at: ("+ uB5.x +"," + uB5.y +"): "+ b5.isOnTop(uB5));
        Block uB6 = makeBlockUnder(b6);
        System.out.println("block at (" + b6.x+ "," + b6.y + ") is on top of block at: ("+ uB6.x +"," + uB6.y +"): "+ b6.isOnTop(uB6));
        Block uB7 = makeBlockUnder(b7);
        System.out.println("block at (" + b7.x+ "," + b7.y + ") is on top of block at: ("+ uB7.x +"," + uB7.y +"): "+ b7.isOnTop(uB7));
        Block uB8 = makeBlockUnder(b8);
        System.out.println("block at (" + b8.x+ "," + b8.y + ") is on top of block at: ("+ uB8.x +"," + uB8.y +"): "+ b8.isOnTop(uB8));
        Block uB9 = makeBlockUnder(b9);
        System.out.println("block at (" + b9.x+ "," + b9.y + ") is on top of block at: ("+ uB9.x +"," + uB9.y +"): "+ b9.isOnTop(uB9));
        Block uB10 = makeBlockUnder(b10);
        System.out.println("block at (" + b10.x+ "," + b10.y + ") is on top of block at: ("+ uB10.x +"," + uB10.y +"): "+ b10.isOnTop(uB10));
        
        
    }
}
