package parser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GetGMTDateTime {

	Date date;
	String dateString;

	public GetGMTDateTime(String str) {
		// TODO Auto-generated constructor stub
		this.dateString = str;
	}

	public Date getDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TimeZone ist = TimeZone.getTimeZone("GMT");
			sdf.setTimeZone(ist);
			sdf.setLenient(false);
			Date date_obj = null;
			try {
				date_obj = sdf.parse(dateString);
//				System.out.println("start: " + date_obj);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return date_obj;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception while searching");
		}
		return null;
	}
	
	

}
