import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    final int maxColumn = 15;
    final int maxRow = 10;
    final int nodeSize = 60;
    final int screenWidth = nodeSize*maxColumn;
    final int screenHeight = nodeSize * maxRow;

    Node[][] node = new Node[maxColumn][maxRow];
    Node startingPointNode, currentPointNode , goalPointNode;
    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow,maxColumn));
        int col = 0;
        int row = 0;
        while(col < maxColumn && row < maxRow){
            node[col][row] = new Node(col,row);
            this.add(node[col][row]);
            col++;
            if(col==maxColumn){
                col = 0;
                row++;
            }
        }
        setStartPointNode(0,1);
        setGoalPointNode(1,9);
    }

    private void setGoalPointNode(int col,int row){
        node[col][row].setGoalPoint();
        goalPointNode = node[col][row];
    }

    private void setStartPointNode(int col,int row){
        node[col][row].setStartPoint();
        startingPointNode = node[col][row];
        currentPointNode = startingPointNode;
    }

}
