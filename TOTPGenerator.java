package util;

import org.jboss.aerogear.security.otp.Totp;

public class TOTPGenerator { /**
     * Method is used to get the TOTP based on the security token
     * @return
     */
	
	String totp;

    String twoFactorCode;
    
    public static String getTwoFactorCode(){
    	


        Totp totp = new Totp("fcdqvgn5irhjen2e7f6p2qfo2igpknry"); // 2FA secret key
        String twoFactorCode = totp.now(); //Generated 2FA code here
        return twoFactorCode;
    	
    }
	
	

}
