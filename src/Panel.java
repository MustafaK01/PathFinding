import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    final int maxColumn = 25;
    final int maxRow = 20;
    final int nodeSize = 60;
    final int screenWidth = nodeSize*maxColumn;
    final int screenHeight = nodeSize * maxRow;
    public boolean reached = false;
    Node[][] node = new Node[maxColumn][maxRow];
    Node startingPointNode, currentPointNode , goalPointNode, solidNode;
    ArrayList<Node> listOfOpen = new ArrayList<>();
    ArrayList<Node> listOfChecked = new ArrayList<>();

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow,maxColumn));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
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
        setStartPointNode(1,1);
        setGoalPointNode(9,1);
        setSolidNode(6,6);
        setSolidNode(6,0);
        setSolidNode(6,1);
        setSolidNode(6,2);
        setSolidNode(6,3);
        setSolidNode(6,4);
        setSolidNode(6,5);

        this.setCostsOfNodes();
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

    private void setSolidNode(int col,int row){
        node[col][row].setSolid();
        solidNode = node[col][row];
    }

    private void getCost(Node node){
        //Get g cost
        int xDistance = Math.abs(node.column - startingPointNode.column);
        int yDistance = Math.abs(node.row - startingPointNode.row);
        node.gCost = xDistance+yDistance;

        //h cost
        xDistance = Math.abs(node.column - goalPointNode.column);
        yDistance = Math.abs(node.row - goalPointNode.row);
        node.hCost = xDistance+yDistance;

        node.fCost = node.gCost + node.hCost;
        if(node!=startingPointNode && node!=goalPointNode){
            node.setText("<html>F>"+node.fCost+"<br>"+"G>"+node.gCost+"<br>"+"C:"+node.column
                    +"<br>"+"R:"+node.row+"</html>");
            node.setFont(node.getFont().deriveFont(9f));
        }
    }

    private void setCostsOfNodes(){
        int col = 0, row = 0;
        while(col<maxColumn && row<maxRow){
            this.getCost(node[col][row]);
            col++;
            if(col == maxColumn){
                col = 0;
                row++;
            }
        }
    }

    public void searchGoalPoint(){
        //while for auto search, it can be changed to 'if' if you don't want auto search
        while(!reached){
            int col = currentPointNode.column;
            int row = currentPointNode.row;
            currentPointNode.setToChecked();
            listOfChecked.add(currentPointNode);
            listOfOpen.remove(currentPointNode);

            if(row-1>0){
                openNode(node[col][row-1]);
            }
            if(col-1>0){
                openNode(node[col-1][row]);
            }
            if(col+1<maxColumn){
                openNode(node[col+1][row]);
            }
            if(row+1<maxRow){
                openNode(node[col][row+1]);
            }

            int bestNodeIndex=0;
            int bestNodefCost=999;

            for (int i = 0; i < listOfOpen.size(); i++) {
                if(listOfOpen.get(i).fCost<bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = listOfOpen.get(i).fCost;
                } else if (listOfOpen.get(i).fCost == bestNodefCost) {
                    if(listOfOpen.get(i).gCost<listOfOpen.get(i).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            currentPointNode = listOfOpen.get(bestNodeIndex);
            if(currentPointNode == goalPointNode){
                reached = true;
                this.getThePath();
            }
        }
    }

    public void getThePath(){
        //it draws the path from goal point to start point
        Node current = goalPointNode;
        while(current!=startingPointNode){
           current = current.parent;
           if(current!=startingPointNode){
               current.setToPath();
           }
        }
    }

    public void openNode(Node node){
        if( !node.open&& !node.checked && !node.solid){
            node.setToOpen();
            node.parent = currentPointNode;
            listOfOpen.add(node);
        }
    }

//    public void setSolidByClick(Node node,int col,int row){
//
//        if(!node.solid){
//            node.
//        }
//
//    }


}
