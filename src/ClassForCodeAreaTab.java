
import java.awt.Font;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
//public interface ActionListener  extends EventListener
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class ClassForCodeAreaTab extends NewApplication implements MouseListener, MouseMotionListener {

    RSyntaxTextArea syntaxTextArea = new RSyntaxTextArea(20, 60);
    JTextArea codeArea;
    NewApplication jframe;

    ClassForCodeAreaTab(NewApplication p) {
        syntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
        codeArea = syntaxTextArea;
        jframe = p;
        codeArea.addMouseListener(this);
        codeArea.addMouseMotionListener(this);
        codeArea.setWrapStyleWord(true);
        Font f = new Font("Monospaced", 1, 20);
        codeArea.setFont(f);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {

        MakepopupMenu popupMenu = new MakepopupMenu(this);
        if (me.isPopupTrigger()) {
            popupMenu.showpop(me);
        }

        
    }
    //private void showPop(MouseEvent evt){

    //}
    @Override
    public void mouseExited(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        int x = me.getX(), y = me.getY();
        String g = "";
        for (int i = x; i > 0; i = i / 10) {
            g = i % 10 + g;
        }
        // System.out.println(x + "  " + g);
        // this.setVisible(true);
        this.jframe.cursor.setText(x + "," + y);

    }

   
}
