package platform.platform.enumerate;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;


class music extends item
{

   int m_titleColumn;

   int m_artistColumn;

   public static native void announce(long id, String display_name, String full_path, String mime_type, String title, String artist);

   @Override
   public Uri get_uri()
   {
       return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
   }

   @Override
   public void on_start()
   {

      super.on_start();

      m_titleColumn = m_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

      m_artistColumn = m_cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

   }

   @Override
   void on_announce()
   {

      String thisDisplayName = m_cursor.getString(m_displayNameColumn);
               
      String thisFullPath = m_cursor.getString(m_fullPathColumn);

      String thisMimeType = m_cursor.getString(m_mimeTypeColumn);

      long thisId = m_cursor.getLong(m_idColumn);
               
      String thisTitle = m_cursor.getString(m_titleColumn);
               
      String thisArtist = m_cursor.getString(m_artistColumn);
       
      announce(thisId, thisDisplayName, thisFullPath, thisMimeType, thisTitle, thisArtist);

   }

}
