package example;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Session class with getters and setters that will be stored on a server
 *
 */
public class Session {
	
	private long sessionId;
	private Date expiry;
	private String ipAddress;

	public long getSessionId() {
		return sessionId;
	}

	//create random session id for a given user/host
	public long setSessionId() {
        long v = -1;
        do
        {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer b = ByteBuffer.wrap(new byte[16]);
            b.putLong(uid.getLeastSignificantBits());
            b.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(b.array());
            v = bi.longValue();
        } while (v < 0);

		return v;
	}

	public Date getExpiry() {
		return expiry;
	}

	// setting the session expiry
	public Date setExpiry() {
		Calendar date = Calendar.getInstance();		
		date.add(Calendar.MINUTE, 5);
		return date.getTime();
	}
	
	public Session(String ip){
		sessionId = setSessionId();
		expiry = setExpiry();
		ipAddress = ip;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ip){
		ipAddress = ip;
	}
}
