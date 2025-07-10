package platform.platform;


import android.content.Context;
import android.text.Editable;
import android.view.inputmethod.BaseInputConnection;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;


class InputConnection extends BaseInputConnection 
{

	public editable m_editable;

	public impact m_impact;

	int m_iBatch;

	public bind m_bind;

	boolean m_bPendingSelectionUpdate;


	public static native boolean BeginBatchEdit(boolean bSuper);

	public static native boolean EndBatchEdit(boolean bSuper);

	public static native boolean CommitText(String text, int newCursorPosition, boolean bSuper);

	public static native boolean DeleteSurroundingText(int beforeLength, int afterLength, boolean bSuper);

	public static native boolean SetComposingText(String text, int newCursorPosition, boolean bSuper);

	public static native boolean SetComposingRegion(int start, int end, boolean bSuper);

	public static native boolean SetSelection(int start, int end, boolean bSuper);

	public static native boolean FinishComposingText(boolean bSuper);

	public InputConnection(impact impact, boolean bFullEditor) 
	{

		super(impact, bFullEditor);

		m_bPendingSelectionUpdate = false;

		m_iBatch = 0;

		m_impact = impact;

		m_editable = impact.m_editable;

		m_bind = impact.m_bind;

	}


	public boolean isBatchEdit()
	{
		
		return m_iBatch > 0;

	}


	@Override
	public boolean beginBatchEdit() 
	{

		boolean bSuper = super.beginBatchEdit();

		m_iBatch++;

		boolean bReturn = BeginBatchEdit(bSuper);

		return bReturn;

	}


	public void updateSelection()
	{

		if(isBatchEdit())
		{

			m_bPendingSelectionUpdate = true;				

		}
		else
		{

			m_bPendingSelectionUpdate = false;

			InputMethodManager manager = (InputMethodManager) m_impact.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

			manager.updateSelection(
					m_impact,
					m_bind.m_iInputMethodManagerSelectionStart,
					m_bind.m_iInputMethodManagerSelectionEnd,
					m_bind.m_iInputMethodManagerCandidateStart,
					m_bind.m_iInputMethodManagerCandidateEnd);

		}

	}


	@Override
	public boolean endBatchEdit() 
	{

		boolean bSuper = super.endBatchEdit();

		boolean bReturn = EndBatchEdit(bSuper);

		m_iBatch--;

		//if(!isBatchEdit() && m_bPendingSelectionUpdate)
		//{
		//
		//	updateSelection();
		//
		//}

		return bReturn;

	}


	@Override
	public Editable getEditable() 
	{

		return m_editable;

	}


	@Override
	public CharSequence getTextBeforeCursor(int n, int flags) 
	{

		if(m_bind.m_strEditorText == null)
		{

			return "";

		}

		int iStart = Math.min(m_bind.m_iEditorSelectionStart, m_bind.m_iEditorSelectionEnd);

		String string = m_bind.m_strEditorText.substring(Math.max(0, iStart - n), iStart);

		return string;

	}


	@Override
	public CharSequence getTextAfterCursor(int n, int flags) 
	{

		if(m_bind.m_strEditorText == null)
		{

			return "";

		}

		int iEnd = Math.max(m_bind.m_iEditorSelectionStart, m_bind.m_iEditorSelectionEnd);

		String string = m_bind.m_strEditorText.substring(iEnd, Math.min(iEnd + n, m_bind.m_strEditorText.length()));

		return string;

	}


	@Override
	public CharSequence getSelectedText(int flags) 
	{

		if(m_bind.m_strEditorText == null)
		{

			return "";

		}

		int iStart = Math.min(m_bind.m_iEditorSelectionStart, m_bind.m_iEditorSelectionEnd);

		int iEnd = Math.max(m_bind.m_iEditorSelectionStart, m_bind.m_iEditorSelectionEnd);

		iStart = Math.max(iStart, 0);

		iEnd = Math.min(iEnd, m_bind.m_strEditorText.length());

		String string = m_bind.m_strEditorText.substring(iStart, iEnd);

		return string;

	}


	@Override
	public boolean deleteSurroundingText(int beforeLength, int afterLength) 
	{

		boolean bSuper = super.deleteSurroundingText(beforeLength, afterLength);

		boolean bReturn = DeleteSurroundingText(beforeLength, afterLength, bSuper);

		return bReturn;

	}


	@Override
	public boolean setComposingText(CharSequence text, int newCursorPosition)
	{

		boolean bSuper = super.setComposingText(text, newCursorPosition);

		String str = text.toString();

		boolean bReturn = SetComposingText(str, newCursorPosition, bSuper);

		//updateSelection();

		return bReturn;

	}


	@Override
	public boolean commitText(CharSequence text, int newCursorPosition)
	{

		boolean bSuper = super.commitText(text, newCursorPosition);

		String str = text.toString();

		boolean bReturn = CommitText(str, newCursorPosition, bSuper);

		return bReturn;

	}


	@Override
	public boolean setComposingRegion(int start, int end)
	{

		boolean bSuper = super.setComposingRegion(start, end);

		boolean bReturn = SetComposingRegion(start, end, bSuper);

		return bReturn;

	}

		
	@Override
	public boolean setSelection(int start, int end)
	{

		boolean bSuper = super.setSelection(start, end);

		boolean bReturn = SetSelection(start, end, bSuper);

		return bReturn;

	}


	// just adding this to show that text is being committed.
	@Override
	public boolean finishComposingText() 
	{
      
		boolean bSuper = super.finishComposingText();
			
		boolean bReturn = FinishComposingText(bSuper);

		return bReturn;

	}


}

