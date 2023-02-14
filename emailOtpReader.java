package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Store;

import com.testing.framework.EmailUtils;




public class emailOtpReader {
	
	private static EmailUtils em=new EmailUtils();
	
	private static Properties pr=new Properties();
	
	public static String readOtp() throws Exception {
		pr.load(new FileInputStream(".\\src\\test\\resources\\config\\emailotp.properties"));
		Store connection=em.connectToGmail(pr);
		//em.getUnreadMessages(connection, "Inbox");
		String otp1="";
		List<String> emailtext=em.getUnreadMessageByFromEmail(connection, "Inbox", "hodgebankautomation123@gmail.com", "[SMSForwarder] New message");
		//List<String> emailtext=em.getUnreadMessageByFromEmail(connection, "Inbox", "mohanreddy166163@gmail.com", "[SMSForwarder] New message from 57575701");
		//List<String> emailtext=em.getUnreadMessageByFromEmail(connection, "Inbox", "hodgeautomationtest@gmail.com", "[SMSForwarder] New message");
		
		if(emailtext.size()<1)
			return "No Email Received";
		else {
			String reg="[^\\d]+";
			String otp=emailtext.get(0);
			System.out.println(otp);
			return otp.substring(otp.lastIndexOf(".")-6, otp.lastIndexOf("."));
			//return otp;
		}
		//return otp1;
}
	
}	



