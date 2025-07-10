package platform.platform;


class timer implements Runnable
{
   
	impact m_impact;
	
	public timer(impact impact)
	{

		m_impact = impact;
	
	}

	@Override
	public void run() 
	{

		m_impact.m_mainactivity.runOnUiThread(
			new Runnable()
			{
						
				public void run() 
				{

					m_impact.on_impact_timer();

				}

			});
   
	}

}



