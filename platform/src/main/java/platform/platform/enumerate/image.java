package platform.platform.enumerate;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;


class image extends item
{

   int m_titleColumn;

   public static native void announce(long id, String display_name, String full_path, String mime_type, String title);

   @Override
   public Uri get_uri()
   {
       return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
   }

   @Override
   public void on_start()
   {

      super.on_start();

      m_titleColumn = m_cursor.getColumnIndex(MediaStore.Images.Media.TITLE);

   }

   @Override
   void on_announce()
   {

      String thisDisplayName = m_cursor.getString(m_displayNameColumn);
               
      String thisFullPath = m_cursor.getString(m_fullPathColumn);

      String thisMimeType = m_cursor.getString(m_mimeTypeColumn);

	   long thisId = m_cursor.getLong(m_idColumn);
               
      String thisTitle = m_cursor.getString(m_titleColumn);
               
      announce(thisId, thisDisplayName, thisFullPath, thisMimeType, thisTitle);

   }

}
