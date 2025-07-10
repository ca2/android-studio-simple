package platform.platform;


import android.text.SpannableStringBuilder;


class editable extends SpannableStringBuilder 
{

	editable() 
	{

		super();

	}

			
	@Override
	public SpannableStringBuilder replace(final int start, final int end, CharSequence tb, int tbstart, int tbend) 
	{

		SpannableStringBuilder builder = super.replace(start, end, tb, tbstart, tbend);

		return builder;

	}

	public SpannableStringBuilder set(String str) 
	{

		int iLocalStart = 0;

		int iLocalLengthy = length();

		int iStringsStart = 0;

		int iStringLen = str.length();

		return replace(iLocalStart, iLocalLengthy, str, iStringsStart, iStringLen);

	}

}


