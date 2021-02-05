package chess;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ListExample  
{  
     ListExample(){  
        JFrame f= new JFrame(); 
       
        DefaultListModel<String> l1 = new DefaultListModel<>();  
          l1.addElement("Item1");  
          l1.addElement("Item2");  
          l1.addElement("Item3");  
          l1.addElement("Item4");  
          JList<String> list = new JList<>(l1);  
           JScrollPane scroll = new JScrollPane(list);
          list.setBounds(100,100, 75,75);  
          //scroll.setBounds(100,100, 75,75);
          f.add(list);  
          f.add(scroll);
          scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
  
          f.setSize(400,400);  
          //f.setLayout(null);  
          f.setVisible(true);  
     }  
public static void main(String args[])  
    {  
   new ListExample();  
    }}  