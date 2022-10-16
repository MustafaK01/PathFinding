import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Panel panel;
    public KeyHandler(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            this.panel.searchGoalPoint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
