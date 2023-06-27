import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

    Node parent;
    int column;
    int row;
    int gCost; //Distance between current node and start node
    int fCost; //sum of g and f costs (G+H). its indicates the total distance cost from start to end
    int hCost;//Distance between current node and goal(end) node
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int column,int row){
        this.column = column;
        this.row = row;
        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);
    }

    public void setStartPoint(){
        setBackground(Color.red);
        setForeground(Color.white);
        setText("Start Point");
        this.start = true;
    }

    public void setGoalPoint(){
        setBackground(Color.blue);
        setForeground(Color.black);
        setText("Goal ! ");
        this.goal = true;
    }

    public void setSolid(){
        setBackground(Color.DARK_GRAY);
        setForeground(Color.DARK_GRAY);
        solid = true;
    }

    public void setToOpen(){
        open = true;
    }

    public void setToChecked(){
        if(!start && !goal){
            setBackground(Color.ORANGE);
            setForeground(Color.black);
        }
        checked = true;
    }

    public void setToPath(){
        setBackground(Color.green);
        setForeground(Color.DARK_GRAY);
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        setBackground(Color.yellow);
        System.out.println("Row: "+this.getRow() +" "+"Column: "+ this.getColumn());
    }
}
