package view.gui;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;


public class PaintCanvas extends JComponent {
    Point startDrag, endDrag;
    Stack<Rectangle> undoStack = new Stack<>();
    Stack<Rectangle> redoStack = new Stack<>();
    List<Rectangle> rectangles = new ArrayList<>();
 
    public  PaintCanvas() {
       addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
             startDrag = new Point(e.getX(), e.getY());
             endDrag = startDrag;
             //repaint();
          }
 
          public void mouseReleased(MouseEvent e) {
             Rectangle rect = new Rectangle(startDrag.x, startDrag.y, e.getX() - startDrag.x, e.getY() - startDrag.y);
             undoStack.push(rect);
             redoStack.clear();
             rectangles.add(rect);
             startDrag = null;
             endDrag = null;
             repaint();
          }
       });
 
       addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
             endDrag = new Point(e.getX(), e.getY());
             repaint();
          }
       });
    }
 
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.black);
      for (Rectangle rect : rectangles) {
         g.fillRect(rect.x, rect.y, rect.width, rect.height);
      }

      if (startDrag != null && endDrag != null) {
         g.drawRect(startDrag.x, startDrag.y, endDrag.x - startDrag.x, endDrag.y - startDrag.y);
      }
   }
 
    public void undo() {
       if (!undoStack.empty()) {
          redoStack.push(undoStack.pop());
          rectangles.remove(rectangles.size() - 1);
          repaint();
       }
    }
 
    public void redo() {
       if (!redoStack.empty()) {
          undoStack.push(redoStack.pop());
          rectangles.add(undoStack.peek());
          repaint();
       }
    }
}
