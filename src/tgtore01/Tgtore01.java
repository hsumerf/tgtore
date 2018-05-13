/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tgtore01;

import static java.time.Clock.system;
import java.util.ArrayList;

/**
 *
 * @author H S Umer Farooq
 */
public class Tgtore01 {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Node>[] transition_table;
    
    public static void main(String[] args) {
        // TODO code application logic here
        int states = 4;
        //initializing Array
        transition_table = new ArrayList[states];
        for(int i=0;i<states;i++)
        {//initializing arrayList
            transition_table[i]=new ArrayList<>();
        }
        Node first_node = new Node(1,"a");
        transition_table[0].add(first_node);
        
        first_node = new Node(3,"b");
        transition_table[0].add(first_node);
        
        Node sec_node = new Node(0,"a");
        transition_table[1].add(sec_node);
        
        sec_node = new Node(2,"b");
        transition_table[1].add(sec_node);
        
        Node third_node = new Node(3,"a");
        transition_table[2].add(third_node);
        
        third_node = new Node(1,"b");
        transition_table[2].add(third_node);
        
         Node fourth_node = new Node(2,"a");
         transition_table[3].add(fourth_node);
         
         fourth_node = new Node(0,"b");
         transition_table[3].add(fourth_node);
         
      
        
        ArrayList<Node> incom = new ArrayList<>();
        ArrayList<Node> outgo = new ArrayList<>();
        ArrayList<Node> loops = new ArrayList<>();        
        
        String concatenate_String = "";
        for (int i = 0; i < transition_table.length; i++) 
        {
         reduceEdges(i);
         incom = incoming(i);
         outgo = outgoing(i);        
         loops = get_loops(i);
            for (int j = 0; j < incom.size(); j++) 
            {

                for (int k = 0; k < outgo.size(); k++) 
                {
                    if(loops.isEmpty())
                    {
                        concatenate_String = incom.get(j).passed_strings + outgo.get(k).passed_strings;
                        Node concatenate_node = new Node(outgo.get(k).state,concatenate_String);
                        transition_table[incom.get(j).state].add(concatenate_node);
                    }
                    else
                    {
                        concatenate_String = incom.get(j).passed_strings + "(" + loops.get(0).passed_strings +")*"+ outgo.get(k).passed_strings;
                        Node concatenate_node = new Node(outgo.get(k).state,concatenate_String);
                        transition_table[incom.get(j).state].add(concatenate_node);

                    }
                }
   
            }
            }
        
        System.out.println(transition_table[states-1].get(0).passed_strings);
    }
    
    
    private static ArrayList<Node> outgoing(int index)
    {
        ArrayList<Node> temp = new ArrayList<>();
        for (int i = 0; i < transition_table[index].size(); i++) 
        {
            if(index != transition_table[index].get(i).state)
                temp.add(transition_table[index].get(i));
        }
        return temp;
    }
    
    
    private static ArrayList<Node> incoming(int index)
    {
        //init ArraList of Node class type
        ArrayList<Node> temp = new ArrayList<>();
        // loop till the lenth of transition table
        for (int i = index+1; i < transition_table.length ; i++) 
        {
            //loop till the end of list of nodes in a row
            for (int j = 0; j < transition_table[i].size(); j++) 
            {
                // if passing no. index is in the list of nodes present
                if(index==transition_table[i].get(j).state)
                {
                    // putting row no. in state of node
                   transition_table[i].get(j).state = i;
                   // adding incoming node in temp Arraylist
                    temp.add(transition_table[i].get(j));
                    //  removing from transition table that node
                    transition_table[i].remove(j);
    
                }
                    
            }
   
        }
        return temp;
    }
    private static void reduceEdges(int index)
    {
        String new_string;
        for (int i = 0; i < transition_table[index].size(); i++) 
        {
            for (int j = i+1; j < transition_table[index].size(); j++) 
            {
                if(transition_table[index].get(i).state==transition_table[index].get(j).state)
                {
                    //System.out.println(transition_table[index].get(i).state+" and "+ transition_table[index].get(j).state);
                    new_string = "("+transition_table[index].get(i).passed_strings +"+"+ transition_table[index].get(j).passed_strings+")";
                    transition_table[index].set(i, new Node(transition_table[index].get(i).state,new_string));
                    transition_table[index].remove(j);
                    
                }
           
   
            }
        }
    }
    
    private static ArrayList<Node> get_loops(int index)
    {
        ArrayList<Node> temp = new ArrayList<>();
        for (int i = 0; i < transition_table[index].size(); i++) 
        {
            if(index == transition_table[index].get(i).state)
                temp.add(transition_table[index].get(i));
        }
        return temp;
    }
    
        
}