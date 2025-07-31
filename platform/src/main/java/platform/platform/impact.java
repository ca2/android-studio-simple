package platform.platform;


import platform.platform.enumerate.enumerate;
import java.util.List;
import java.util.ArrayList;
import java.lang.Runnable;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.text.InputType;
import android.net.Uri;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import static java.util.concurrent.TimeUnit.*;



class impact extends View
{

   public main_activity m_mainactivity;

	private boolean m_bAskedToShowSoftInput;

   private Bitmap m_bitmap;

   private long m_lStartTime;

	private int m_iStep;

	private int m_iWidth;

	private int m_iHeight;

	private boolean m_bShowKeyboardAfterwards2;

	private boolean m_bFocus;

	platform.platform.InputConnection m_inputconnection;

	enumerate m_enumerate;

	public bind m_bind;

	public editable m_editable;

	public List < message_box > m_messageboxlist;

	public message_box		m_messagebox;

   private static native void render_impact(Bitmap bitmap, long time_ms);

	private static native void native_on_timer();

   private static native void lButtonDown(float x, float y);

   private static native void mouseMove(float x, float y);

   private static native void lButtonUp(float x, float y);

   private static native void keyDown(int keycode);

   private static native void keyUp(int keycode);

   private static native void keyPreImeDown(int keycode, int iUni);

   private static native void keyPreImeUp(int keycode, int iUni);

   private static native void onReceivedShowKeyboard();

   private static native void onReceivedHideKeyboard();

   private static native void onText(String str);

	private static native void aura_size_changed();

	//private static native boolean aura_on_text_commit(String str, int newCursorPosition);

	//public task_scheduler m_scheduler;
	
	private final ScheduledExecutorService m_scheduler =
     Executors.newScheduledThreadPool(1);

	private ScheduledFuture<?> m_timer;

	
   public impact(main_activity mainactivity) 
   {

		super(mainactivity);

		m_editable = new editable();

		m_messageboxlist = new ArrayList < message_box >();
		
//		setEnabled(false);

//		setFocusable(false);

		setEnabled(true);

		setFocusable(true);

		setFocusableInTouchMode(true);

		m_mainactivity = mainactivity;

		m_bind = m_mainactivity.m_bind;

		m_lStartTime = System.currentTimeMillis();

	}


	public void on_editable_replace(final int start, final int end, CharSequence s, int tbstart, int tbend)
	{

		String str = s.toString();

		Log.d("on_editable_replace", "Text : \"" + str + "\"");

	}


	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
  
		super.onSizeChanged(w, h, oldw, oldh);

  		m_iWidth = w;

		m_iHeight = h;

		m_bitmap = Bitmap.createBitmap(m_iWidth, m_iHeight, Bitmap.Config.ARGB_8888);

		m_mainactivity.m_bind.m_iWidth = m_iWidth;

		m_mainactivity.m_bind.m_iHeight = m_iHeight;

		try
		{

			if(!m_mainactivity.application_is_started())
			{

				m_mainactivity.start_application();

			}
			else
			{

				aura_size_changed();

			}

		}
		catch(UnsatisfiedLinkError unsatisfiedLinkError)
		{

			String strUnsatisfiedLinkError = unsatisfiedLinkError.getMessage();

			Log.i("platform.platform.main_activity", strUnsatisfiedLinkError);

		}


		m_mainactivity.update_mem_free_available();

