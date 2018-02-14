import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class TestClass {

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] test = "huhu".getBytes(Charset.forName("UTF-8"));
		for(byte b: test) {
			System.out.println(b);
		}
		System.out.println(new String(test, Charset.forName("UTF-8")));
	}

}
