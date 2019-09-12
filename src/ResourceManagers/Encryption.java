package ResourceManagers;


public final class Encryption {
	
	private static byte pad = (byte)69;
	
	public static String encrypt(String input){
		byte[] bytes = new byte[input.length()];
		String output = ""; 
		for(int i = 0;i < bytes.length;i++){
			bytes[i] = (byte)input.charAt(i);
			bytes[i] = (byte)(bytes[i]^(pad + i));
		}
		for(byte b : bytes){
			output += ((char) b);
		}
		return output;
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
}