		if(m_timer == null)
		{

			Runnable timer = new timer(this);

   		m_timer = m_scheduler.scheduleAtFixedRate(timer, 50, 50, MILLISECONDS);

		}

	}


   public void on_impact_timer()
	{

		native_on_timer();

		if(m_bind.m_lMessageBoxSequence != 0)
		{

			message_box pmessagebox = new message_box(
					m_bind.m_lMessageBoxSequence,
					m_bind.m_strMessageBox,
					m_bind.m_strMessageBoxCaption,
					m_bind.m_iMessageBoxButton);

			m_messageboxlist.add(pmessagebox);

			m_bind.m_lMessageBoxSequence = 0;

		}

		step();

		if(m_bind.m_bRedraw)
		{

			invalidate();
						
		}

		if(m_bind.m_bLockListFileEnumerate)
		{

			if(m_bind.m_strListFileEnumerate.length() > 0)
			{

				if(m_enumerate == null)
				{

					m_enumerate = new enumerate();

				}

				m_mainactivity.requestReadExternalStoragePermission();

				String strListFileEnumerate = new String(m_bind.m_strListFileEnumerate);

				m_bind.m_strListFileEnumerate = "";

				m_enumerate.start(strListFileEnumerate, m_mainactivity);

				m_bind.m_bLockListFileEnumerate = false;

			}

		}
		
	}


	void step()
	{

		m_iStep++;

		if(m_messagebox == null)
		{

			if(m_messageboxlist.size() > 0)
			{

				m_messagebox = m_messageboxlist.get(0);

				m_messageboxlist.remove(0);

				m_messagebox.display(m_mainactivity);

			}

		}

		if (m_bind.m_bHideKeyboard) 
		{
	  
			m_bind.m_bHideKeyboard = false;

			Log.d("com.android_app.impact", "onDraw Start Hiding Soft Keyboard");

			onReceivedHideKeyboard();

			if(m_bAskedToShowSoftInput)
			{

				m_bAskedToShowSoftInput = false;

				InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				///manager.hideSoftInputFromWindow (getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

				manager.hideSoftInputFromWindow (getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

			}

			clearFocus();

			Log.d("com.android_app.impact", "m_bind.m_bHideKeyboard");

		}

		if(m_bind.m_bEditFocusSet)
		{

			m_bind.m_bEditFocusSet = false;

			if(m_bind.m_bShowKeyboard)
			{

				m_bind.m_bShowKeyboard = false;

				m_bShowKeyboardAfterwards2 = true;

			}

			InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

			manager.restartInput(this);

			requestFocus();

		}

		if (m_bind.m_bShowKeyboard) 
		{

			m_bind.m_bShowKeyboard = false;

			Log.d("com.android_app.impact", "m_bind.m_bShowKeyboard");

			onReceivedShowKeyboard();

			//setEnabled(true);

			//setFocusable(true);

			//setFocusableInTouchMode(true);

			requestFocus();

			if(!m_bAskedToShowSoftInput)
			{

				m_bAskedToShowSoftInput = true;

				InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				//manager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
			
				//manager.showSoftInput(this, InputMethodManager.SHOW_FORCED);

				manager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);

			}

		}

		if (m_bind.m_strOpenUrl != null && m_bind.m_strOpenUrl.length() > 0) 
		{

			openUrl(m_bind.m_strOpenUrl);
		
		}

		//if (m_bind.m_iShowMessageBox > 0) 
		//{

			//m_bind.m_iShowMessageBox = 0;

			//message_box(this, m_bind.m_strMessageBox, m_bind.m_strMessageBoxCaption, m_bind.m_iMessageBoxButton);

		//}

		if(m_bind.m_bEditorTextUpdated)
		{

			m_bind.m_bEditorTextUpdated = false;

			m_editable.set(m_bind.m_strEditorText);

		}

		if(m_inputconnection != null)
		{

			if(!m_inputconnection.isBatchEdit())
			{

				//if(m_bind.m_bInputMethodManagerUpdateSelection)
				//{

				//	m_bind.m_bInputMethodManagerUpdateSelection = false;

				//}

			}

		}

		if(m_bind.m_bEditFocusKill)
		{

			m_bind.m_bEditFocusKill = false;

		}

		if(m_iStep % 8 == 0)
		{
		
			m_mainactivity.update_mem_free_available();

		}

	}

	
   @Override
	protected void onDraw(Canvas canvas) 
	{

		render_impact(m_bitmap, System.currentTimeMillis() - m_lStartTime);

		canvas.drawBitmap(m_bitmap, 0, 0, null);

		if(m_bind.m_bRedraw)
		{

			m_bind.m_bRedraw = false;

		}

   }


   @Override
   public InputConnection onCreateInputConnection(EditorInfo outAttrs) 
   {

		m_inputconnection = new platform.platform.InputConnection(this, true);

		outAttrs.inputType = InputType.TYPE_CLASS_TEXT;

		outAttrs.initialSelStart = m_bind.m_iEditorSelectionStart;

		outAttrs.initialSelEnd = m_bind.m_iEditorSelectionEnd;

		String strEditor;

		if(m_bind.m_strEditorText != null)
		{

			strEditor = m_bind.m_strEditorText;

		}
		else
		{

			strEditor = "";

		}

		m_editable.set(strEditor);

		  if(m_bShowKeyboardAfterwards2)
		  {

				  m_bShowKeyboardAfterwards2 = false;

			m_bind.m_bShowKeyboard = true;

		  }

		return m_inputconnection;

   }


	public boolean onKey(View impact, int keyCode, KeyEvent keyEvent) 
	{

		int action = keyEvent.getAction();

		if (action == KeyEvent.ACTION_MULTIPLE) 
		{

			String s = keyEvent.getCharacters();
		 
		 /*
			if (!s.equals(DUMMY) && !s.equals("\n")) 
			{

				onText(s);

			}
			*/
	  }
	  else if (action == KeyEvent.ACTION_DOWN) 
	  {
	  
			keyPreImeDown(keyCode, keyEvent.getUnicodeChar());

	  }
	  else if (action == KeyEvent.ACTION_UP) 
	  {

			keyPreImeUp(keyCode, keyEvent.getUnicodeChar());

	  }

	  return false;

   }


	public boolean onTouchEvent(final MotionEvent ev)
	{
	
		if (ev.getAction() == MotionEvent.ACTION_DOWN) 
		{

			lButtonDown(ev.getX(), ev.getY());

		}
		else if (ev.getAction() == MotionEvent.ACTION_MOVE) 
		{

			mouseMove(ev.getX(), ev.getY());

		}
		else if (ev.getAction() == MotionEvent.ACTION_UP) 
		{

			lButtonUp(ev.getX(), ev.getY());

		}

		return true;

   }

   
   public boolean onKeyPreIme(int keyCode, KeyEvent ev) 
   {

		if (ev.getAction() == KeyEvent.ACTION_DOWN) 
		{

			keyPreImeDown(keyCode, ev.getUnicodeChar());

		}
		else if (ev.getAction() == KeyEvent.ACTION_UP) 
		{

			keyPreImeUp(keyCode, ev.getUnicodeChar());

		}

		return true;

	}


   private void openUrl(String url) 
   {

		try
		{

			Uri uriUrl = Uri.parse(url);

			Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);

			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			getContext().startActivity(intent);

		}
		catch(android.content.ActivityNotFoundException e)
		{

		}

   }

//
//    static class editable extends SpannableStringBuilder
//    {
//
//        editable()
//        {
//
//            super();
//
//        }
//
//
//        @Override
//        public SpannableStringBuilder replace(final int start, final int end, CharSequence tb, int tbstart, int tbend)
//        {
//
//            SpannableStringBuilder builder = super.replace(start, end, tb, tbstart, tbend);
//
//            return builder;
//
//        }
//
//        public SpannableStringBuilder set(String str)
//        {
//
//            int iLocalStart = 0;
//
//            int iLocalLengthy = length();
//
//            int iStringsStart = 0;
//
//            int iStringLen = str.length();
//
//            return replace(iLocalStart, iLocalLengthy, str, iStringsStart, iStringLen);
//
//        }
//
//    }

}



