import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextUndoPlainDocument extends PlainDocument 
	{    
	
	private static final long serialVersionUID = 241956443028778066L;
	private TextUndoManager undoManager;

	 TextUndoPlainDocument(
	   TextUndoManager theManager)
	   {
	    super();
	    undoManager = theManager;
	    this.addUndoableEditListener(undoManager);
	   }

	 public void replace(
	   int offset, 
	   int length,
	   String text, 
	   AttributeSet attrs) throws BadLocationException
	   {
	    if (length == 0)
	      { super.replace(offset,length,text,attrs); }
	    else
	      {
	       undoManager.startEditGrouping();
	       super.replace(offset,length,text,attrs); 
	       undoManager.stopEditGrouping();
	      }
	   }
	}