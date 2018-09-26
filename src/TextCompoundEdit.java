import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;

public class TextCompoundEdit extends CompoundEdit
	{
	
	private static final long serialVersionUID = -6370254163875263723L;
	private boolean isUnDone = false;

	 public int getLength()
	   {
	    return edits.size();
	   }

	 public void undo() throws CannotUndoException
	   {
	    super.undo();
	    isUnDone = true;
	   }

	 public void redo() throws CannotUndoException
	   {
	    super.redo();
	    isUnDone = false;
	   }

	 public boolean canUndo()
	   {
	    return (edits.size() > 0) && (! isUnDone);
	   }

	 public boolean canRedo()
	   {
	    return (edits.size() > 0) && isUnDone;
	   }
	}