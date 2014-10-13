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
public class MovingBlocks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Block block1 = new Block(5, 5);
        ArrayList<Block> array1 = new ArrayList<>(1);
        ArrayList<Block> array2 = new ArrayList();
        Grid testGrid1 = new Grid(array1, array2);
        Grid testGrid2 = new Grid(testGrid1.addLiveBlock(block1), array2);
        System.out.println("Yo");
        System.out.println("the first X Value is 5 : " + testGrid2.movingBlocks.get(0).x
                + " The Moved x value should be 6 : " + testGrid2.moveOneWay().movingBlocks.get(0).x);
        
        System.out.println("the first X Value is 6 : " + testGrid2.movingBlocks.get(0).x
                + " The Moved x value should be 7 : " + testGrid2.moveOneWay().movingBlocks.get(0).x);


    }

}
