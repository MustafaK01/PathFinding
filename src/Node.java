import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

    Node parent;
    int column;
    int row;
    int gCost;
    int fCost;
    int hfCost;
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


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        setBackground(Color.yellow);
    }
}
