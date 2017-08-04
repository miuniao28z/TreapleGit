package main.java.tr.publicutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StaticFunction {

	public static String getPwdOutPutFile(){
		//path=path.replace("\\", "//")+"//UTILFILE//createtable.txt";
		return System.getProperty("user.dir").replace("\\", "//")+"//WebMagic//result//";
	}

	public static void ToText(String sb,String filename) throws IOException {
		String path=StaticFunction.getPwdOutPutFile()+filename;
		File file=new File(path);
		BufferedWriter bw =new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
		bw.close();
	}

	
	
}
