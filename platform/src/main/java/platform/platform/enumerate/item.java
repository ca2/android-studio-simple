package platform.platform.enumerate;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;


class item
{


	Cursor m_cursor;

   int m_displayNameColumn;

   int m_fullPathColumn;

   int m_mimeTypeColumn;

   int m_idColumn;
         

   item()
   {

   }


   public void on_start()
   {

      m_displayNameColumn = m_cursor.getColumnIndex(MediaColumns.DISPLAY_NAME);
      
      m_fullPathColumn = m_cursor.getColumnIndex(MediaColumns.DATA);

      m_mimeTypeColumn = m_cursor.getColumnIndex(MediaColumns.MIME_TYPE);

      m_idColumn = m_cursor.getColumnIndex(MediaColumns._ID);

   }


   public Uri get_uri()
   {

       return null;

   }


   public boolean start_cursor()
   {

      if(m_cursor != null && m_cursor.moveToFirst())
      {

          return true;

      }

      return false;

   }


   boolean start(final Activity activity)
   {

       new Thread(new Runnable() {
     @Override
     public void run() {

      ContentResolver contentresolver = activity.getContentResolver();
      
      Uri uri = get_uri();

      if(uri == null)
      {

          return;

      }

      m_cursor = contentresolver.query(uri, null, null, null, null);

      if(!start_cursor())
      {

          m_cursor = null;

          return;

      }

      on_start();

      while(step());

}
    		   
       }).start();


      return true;

   }


   boolean step()
   {

       on_announce();

       if(!m_cursor.moveToNext())
       {

           m_cursor = null;

           return false;

       }

       return true;

   }

   void on_announce()
   {

   }


}
