package com.target.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Rishi Mishra
 * It takes one exported .dsx file( one .dsx file may contain multiple jobs ) from datastage as input , splits it into individual .dsx file ( which contains single job ) . 
 * This code is written to minimize the manual effort in exporting individual job from DataStage .
 */



public class DSXSplitter {

	public static void main(String[] args) {
		try {
			Map map = readDSXFile("H:\\Rishi\\EDW\\temp\\test.dsx");//Please provide input file path as per your file system.
			if(map != null && map.size() > 0){
			writeIndividualDSXFile(map , "H:\\Rishi\\EDW\\temp\\"); // Please provide output file path as per your file system.
			System.out.println(" Split job successful !!");
			}else{
				System.out.println("input file is empty .");
			}
		} catch (Exception e) {
			System.out.println("Oops file split operation failed , Sorry !!!!");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method reads the .dsx file (which is exported from datastage ).
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static  Map  readDSXFile(String inputFilePath) throws Exception{
		BufferedReader br = null;
		StringBuffer headerInfoBuffer = new StringBuffer();
		StringBuffer tempBuffer = new StringBuffer();
		StringBuffer finalBuffer = null;
		String jobName = "";
		int j = 0;
		Map map = new HashMap();
		int i = 0;
		String sCurrentLine;
		br = new BufferedReader(
				new FileReader(inputFilePath));
		while ((sCurrentLine = br.readLine()) != null) {
			if (i < 11) {
				if (!sCurrentLine.contains("BEGIN DSJOB")) {
					headerInfoBuffer.append(sCurrentLine);
					if (!sCurrentLine.contains("END HEADER"))
						headerInfoBuffer.append("\r\n");
				}
				i++;
			} else {
				if (!sCurrentLine.contains("END DSJOB")) {
					tempBuffer.append(sCurrentLine);
					tempBuffer.append("\r\n");
					if (j < 1) {
						if (sCurrentLine.contains("Identifier")) {
							// Identifier "ecf_archive"
							jobName = sCurrentLine.split("\"")[1].trim()
									.toString();
							j++;
						}
					}
				} else {
					tempBuffer.append(sCurrentLine);
					finalBuffer = new StringBuffer();
					map.put(jobName,
							finalBuffer.append(headerInfoBuffer).append("\r\n").append(tempBuffer)
									.toString());
					tempBuffer.setLength(0);
					jobName = "";
					j = 0;
				}
			}
		}
		br.close();
		return map;
	}
	
	/**
	 * This method is to write the individual .dsx file (containing individual job).
	 * @param map
	 * @throws Exception
	 */
	public static void  writeIndividualDSXFile(Map map , String outputFilePath) throws Exception{
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		BufferedWriter bw = null;
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			FileWriter fw = new FileWriter(outputFilePath+ entry.getKey() + ".dsx");
			 bw = new BufferedWriter(fw);
			bw.write(entry.getValue());
		}
		bw.close();
	}
}