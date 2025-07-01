import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import src.main.java.com.app.TextToSpeechify;

public class TextToSpeechifyTest {
	
	private final String ROOT = "src/test/java/com/app/";
	String urlFeedFile = ROOT + "urlFeed.test.txt";	
	private final String pdfFileName = ROOT + "blob.test.pdf";
	private final String htmlFromPDFFileName = ROOT + "blob.test.pdf.html";
	private final String htmlFileName = ROOT + "blob.test.html";
	private final String scratchFileName = ROOT + "blob.test.scratch.html";
	String speechReadyTextFile = ROOT + "speechReadyText.test.txt";

	@Test
	public void testsWorking() {
		String greet = "hello world";
		Assertions.assertEquals( "hello world",greet );
	}
	
	@Test
	public void testReadSingleUrlFromURLFeed() {
		int numberOfURLsRead = 0;
		try {

			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
			String urls;
			while( ( urls = br0.readLine() ) != null ) {
				if( urls.length() != 0 )
					numberOfURLsRead += 1;
				// break early to end test after reading a single file				
				break;
			}
		}
		catch( IOException ioe ) {
			ioe.printStackTrace();
		}
		Assertions.assertEquals( numberOfURLsRead,1 );
	}
	
	@Test
	public void testReadMultipleUrlsFromURLFeed() {
		int totalNumberOfURLs = 0, numberOfURLsRead = 0;
		try {
			byte[] bytes = Files.readAllBytes( Paths.get( urlFeedFile ) );
			String[] tmpFileContents = new String( bytes ).split("\\n");
			totalNumberOfURLs = tmpFileContents.length;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// make sure we don't have 0 for totalNumberOfURLs
		Assertions.assertNotEquals(0,totalNumberOfURLs);
		
		try {

			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
			String urls;
			while( ( urls = br0.readLine() ) != null ) {				
				if( urls.length() != 0 )
					numberOfURLsRead += 1;
			}
		}
		catch( IOException ioe ) {
			ioe.printStackTrace();
		}	
		Assertions.assertEquals( totalNumberOfURLs,numberOfURLsRead );
	}
	
	@Test
	public void testDownloadSingleNonPDFURLFromFeeder() {
		int numberOfURLsRead = 0;	
		
		try {

			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
			String urls;
			File htmlFile = new File( htmlFileName );
			// ensure the file doesn't exist before we run this test
			if( htmlFile.exists() )
            	htmlFile.delete();
			Assertions.assertFalse( htmlFile.exists() );
			
			while( ( urls = br0.readLine() ) != null ) {
				try {
		            // Create a URI object and convert it to a URL
		            URI uri;
		            URL url;
		            BufferedReader br1;
	            	BufferedWriter bw;
            		// Create a URI object and convert it to a URL
		            uri = new URI( urls );
		            url = uri.toURL();
            		System.out.println("[test][*] Downloading: " + urls);
            		// Specify the filename to save the downloaded content
		            if( !htmlFile.exists() )
		            	htmlFile.createNewFile();
		            else {
		            	htmlFile.delete();
		            	htmlFile.createNewFile();
		            }
		            bw = new BufferedWriter( new FileWriter( htmlFile ) );
		            
		            // Open a stream to read the webpage content
	            	br1 = new BufferedReader( new InputStreamReader( url.openStream() ) );
		            
		            // Read each line from the stream and write it to the file
		            String line;
		            while ( ( line = br1.readLine() ) != null ) {
		                bw.write( line );
		                bw.newLine(); // Add a new line for better formatting
		            }
		            // Close the streams
		            br1.close();
		            bw.close();
		        } 
		        // Handle malformed URL or URI exceptions
		        catch( MalformedURLException | IllegalArgumentException e ) {
		        	e.printStackTrace();
		        } 
		        // Handle IO exceptions
		        catch( IOException e ) {
		        	e.printStackTrace();
		        } 
		        // Handle URI syntax exceptions
		        catch( Exception e ) {
		        	e.printStackTrace();
		        }
				if( urls.length() != 0 )
					numberOfURLsRead += 1;
				break;
			}
			Assertions.assertEquals( numberOfURLsRead,1 );
			Assertions.assertTrue( htmlFile.exists() );
		}
		catch( IOException ioe ) {
			ioe.printStackTrace();
		}		
	}
	
//	@Test
//	public void completeAlgorithmTest() {
//		try {
//
//			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
//			String urls;
//			File htmlFile = new File( htmlFileName );
//			File pdfFile = new File( pdfFileName );
////			File textFile = new File( textFileName );
//			boolean parsingAPDF = false, movedFileToLocalDir = false;
//			while( ( urls = br0.readLine() ) != null ) {				
//				try {
//		            // Create a URI object and convert it to a URL
//		            URI uri;
//		            URL url;
//		            BufferedReader br1;
//	            	BufferedWriter bw;
//	            	Pattern pdfPattern = Pattern.compile("(\\.pdf|\\/pdf\\/)");
//	            	Matcher pdfPatternMatcher = pdfPattern.matcher( urls );
//	            	if(! pdfPatternMatcher.find() ) {
//	            		// Create a URI object and convert it to a URL
//			            uri = new URI( urls );
//			            url = uri.toURL();
//	            		System.out.println("[*] Downloading: " + urls);
//	            		// Specify the filename to save the downloaded content
//			            if( !htmlFile.exists() )
//			            	htmlFile.createNewFile();
//			            else {
//			            	htmlFile.delete();
//			            	htmlFile.createNewFile();
//			            }
//			            bw = new BufferedWriter( new FileWriter( htmlFile ) );
//			            
//			            // Open a stream to read the webpage content
//		            	br1 = new BufferedReader( new InputStreamReader( url.openStream() ) );
//			            
//			            // Read each line from the stream and write it to the file
//			            String line;
//			            while ( ( line = br1.readLine() ) != null ) {
//			                bw.write( line );
//			                bw.newLine(); // Add a new line for better formatting
//			            }
//			            // Close the streams
//			            br1.close();
//			            bw.close();
//	            	}
//	            	else {
//	            		// Specify the filename to save the downloaded content
//	            		System.out.println("[*] Found pdf url in feed!");
//	            		parsingAPDF = true;
//			            if( !pdfFile.exists() )
//			            	pdfFile.createNewFile();
//			            else {
//			            	pdfFile.delete();
//			            	pdfFile.createNewFile();
//			            }
//			            // check if we need to download the pdf from a remote source
//			            Pattern protocolPattern = Pattern.compile("^(http?s)");
//		            	Matcher protocolPatternMatcher = protocolPattern.matcher( urls );
//		            	if( protocolPatternMatcher.find() ) {
//		            		// Create a URI object and convert it to a URL
//		            		System.out.println("[*] Downloading: " + urls);
//				            uri = new URI( urls );
//				            url = uri.toURL();		            		
//			            	ReadableByteChannel readableByteChannel = Channels.newChannel( url.openStream() );
//			            	FileOutputStream fileOutputStream = new FileOutputStream( pdfFileName );
//			            	FileChannel fileChannel = fileOutputStream.getChannel();
//			            	fileChannel.transferFrom( readableByteChannel, 0, Long.MAX_VALUE );
//		            	}
//		            	else {
//		            		// the file already exists locally so let's move it from where it is to the current directory
//		            		File localPDFFile = new File( urls );
//		            		localPDFFile.renameTo( pdfFile );
//		            		movedFileToLocalDir = true;
//		            	}
//	            	}
//
//		            if ( parsingAPDF == true ) {
//						convertPDFToHTML( pdfFileName );						
//						if( movedFileToLocalDir == true ) {
//							// move the file back to its original location
//							System.out.println("[*] moving " + pdfFileName + " back to its original location: " + urls);
//							File originalPDFFilePath = new File( urls );
//							pdfFile.renameTo( originalPDFFilePath );
//							movedFileToLocalDir = false;
//						}
//						parsePDFToHTMLFile( htmlFromPDFFileName );
//					}
//		            else {						
//						parseHTMLFile( htmlFileName );
//					}
//		            parsingAPDF = false;	
//		        } 
//		        // Handle malformed URL or URI exceptions
//		        catch( MalformedURLException | IllegalArgumentException e ) {
//		        	e.printStackTrace();
//		        } 
//		        // Handle IO exceptions
//		        catch( IOException e ) {
//		        	e.printStackTrace();
//		        } 
//		        // Handle URI syntax exceptions
//		        catch( Exception e ) {
//		        	e.printStackTrace();
//		        }
//			}
//		}
//		catch( IOException ioe ) {
//			ioe.printStackTrace();
//		}
//	}
}
