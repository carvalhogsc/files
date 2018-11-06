package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import models.entities.Product;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		
		List<Product> products = new ArrayList<>();
		
		Scanner inputData = new Scanner(System.in);
		String srcPathFile = inputData.nextLine();
		
		File srcPath = new File(srcPathFile);
		String srcPathFolder = srcPath.getParent();
		
		boolean sucess = new File(srcPathFolder + "\\out").mkdir();
		
		if(sucess) {		
		
			String targetFileStr = srcPathFolder + "\\out\\summary.csv";			
			
			
			try (BufferedReader bufferInput = new BufferedReader(new FileReader(srcPath))){
				
				String lineCSV = bufferInput.readLine();
				
				while(lineCSV != null) {
					String[] fields = lineCSV.split(",");
					String name = fields[0];
					double price = Double.parseDouble(fields[1]);
					int quantity = Integer.parseInt(fields[2]);
					
					products.add(new Product(name, price, quantity));
					
					lineCSV = bufferInput.readLine();				
				}
				
				try (BufferedWriter bufferOut = new BufferedWriter(new FileWriter(targetFileStr))){
					for (Product product : products) {
						bufferOut.write(product.getName() + "," + String.format("%.2f", product.totalPrice()));
						bufferOut.newLine();
					}
					
					System.out.println(targetFileStr.toUpperCase() + " CREATED!");
					
				}catch (IOException e) {
					System.out.println("Error writing file: " + e.getMessage());
				}
				
			} catch (Exception e) {
				System.out.println("Error reading file: " + e.getMessage());
			}
			finally {
				if(inputData != null) {
					inputData.close();
				}
			}
		}

	}

}
