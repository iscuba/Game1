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

    public ArrayList<Block> movingBlocks;
    public ArrayList<Block> deadBlocks;

    public Grid() {
        ArrayList<Block> firstDead = new ArrayList<>();
        ArrayList<Block> firstBorn = new ArrayList<>();
        Block db1 = new Block(4, 1);
        Block db2 = new Block(5, 1);
        Block db3 = new Block(6, 1);
        Block db4 = new Block(7, 1);

        firstDead.add(db1);
        firstDead.add(db2);
        firstDead.add(db3);
        firstDead.add(db4);

        Block mb1 = new Block(4, 2);
        Block mb2 = new Block(5, 2);
        Block mb3 = new Block(6, 2);
        Block mb4 = new Block(7, 2);
        
        firstBorn.add(mb1);
        firstBorn.add(mb2);
        firstBorn.add(mb3);
        firstBorn.add(mb4);
        
        this.movingBlocks = firstBorn;
        this.deadBlocks = firstDead;
    }

    private Grid(ArrayList<Block> moving, ArrayList<Block> dead) {
        super();
        this.movingBlocks = moving;
        this.deadBlocks = dead;
    }
//Both add functions add at the front of the arrayList 

    public ArrayList<Block> addDeadBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < deadBlocks.size(); i++) {
            temp.add(deadBlocks.get(i));
        }
        temp.add(block);
        return temp;
//        for (int i = 0; i < deadBlocks.size(); i++) {
//            if (deadBlocks.get(i).isTaken(block)) {
//                break;
//            } else {
//                deadBlocks.add(0, block);
//            }
//        }
    }

    public ArrayList<Block> addLiveBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < movingBlocks.size(); i++) {
            temp.add(i, movingBlocks.get(i));
        }
        temp.add(block);
        return temp;
        //movingBlocks.add(block);
        // return movingBlocks;
//        for (int i = 0; i < movingBlocks.size(); i++) {
//            if (movingBlocks.get(i).isTaken(block)) {
//                break;
//            } else {
//                movingBlocks.add(0, block);
//                movingBlocks.trimToSize();
//            }
//        }
//        return movingBlocks;
    }

    public ArrayList<Block> removeLiveBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < movingBlocks.size(); i++) {
            if (!movingBlocks.get(i).equalBlock(block)) {
                temp.add(i, movingBlocks.get(i));
            } else {
            }
        }
        return temp;
