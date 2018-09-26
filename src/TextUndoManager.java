import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

public class TextUndoManager extends AbstractUndoableEdit 
    implements UndoableEditListener
{     

private static final long serialVersionUID = 802925658741955933L;
private String lastEditName = null;
private int lastStart = 0;
private ArrayList<TextCompoundEdit> edits = new ArrayList<TextCompoundEdit>();
private TextCompoundEdit current;
private int pointer = -1;
private int groupIndex = 0;
private String groupName = null;

public void undoableEditHappened(
UndoableEditEvent e)
{
boolean isNeedStart = false;
UndoableEdit edit = e.getEdit();

if (! (edit instanceof AbstractDocument.DefaultDocumentEvent))
{ return; }

AbstractDocument.DefaultDocumentEvent event = (AbstractDocument.DefaultDocumentEvent) edit;

int start = event.getOffset();

String editName;

if (groupName != null)
{ editName = groupName; }
else
{ editName = event.getType().toString(); }

if (current == null)
{ isNeedStart = true; }

else if ((lastEditName == null) || 
(! lastEditName.equals(editName)))
{ isNeedStart = true; }

else if (groupName == null)
{            
if ((event.getType() == DocumentEvent.EventType.INSERT) &&
(start != (lastStart + 1)))
{ isNeedStart = true; }
else if ((event.getType() == DocumentEvent.EventType.REMOVE) &&
(start != (lastStart - 1)))
{ isNeedStart = true; }
}

while (pointer < edits.size() - 1)
{
edits.remove(edits.size() - 1);
isNeedStart = true;
}

if (isNeedStart)
{ createCompoundEdit(); }

current.addEdit(edit);

lastEditName = editName;
lastStart = start;
}

public void startEditGrouping()
{
groupName = "Group-" + groupIndex++;
}

public void stopEditGrouping()
{
groupName = null;
}

private void createCompoundEdit()
{
if (current == null)
{ current = new TextCompoundEdit(); }
else if (current.getLength() > 0)
{ current = new TextCompoundEdit(); }

edits.add(current);
pointer++;
}

public void undo() throws CannotUndoException
{
if (! canUndo())
{ throw new CannotUndoException(); }

TextCompoundEdit u = edits.get(pointer);
u.undo();
pointer--;
}

public void redo() throws CannotUndoException
{
if (! canRedo())
{ throw new CannotUndoException(); }

pointer++;
TextCompoundEdit u = edits.get(pointer);
u.redo();
}

public boolean canUndo()
{ 
return pointer >= 0; 
}

public boolean canRedo()
{
return (edits.size() > 0) && (pointer < (edits.size() - 1));
}
}