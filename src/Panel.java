import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel {
    final int maxColumn = 15;
    final int maxRow = 10;
    final int nodeSize = 60;
    final int screenWidth = nodeSize*maxColumn;
    final int screenHeight = nodeSize * maxRow;
    public boolean reached = false;
    Node[][] node = new Node[maxColumn][maxRow];
    Node startingPointNode, currentPointNode , goalPointNode, solidNode;
    ArrayList<Node> listOfOpen = new ArrayList<>();
    ArrayList<Node> listOfChecked = new ArrayList<>();

    private Timer timer;
    private int countdown = 6;
    Random r = new Random();

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

        setStartPointNode(r.nextInt(maxColumn),r.nextInt(maxRow));
        setGoalPointNode(r.nextInt(maxColumn),r.nextInt(maxRow));
        for (int i = 0; i < 45; i++) {
            setSolidNode(r.nextInt(maxColumn),r.nextInt(maxRow));
        }
        this.setCostsOfNodes();

        setFocusable(true);
        addEnterKeyListener();
    }

    private void addEnterKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startCountdown();
                }
            }
        });
    }

    private void removeEnterKeyListener() {
        KeyListener[] keyListeners = getKeyListeners();
        for (KeyListener listener : keyListeners) {
            if (listener instanceof KeyAdapter) {
                removeKeyListener(listener);
            }
        }
    }

    private void updateTitle() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setTitle("Countdown: " + countdown);
        }
    }

    private void startCountdown() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                if (countdown < 1) {
                    timer.stop();
                    restart();
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateTitle();
                    }
                });
            }
        });
        timer.start();
        removeEnterKeyListener();
    }

    private void restart() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            Container contentPane = frame.getContentPane();
            contentPane.removeAll();
            frame.dispose();
            JFrame newWindow = new JFrame();
            newWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newWindow.setResizable(false);
            newWindow.add(new Panel());
            newWindow.pack();
            newWindow.setLocationRelativeTo(null);
            newWindow.setVisible(true);
            this.countdown = 5;
        }
    }

    private void setGoalPointNode(int col,int row){
        if(!node[col][row].start&&!node[col][row].solid) {
            node[col][row].setGoalPoint();
            goalPointNode = node[col][row];
        }
    }

    private void setStartPointNode(int col,int row){
        if(!node[col][row].goal&&!node[col][row].solid) {
            node[col][row].setStartPoint();
            startingPointNode = node[col][row];
            currentPointNode = startingPointNode;
        }
    }

    private void setSolidNode(int col,int row){
        if(!node[col][row].goal&&!node[col][row].start){
            node[col][row].setSolid();
            solidNode = node[col][row];
        }
    }

    private void getCost(Node node){
        if(node!=null){
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
        }else JOptionPane.showMessageDialog(this, "There is some issues with nodes", "", JOptionPane.INFORMATION_MESSAGE);
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
        //while is for auto search, it can be changed to 'if' if you don't want auto search
        try{
            while(!reached){
                int col = currentPointNode.column;
                int row = currentPointNode.row;
                currentPointNode.setToChecked();
                listOfChecked.add(currentPointNode);
                listOfOpen.remove(currentPointNode);

                if(row-1>=0){
                    openNode(node[col][row-1]);
                }
                if(col-1>=0){
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
        }catch (IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(this, "Couldn't find way", "", JOptionPane.INFORMATION_MESSAGE);
            restart();
        }
    }

    public void openNode(Node node){
        if( !node.open&& !node.checked && !node.solid){
            node.setToOpen();
            node.parent = currentPointNode;
            listOfOpen.add(node);
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
}
