package platform.platform;


import android.app.AlertDialog;
import android.content.DialogInterface;


class message_box
{

	static int e_dialog_result_none = 0;
   static int e_dialog_result_ok = 1;
   static int e_dialog_result_cancel = 2;
   static int e_dialog_result_abort = 3;
   static int e_dialog_result_retry = 4;
   static int e_dialog_result_ignore = 5;
   static int e_dialog_result_yes = 6;
   static int e_dialog_result_no = 7;
   static int e_dialog_result_close = 8;
   static int e_dialog_result_help = 9;
   static int e_dialog_result_try_again = 10;
   static int e_dialog_result_continue = 11;
   static int e_dialog_result_timeout = 32000;


	long m_lMicromessagebox;

	String m_strMessage;

	String m_strCaption;

	int m_iButton;

	//View m_impact;

	main_activity m_mainactivity;

	AlertDialog.Builder m_builder;

	//public message_box(jlong lMicromessagebox, ace ace, View impact, String strMessage, String strCaption, int iButton) 
	public message_box(long lMicromessagebox, /*ace ace,*/ String strMessage, String strCaption, int iButton) 
	{

		m_lMicromessagebox = lMicromessagebox;

		//m_mainactivity = ace;

		//m_impact = impact;

		m_strMessage = strMessage;

		m_strCaption = strCaption;

		m_iButton = iButton;

	}

	public void display(main_activity mainactivity) 
   {

		m_mainactivity = mainactivity;

		AlertDialog.Builder builder = new AlertDialog.Builder(mainactivity);

		builder.setTitle(m_strCaption);

		builder.setMessage(m_strMessage);

		//m_direct.m_iMessageBoxResult = 0;

		if ((m_iButton & 16) != 0 || m_iButton == 0) 
		{

			builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
		 
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
			   
					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_ok);

				}

			});

		}

		if ((m_iButton & 32) != 0) 
		{

			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_yes);

				}

			});

		}
	
		if ((m_iButton & 64) != 0) 
		{

			builder.setNegativeButton("No", new DialogInterface.OnClickListener() 
			{

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_no);

				}

			});

		}

		if ((m_iButton & 128) != 0) 
		{

			builder.setNegativeButton("Abort", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_abort);

				}

			});

		}

		if ((m_iButton & 256) != 0) 
		{

			builder.setNegativeButton("Retry", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_retry);

				}

			});

		}

		if ((m_iButton & 512) != 0) 
		{

			builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_ignore);

				}

			});

		}

		if ((m_iButton & 1024) != 0) 
		{

			builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_cancel);

				}

			});

		}

		if ((m_iButton & 2048) != 0) 
		{

			builder.setNegativeButton("Try", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_try_again);

				}

			});

		}

		if ((m_iButton & 4096) != 0) 
		{

			builder.setNegativeButton("Continue", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) 
				{

					m_mainactivity.on_message_box_response(m_lMicromessagebox, e_dialog_result_continue);

				}

			});

		}

		m_builder = builder;

		mainactivity.runOnUiThread(
					new Runnable()
					{
						
						public void run() 
						{
		
							AlertDialog dialog = m_builder.create();

							if ((m_iButton & 1024) != 0) 
							{
								
								dialog.setCanceledOnTouchOutside(true);

							}

							dialog.show();

						}

					});

	}

}
