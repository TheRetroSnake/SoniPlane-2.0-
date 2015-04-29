package soni.plane.api.java.util;

@SuppressWarnings("deprecation")
public final class Date {
	/* stored reference */
	private java.util.Date date;

	public Date(long date){
		this.date = new java.util.Date(date);
	}

	/* methods to get values from Date
	 * And yes, I am aware all of these are deprecated. So what? */
	public int getYear(){
		return date.getYear() + 1900;
	}

	public int getMonth(){
		return date.getMonth() + 1;
	}

	public int getDay(){
		return date.getDate();
	}

	public int getHour(){
		return date.getHours();
	}

	public int getMinute(){
		return date.getMinutes();
	}

	public int getSecond(){
		return date.getSeconds();
	}
}
