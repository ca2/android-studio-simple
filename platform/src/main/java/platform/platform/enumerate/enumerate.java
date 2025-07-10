package platform.platform.enumerate;


import android.provider.MediaStore;
import android.app.Activity;


public class enumerate
{

    
   item        m_item;

   private item create_item(String strFolder)
   {

      if(strFolder.equals("image"))
      {

         return new image();

      }
      else if(strFolder.equals("music"))
      {

         return new music();

      }
      else if(strFolder.equals("video"))
      {

         return new video();

      }
      else
      {

         return null;

      }

   }

   public boolean start(String strFolder, Activity activity)
   {

      m_item = create_item(strFolder);

      if(m_item == null)
      {

          return false;

      }

      if(!m_item.start(activity))
      {

         m_item = null;

         return false;

      }

      return true;

   }


   public boolean step()
   {

       for(int i = 0; i < 5; i++)
       {

         if(!m_item.step())
         {

            m_item = null;

            return false;

         }

       }

       return true;

   }

}



