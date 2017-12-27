
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class MakepopupMenu {
    JPopupMenu popupMenu;
    JMenuItem changeFont;
    JMenuItem cut ;
    JMenuItem copy ;
    JMenuItem paste ;
    JMenuItem findAndReplace;
    ClassForCodeAreaTab chngFont ;
    MakepopupMenu(ClassForCodeAreaTab cc) {
        chngFont = cc;
        this.changeFont = new JMenuItem(new AbstractAction("Change Font") {
            @Override
            public void actionPerformed(ActionEvent ae) { 
                TextStylesInternalFrame fontFrame = new TextStylesInternalFrame(chngFont);
                chngFont.jframe.jDesktopPane1.add(fontFrame);
                fontFrame.setLocation(495, 110);
                chngFont.jframe.jDesktopPane1.moveToFront(fontFrame);   
                fontFrame.show();                              
            }
        });
        popupMenu = new JPopupMenu();
        this.cut = new JMenuItem(new AbstractAction("Cut") {
            @Override
            public void actionPerformed(ActionEvent ae) {
              //  chngFont.jframe.cutString = chngFont.codeArea.getSelectedText() ;
              try{  
              chngFont.jframe.selectedString = chngFont.codeArea.getSelectedText();
            chngFont.codeArea.replaceSelection("");
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            this.copy = new JMenuItem(new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent ae) {
              try{
                chngFont.jframe.selectedString = chngFont.codeArea.getSelectedText() ;
                
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
         this.paste= new JMenuItem(new AbstractAction("Paste") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
            JScrollPane scroll = (JScrollPane) chngFont.jframe.jTabbedPane.getSelectedComponent();
            JViewport viewport = scroll.getViewport();                        // first extract viewport from scrollpane then extract JTextArea from Viewport
            JTextArea area = (JTextArea) viewport.getView();

            
                area.insert(chngFont.jframe.selectedString, area.getCaretPosition());
            
        } catch (Exception e) {

        }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
         this.findAndReplace= new JMenuItem(new AbstractAction("Find & Replace") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                FindAndReplaceFrame1 fr= new FindAndReplaceFrame1(chngFont);
                chngFont.jframe.jDesktopPane1.add(fr);
                fr.setLocation(495, 110);
                chngFont.jframe.jDesktopPane1.moveToFront(fr);   
                fr.show();  
            
        } catch (Exception e) {
               e.printStackTrace();
        }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        popupMenu.add(changeFont);
        popupMenu.add(cut);
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(findAndReplace);
    }
    public void showpop(MouseEvent evt) {

        this.popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}