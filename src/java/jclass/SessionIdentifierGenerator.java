package jclass;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public final class SessionIdentifierGenerator {

public String SessionID(){
UUID uuid = UUID.randomUUID();
String randomUUIDString = uuid.toString();
return randomUUIDString;
}
}