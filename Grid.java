/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

import java.util.ArrayList;
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
public class Grid extends World {

    int width = 12;
    int height = 15;

    private ArrayList<Block> movingBlocks;
    private ArrayList<Block> deadBlocks;

    public Grid(ArrayList<Block> moving, ArrayList<Block> dead) {
        super();
        this.movingBlocks = moving;
        this.deadBlocks = dead;
    }
//Both add functions add at the front of the arrayList 

    public void addDeadBlock(Block block) {
        for (int i = 0; i < deadBlocks.size(); i++) {
            if (deadBlocks.get(i).isTaken(block)) {
                break;
            } else {
                deadBlocks.add(0, block);
            }
        }
    }

    public void addLiveBlock(Block block) {
        for (int i = 0; i < movingBlocks.size(); i++) {
            if (movingBlocks.get(i).isTaken(block)) {
                break;
            } else {
                movingBlocks.add(0, block);
                movingBlocks.trimToSize();
            }
        }
    }

    public void removeLiveBlock(Block block) {
        for (int i = 0; i < movingBlocks.size(); i++) {
            if (movingBlocks.get(i).isTaken(block)) {
                //might be remove(block) but also that might not work 
                movingBlocks.remove(i);
            } else {
                break;
            }
        }
    }

    // When the user clicks the stop, the drawing code needs to check:
    //   - if all the blocks in the live array are on top of the blocks of the  
    //      most recent row in the dead array (this will be the set of blocks  
    //      that share the highest Y value) 
    //     THEN: don't add any of these stray blocks to the dead block list.
    // checkBlockArray looks where the MovingArray was stopped and adds/ doesnt
    //  add the blocks to the dead block list.
    public void changeBlockArray() {
        // Checking like this means that My blocks are going to move in a 
        //   "block grid" so make them move block by block.
        int why = deadBlocks.get(0).y;
        //look at the deadBlock array and determine the highest Y value to usein the loop 
        for (int i = 0; i < movingBlocks.size(); i++) {
            for (int j = 0; j < deadBlocks.size(); j++) {
                //although this will look through all the blocks to check, and it shouldnt have to 
                if ((movingBlocks.get(i).isTop(deadBlocks.get(j))) && (deadBlocks.get(j).y == why)) {
                    addDeadBlock(movingBlocks.get(i));
                    removeLiveBlock(movingBlocks.get(i));
                } else {
                    removeLiveBlock(movingBlocks.get(i));
                }
            }
        }
    }

    public boolean LooseHuh() {
        return movingBlocks.isEmpty();
    }

    //Defines when the movingBlocks should bounce off the side of the grid and 
    //  change direction using block units
    // since we are checking where the center of the squares, it will appear that 
    // the sides are at 0 and 12.
    public boolean offLeftGridHuh() {
        int num = movingBlocks.size() - 1;
        return (movingBlocks.get(num).x <= 1);
    }

    public boolean offRightGridHuh() {
        int num = movingBlocks.size() - 1;
        return (movingBlocks.get(num).x <= 11);
    }
    
    // made this return a new grid so that
    public Grid moveBlocks(String direction) {
        if (direction.equals("right")) {
            for (int i = 0; i <= (movingBlocks.size() - 1); i++) {
                //add "1" to the x value of each block
                movingBlocks.get(i).x++;
            }
        } else {
            for (int i = 0; i <= (movingBlocks.size() - 1); i++) {
                //add "1" to the x value of each block
                movingBlocks.get(i).x--;
            }
        }
        return new Grid(movingBlocks,deadBlocks);
    }

    public World onTick() {
        //if the blocks are out of bounds make them switch direction they are moving. 
        do {
            return moveBlocks("right");
        } while (!offRightGridHuh());
        do {
            return moveBlocks("left");
        } while (!offLeftGridHuh());

    }

    public WorldImage backdrop = new RectangleImage(new Posn(0, 0), 120, 150, new Black());

    public WorldImage makeImage() {
        for (int i = 0; i < movingBlocks.size(); i++) {
            OverlayImages(backdrop, movingBlocks.get(i).makeBlock());
        }
        for (int j = 0; j < deadBlocks.size(); j++) {
            return OverImages(backdrop, deadBlocks.get(j).makeBlock());
        }

    }

    public World onKeyEvent(String ke) {
        if (ke.equals("x")) {
            return this.endOfWorld("Goodbye");
        } else {
            return this;
//            return new Grid(this.movingBlocks.moveBlocks(ke));
        }
    }


    /*  FUNCTION CEMETARY

     // how many blocks should he new array have? 
     // Well,,:
     // how many blocks, with the max Y value, are on the deadblocks list?
     //I dont need this function.. change block array does it all 
     public void newLiveBlocks() {
     int maxy = deadBlocks.get(0).y;
     int countWhy = countBlocks(deadBlocks, maxy);
     /// CHANGE What do I use for x values?? ehh  could use i since thats 
     //getting bigger in the for loop       
     //int ex = 0;
     // plus 1 block length 
     int why = (deadBlocks.get(0).y + 1);
     // is this where I decide where I want to spawn the new blocks?
     for (int i = 0; i <= countWhy; i++) {
     addLiveBlock(new Block(i, why));
     }
     }

     //when to end the game:
     //    sees if all the blocks in movingBlocks are off the dead blocks
     // make arraylist of booleans to hold 
     //   ** what do I call this function on? do I have to call it on something? 
     public ArrayList allBlocksOffHuh() {
     // can I just say when newLiveBlocks() = 0 
        
     // or if movingBlocks is empty.. since i have a function that sets the 
     //      arrays to what they need to be.
     int why = deadBlocks.get(0).y;
     ArrayList arrlist = new ArrayList();
     for (int i = 0; i < movingBlocks.size(); i++) {
     for (int j = 0; j < deadBlocks.size(); j++) {
     //"b" would be outside of its local defn 
     boolean b = (movingBlocks.get(i).isTop(deadBlocks.get(j)) && deadBlocks.get(j).y == why);
     return b;
     } 
     // I want to set the arraylist at position i to be the return value of the for loop
     arrlist.set(i,/*what do i say ??);
     }
     return arrlist;
     }
     //Counts how many blocks' y values are equal to "why" so tells us how many
     //   new blocks to add to movingBlocks 
     public int countBlocks(ArrayList<Block> blocklist, int why) {
     int count = 0;
     for (int i = 0; i > blocklist.size(); i++) {
     if (blocklist.get(i).y == why) {
     count++;
     }
     }
     return count;
     }

     public WorldImage makeArrayImage(ArrayList<Block> arrayl){
     for (int i=0; i<arrayl.size();i++ ){
     arrayl.get(i).blockImage();
     }
     }
    
     */
}
