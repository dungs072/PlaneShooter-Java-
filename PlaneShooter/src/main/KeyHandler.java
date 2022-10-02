package main;
import java.awt.event.*;
public class KeyHandler implements KeyListener{

    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean firePressed;
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        handlePressedAndReleasedKey(code, true);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        handlePressedAndReleasedKey(code, false);
        
    }
    private void handlePressedAndReleasedKey(int code,boolean state)
    {
        if(code==KeyEvent.VK_W)
        {
            upPressed = state;
        }
        if(code==KeyEvent.VK_S)
        {
            downPressed = state;
        }
        if(code==KeyEvent.VK_A)
        {
            leftPressed = state;
        }
        if(code==KeyEvent.VK_D)
        {
            rightPressed = state;
        }
        if(code==KeyEvent.VK_SPACE)
        {
            firePressed = state;
        }
    }
    public boolean IsUpPressed(){return upPressed;}
    public boolean IsDownPressed(){return downPressed;}
    public boolean IsLeftPressed(){return leftPressed;}
    public boolean IsRightPressed(){return rightPressed;}
    public boolean IsFirePressed(){return firePressed;}
}
