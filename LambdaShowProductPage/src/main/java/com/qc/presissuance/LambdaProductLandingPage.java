package com.qc.presissuance;

import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.qc.presissuance.request.Request;
import com.qc.presissuance.util.AESEncryptor;

public class LambdaProductLandingPage implements RequestHandler<Request, String>{

	
    
	@Override
	public String handleRequest(Request request, Context context) {
		String productHtml = "";
		if(request.getTxnId()!=null&&!request.getTxnId().isEmpty()) {
			try {
				final AmazonS3 amazonS3client = AmazonS3ClientBuilder.standard().build();
				String txnWithSource = AESEncryptor.decrypt(request.getTxnId());
				String txnIde = txnWithSource.split(Pattern.quote("||"))[0];
//				String source = txnWithSource.split(Pattern.quote("||"))[1];
//				String appSource = txnWithSource.split(Pattern.quote("||"))[2];
				String s3Content = amazonS3client.getObjectAsString("qc-posv-webapp", "product-mpro.html");
				s3Content = s3Content.replaceAll("TTTXXXNNNIIIDDD", txnIde);
				productHtml = s3Content;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			//productHtml should return error page.
			System.out.println("Txn id is empty for the given product page.");
		}
		return productHtml;
	}
	
	
	public static void main(String[] args) {
		
		String txnWithSource = AESEncryptor.decrypt("3331333233313233317c7c457c7c6d70726f");
		String txnIde = txnWithSource.split(Pattern.quote("||"))[0];
		System.out.println(txnIde);
	}

}