//        for (int i = 0; i < movingBlocks.size(); i++) {
//            if (movingBlocks.get(i).isTaken(block)) {
//                //might be remove(block) but also that might not work 
//                movingBlocks.remove(i);
//            } else {
//                break;
//            }
//        }

    }

    // Because this is mutation, I think this function is feeding itself.
    // When the user clicks the stop, the drawing code needs to check:
    //   - if all the blocks in the live array are on top of the blocks of the  
    //      most recent row in the dead array (this will be the set of blocks  
    //      that share the highest Y value) 
    //     THEN: don't add any of these stray blocks to the dead block list.
    // checkBlockArray looks where the MovingArray was stopped and adds/ doesnt
    //  add the blocks to the dead block list.
    public Grid changeBlockArray() {
        ArrayList<Block> tempMove = new ArrayList<>();
        ArrayList<Block> tempDead = new ArrayList<>();
        for (int i = 0; i < deadBlocks.size(); i++) {
            tempDead.add(deadBlocks.get(i));
        }
        for (int i = 0; i < movingBlocks.size(); i++) {
            for (int j = 0; j < deadBlocks.size(); j++) {
                if (!movingBlocks.get(i).isOnTop(deadBlocks.get(j))) {
                    int blX = movingBlocks.get(i).x;
                    int blY = movingBlocks.get(i).y + 1;
                    tempMove.add(new Block(blX, blY));
                    tempDead.add(movingBlocks.get(i));
                } else {
                }
            }
        }
        return new Grid(tempMove, tempDead);
    }

    public boolean LooseHuh() {
        return movingBlocks.isEmpty();
    }

    public boolean WinHuh() {
        return deadBlocks.get(deadBlocks.size() - 1).y == 15;
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
        return (movingBlocks.get(num).x >= width - 1);
    }

    // made this return a new grid so that
    //how do I get this to work?
    public World onTick() {
        if (LooseHuh()) {
            return this.endOfWorld("You loose");
        } else if (WinHuh()) {
            return this.endOfWorld("WINNER!");

        } else {
            return moveOneWay();
        }
    }

    public WorldImage backdrop = new RectangleImage(new Posn(0, 0), 120, 150, new Black());

    public WorldImage makeImage() {
        for (int i = 0; i < movingBlocks.size(); i++) {
            WorldImage b = movingBlocks.get(i).makeBlock();
            new OverlayImages(backdrop, b);
        }
        for (int j = 0; j < deadBlocks.size(); j++) {
            return new OverlayImages(backdrop, deadBlocks.get(j).makeBlock());
        }
        /// CHANGE!!! DO not trust 
        return backdrop;

    }

    public World onKeyEvent(String ke) {
        switch (ke) {
            case "x":
                return this.endOfWorld("Goodbye");
            case "s":
                return changeBlockArray();
            default:
                return this.moveOneWay();
//            return new Grid(this.movingBlocks.moveBlocks(ke));
        }
    }

    // might be easier to only move the blocks right, and have them wrap the screen 
    public Grid moveOneWay() {
        for (int i = 0; i < movingBlocks.size(); i++) {
            int x = movingBlocks.get(i).x;
            int ex = x + 1;
            int why = movingBlocks.get(i).y;
            if (ex >= width) {
                movingBlocks.set(i, new Block(1, why));
            } else {
                movingBlocks.set(i, new Block(ex, why));
            }

        }
        return new Grid(movingBlocks, deadBlocks);
    }
    
    public static void testGrid(){
                Block block2 = new Block(5, 5);
        Block block3 = new Block(5, 4);
        ArrayList<Block> arrayA = new ArrayList<>();
        ArrayList<Block> arrayB = new ArrayList();
        
        Grid testGridA = new Grid(arrayA, arrayB);
        testGridA.movingBlocks = arrayA;
        testGridA.deadBlocks = arrayB;
        
        Grid testGridB = new Grid(testGridA.addLiveBlock(block2), testGridA.addDeadBlock(block3));
        Grid afterChange = testGridB.changeBlockArray();

        System.out.println("this should return true: " + block3.isOnTop(block2));
        System.out.println("this should return false: " + block2.isOnTop(block3));

        System.out.println("testGridB's movingBlocks should have 1 thing and has: " + testGridB.movingBlocks.size());
        System.out.println("testGridB's DeadBlocks should have 1 thing and has: " + testGridB.deadBlocks.size());
        System.out.println(" I expect movingBlocks after change to have 1 block in it and it has: " + afterChange.movingBlocks.size());
        System.out.println("The first grid has 2 blocks: Live block: (5,5) deadblock: (5,4) ("
                + testGridB.movingBlocks.get(0).x + ", " + testGridB.movingBlocks.get(0).y + ") and ("
                + testGridB.deadBlocks.get(0).x + "," + testGridB.deadBlocks.get(0).y
                + ") after call to changeBlock Array: should have 3 blocks total (5,5) (5,4) (5,6): ("
                + afterChange.movingBlocks.get(0).x + ", " + afterChange.movingBlocks.get(0).y+ ")" 
                + "(" + afterChange.deadBlocks.get(0).x + "," + afterChange.deadBlocks.get(0).y + ") ("
                + afterChange.deadBlocks.get(1).x + ", " + afterChange.deadBlocks.get(1).y + ")");
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
    
     public Grid moveOneWay() {
     ArrayList<Block> temp = new ArrayList<>();
     System.out.println("size of moving Blocks: " + movingBlocks.size());
     System.out.println("size of temp: " + temp.size());
     for (int i = 0; i < movingBlocks.size(); i++) {
     int x = movingBlocks.get(i).x++;
     int ex = x+1;
     int why = movingBlocks.get(i).y;
     if (ex >= width) {
     temp.add(i, new Block(1, why));
     } else {
     temp.add(i, new Block(ex, why));
     }
     System.out.println("size of temp: " + temp.size());
     movingBlocks = temp;
     }
     return new Grid(movingBlocks, deadBlocks);
     }
    
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
     return new Grid(movingBlocks, deadBlocks);
     }
    
     // This DOES NOT WORK 
     // This looks like it should be recursive for it to work 
     public Grid moveBlocks2() {
     if (offRightGridHuh()) {
     do {
     for (int i = 0; i < movingBlocks.size(); i++) {
     movingBlocks.get(i).x--;
     }
     } while (movingBlocks.get(0).x != 0);
     } else if (offLeftGridHuh()) {
     do {
     for (int i = 0; i < movingBlocks.size(); i++) {
     movingBlocks.get(i).x++;
     }
     } while (movingBlocks.get(movingBlocks.size() - 1).x < 12);
     }
     return new Grid(movingBlocks, deadBlocks);
     }

     // For testing? Maybe Later I will need it
     public Grid makeAGrid() {
     Block block1 = new Block(5, 5);
     ArrayList<Block> array1 = new ArrayList<>(1);
     ArrayList<Block> array2 = new ArrayList();
     Grid testGrid = new Grid(array1, array2);
     testGrid.addLiveBlock(block1);
     return testGrid;
     }
    
     public Grid changeBlockArray() {
     // Checking like this means that My blocks are going to move in a 
     //   "block grid" so make them move block by block.
     int why = deadBlocks.get(0).y;
     System.out.println("why in changeBlockArray is " + why);
     //look at the deadBlock array and determine the highest Y value to usein the loop 
     System.out.println("DeadBlocks size is:" + deadBlocks.size());
     System.out.println("MovingBlocks size is:" + movingBlocks.size());
     for (int i = 0; i < movingBlocks.size(); i++) {
     for (int j = 0; j < deadBlocks.size(); j++) {
     //although this will look through all the blocks to check, and it shouldn't have to 
     if (!movingBlocks.get(i).isOnTop(deadBlocks.get(j))) {
     System.out.println("removing the live block W/O adding ");
     movingBlocks = removeLiveBlock(movingBlocks.get(i));
     } else {
     System.out.println("time doing inner loop: " + j);
     int blX = movingBlocks.get(i).x + 1;
     int blY = movingBlocks.get(i).y + 1;
     System.out.println("making the new block at point: (" + blX + ", " + blY + ") ");
     movingBlocks = addLiveBlock(new Block(blX, blY));
     deadBlocks = addDeadBlock(movingBlocks.get(i));
     movingBlocks = removeLiveBlock(movingBlocks.get(i));

     }
     System.out.println("time doing the outer i loop: " + j);
     }
     }
     return new Grid(movingBlocks, deadBlocks);
     }
    
     */
}
