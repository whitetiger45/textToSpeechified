package src.main.java.com.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.*;
import java.util.Stack;
import java.util.stream.Stream;

import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import src.main.java.com.app.util.JarClassLoader;

public class TextToSpeechify {
	
	String urlFeedFile = "urlFeed.txt";	
	private final String pdfFileName = "blob.pdf";
	private final String htmlFromPDFFileName = "blob.pdf.html";
	private final String htmlFileName = "blob.html";
	private final String scratchFileName = "blob.scratch.html";
	String speechReadyTextFile = "speechReadyText.txt";
	private JarClassLoader jar;
	
	/* resources:
	 * https://www.geeksforgeeks.org/java/download-web-page-using-java/
	 * https://www.baeldung.com/java-download-file
	 */
	public void getWebResourcesFromURLFeed() {
		
		try {

			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
			String urls;
			File htmlFile = new File( htmlFileName );
			File pdfFile = new File( pdfFileName );
//			File textFile = new File( textFileName );
			boolean parsingAPDF = false, parsingHTML = false, movedFileToLocalDir = false;
        	Pattern htmlPattern = Pattern.compile( "(\\<!doctype\\shtml)" );
        	Pattern pdfPattern = Pattern.compile("(\\.pdf|\\/pdf\\/)");
        	
			while( ( urls = br0.readLine() ) != null ) {				
				try {
		            // Create a URI object and convert it to a URL
		            URI uri;
		            URL url;
		            BufferedReader br1;
	            	BufferedWriter bw;
	            	Matcher pdfPatternMatcher = pdfPattern.matcher( urls );	            	
	            	if(! pdfPatternMatcher.find() ) {
	            		// Create a URI object and convert it to a URL
			            uri = new URI( urls );
			            url = uri.toURL();
	            		System.out.println("[*] Downloading: " + urls);
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
			            String line; int idx = 0;
			            while ( ( line = br1.readLine() ) != null ) {
			            	if( idx == 0 ) {			            		
			            		Matcher htmlPatternMatcher = htmlPattern.matcher( line.toLowerCase() );
			            		if( htmlPatternMatcher.find() ) {
			            			parsingHTML = true;
			            		}
			            		idx++;
			            	}
			                bw.write( line.toLowerCase() );
			                if( parsingHTML == false )
			                	bw.newLine();
			            }
			            // Close the streams
			            br1.close();
			            bw.close();
	            	}
	            	else {
	            		// Specify the filename to save the downloaded content
	            		System.out.println("[*] Found pdf url in feed!");
	            		parsingAPDF = true;
			            if( !pdfFile.exists() )
			            	pdfFile.createNewFile();
			            else {
			            	pdfFile.delete();
			            	pdfFile.createNewFile();
			            }
			            // check if we need to download the pdf from a remote source
			            Pattern protocolPattern = Pattern.compile("^(http?s)");
		            	Matcher protocolPatternMatcher = protocolPattern.matcher( urls );
		            	if( protocolPatternMatcher.find() ) {
		            		// Create a URI object and convert it to a URL
		            		System.out.println("[*] Downloading: " + urls);
				            uri = new URI( urls );
				            url = uri.toURL();		            		
			            	ReadableByteChannel readableByteChannel = Channels.newChannel( url.openStream() );
			            	FileOutputStream fileOutputStream = new FileOutputStream( pdfFileName );
			            	FileChannel fileChannel = fileOutputStream.getChannel();
			            	fileChannel.transferFrom( readableByteChannel, 0, Long.MAX_VALUE );
		            	}
		            	else {
		            		// the file already exists locally so let's move it from where it is to the current directory
		            		File localPDFFile = new File( urls );
		            		localPDFFile.renameTo( pdfFile );
		            		movedFileToLocalDir = true;
		            	}
	            	}

		            if ( parsingAPDF == true ) {
						convertPDFToHTML( pdfFileName );						
						if( movedFileToLocalDir == true ) {
							// move the file back to its original location
							System.out.println("[*] moving " + pdfFileName + " back to its original location: " + urls);
							File originalPDFFilePath = new File( urls );
							pdfFile.renameTo( originalPDFFilePath );
							movedFileToLocalDir = false;
						}
						parsePDFToHTMLFile( htmlFromPDFFileName );
					}
		            else {						
						parseHTMLFile( htmlFileName, parsingHTML );
					}
		            parsingAPDF = false; parsingHTML = false;	
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
			}
		}
		catch( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	
	public void parseHTMLFile( String inputFileName, boolean parsingHTML ) {

		String line;
		try {
			
			File htmlFile = new File( inputFileName );
			BufferedReader br = new BufferedReader( new FileReader( htmlFile ) );			
			File scratchFile = new File( scratchFileName );
			
            if( !scratchFile.exists() )
            	scratchFile.createNewFile();
            
            // open file for appending
            BufferedWriter bw0 = new BufferedWriter( new FileWriter( speechReadyTextFile, true ) );
            
         // step 1
            StringBuffer sb = new StringBuffer();
            while( ( line = br.readLine() ) != null ) {				
				if( parsingHTML == false )
					sb.append( line + "\n" );
				else
					sb.append( line );
			}
            
            String stepOne = sb.toString();
            
            if( parsingHTML == false ) {
            	bw0.write( stepOne );
            	bw0.close();
            	return;
            }            
            
            // step 2
            String stepTwo = stepOne.replaceAll("\\<", "\n<");
            // writing out the updated blob.html with newlines and correct html formatting and debugging
            File originalHTMLFile = new File( inputFileName );
            originalHTMLFile.delete();
            originalHTMLFile.createNewFile();
            BufferedWriter bw1 = new BufferedWriter( new FileWriter( inputFileName ) );
            bw1.write( stepTwo );
            bw1.close();

            // step 3
            String offset = "</head>\n";
            int stepThree = stepTwo.indexOf( offset );
            
            // step 4            
            String stepFour = stepTwo.substring( stepThree + offset.length(), stepTwo.length() );
            StringBuffer stepFourReversedSB = new StringBuffer( stepFour );
            String stepFourReversed = stepFourReversedSB.reverse().toString();
            
            // step 5
            Pattern stepFiveAPattern = Pattern.compile("\\<p[\\s]*.*?\\>");
            Pattern stepFiveBPattern = Pattern.compile("\\>p\\/\\<");
            Matcher stepFiveAMatcher = stepFiveAPattern.matcher( stepFour );
            Matcher stepFiveBMatcher = stepFiveBPattern.matcher( stepFourReversed );
            if ( stepFiveAMatcher.find() && stepFiveBMatcher.find() ) {
            	            	
            	int stepFiveA = stepFiveAMatcher.start();
            	int stepFiveB = stepFiveBMatcher.start();            	
            	offset = ">p/<";
            	stepFiveB = stepFour.length()-(stepFiveB+offset.length());
            	
            	// step 6
            	String stepSix = stepFour.substring(stepFiveA,stepFiveB);
            	String[] stepSixArray = stepSix.split("\\n");
            	
            	// step 7
            	String[] stepSeven = Utils.unescapeHTML( stepSixArray );
            	
            	for( String stepSevenArrayVal : stepSeven ) {            		
            		Matcher match = Utils.stripTagL( stepSevenArrayVal );
            		if ( match.find() && !Utils.shouldSkipTag( match.group( 1 ) ) ) {
            			// step 8
            			String stepEight = match.group( 2 );
            			if( stepEight.length() != 0 && stepEight != "\n" ) {
            				bw0.write( stepEight );
            				bw0.newLine();
            			}
            		}
            	}
            	
            }
            else if ( stepFour.length() != 0 || stepOne.length() != 0 ) {
            	
            	// step 6
            	String stepSix;
            	
            	if( stepFour.length() != 0 ) {
            		System.out.println("sending stepFour to second_pass");
            		stepSix = secondPass( stepFour );
            	}
            	else {
            		System.out.println("sending stepOne to second_pass");
            		stepSix = secondPass( stepOne );
            	}

            	// step 7
            	String[] stepSixArray = stepSix.split("\\n");
            	String[] stepSeven = Utils.unescapeHTML( stepSixArray );

            	StringBuilder htmlSB = new StringBuilder(); 
            	
            	for( String stepSevenArrayVal : stepSeven ) {
            		if( stepSevenArrayVal.length() != 0 )
            			htmlSB.append(stepSevenArrayVal);
            	}
            	String parsedHTML = htmlSB.toString(); 
            	bw0.write( parsedHTML );
            }
            else {
            	System.out.println("Did not find one, either, or both of the initial '<p>' and closing </p> tags. Time for an upgrade!");
            }
            
            bw0.newLine();
			bw0.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public void parsePDFToHTMLFile( String inputFileName ) {

		String line;
		try {
			
			File htmlFile = new File( inputFileName );
			BufferedReader br = new BufferedReader( new FileReader( htmlFile ) );			
			File scratchFile = new File( scratchFileName );
			
            if( !scratchFile.exists() )
            	scratchFile.createNewFile();
            
            // open file for appending
            BufferedWriter bw = new BufferedWriter( new FileWriter( speechReadyTextFile, true ) );
           
			// step 1
            StringBuffer sb = new StringBuffer();
            while( ( line = br.readLine() ) != null ) {
				sb.append( line );
			}            
            String stepOne = sb.toString();
            
            // step 2
            String stepTwo = stepOne.replaceAll("\\<", "\n<");

            // step 3
            Pattern headTagPattern = Pattern.compile( "\\<\\/head\\>" );
            Matcher headTagPatternMatcher = headTagPattern.matcher( stepTwo );
            int stepThree = 0;
            if( headTagPatternMatcher.find() ) {
            	stepThree = headTagPatternMatcher.end();
            }
            
            // step 4            
            String stepFour = stepTwo.substring( stepThree );
            
            StringBuffer stepFourReversedSB = new StringBuffer( stepFour );
            String stepFourReversed = stepFourReversedSB.reverse().toString();
            
            // step 5
            Pattern stepFiveAPattern = Pattern.compile("\\<p[\\s]*.*?\\>");
            Pattern stepFiveBPattern = Pattern.compile("\\>p\\/\\<");
            Matcher stepFiveAMatcher = stepFiveAPattern.matcher( stepFour );
            Matcher stepFiveBMatcher = stepFiveBPattern.matcher( stepFourReversed );
            if ( stepFiveAMatcher.find() && stepFiveBMatcher.find() ) {
            	            	
            	int stepFiveA = stepFiveAMatcher.start();
            	int stepFiveB = stepFiveBMatcher.start();            	
            	String offset = ">p/<";
            	stepFiveB = stepFour.length()-(stepFiveB+offset.length());
            	
            	// step 6
            	String stepSix = stepFour.substring(stepFiveA,stepFiveB);
            	String[] stepSixArray = stepSix.split("\\n");
            	
            	// step 7
            	String[] stepSeven = Utils.unescapeHTML( stepSixArray );
            	
            	for( String stepSevenArrayVal : stepSeven ) {            		
            		Matcher match = Utils.stripTagL( stepSevenArrayVal );
            		if ( match.find() && !Utils.shouldSkipTag( match.group( 1 ) ) ) {
            			// step 8
            			String stepEight = match.group( 2 );
            			if( stepEight.length() != 0 && stepEight != "\n" ) {
            				bw.write( stepEight );
            				bw.newLine();
            			}
            		}
            	}
            	
            }
            else if ( stepFour.length() != 0 || stepOne.length() != 0 ) {
            	
            	// step 6
            	String stepSix;
            	
            	if( stepFour.length() != 0 ) {
            		System.out.println("sending stepFour to second_pass");
            		stepSix = secondPass( stepFour );
            	} else {
            		System.out.println("sending stepOne to second_pass");
            		stepSix = secondPass( stepOne );
            	}

            	// step 7
            	String[] stepSixArray = stepSix.split("\\n");
            	String[] stepSeven = Utils.unescapeHTML( stepSixArray );

            	StringBuilder htmlSB = new StringBuilder(); 
            	Pattern punctuationPattern = Pattern.compile( "\\S([\\.\\?\\!])" );
            	Pattern numberPattern = Pattern.compile( "([0-9]+)" );
            	Matcher punctuationPatternMatcher, numberPatternMatcher;
            	int idx = 0;
            	String lastLine = "";
            	while( idx < stepSeven.length-1 ) {
            		String currLine = stepSeven[idx].stripLeading().stripTrailing();
            		currLine = Utils.unescapeHTML( currLine );
            		String nextLine = stepSeven[idx+1].stripLeading().stripTrailing();
            		nextLine = Utils.unescapeHTML( nextLine );
            		punctuationPatternMatcher = punctuationPattern.matcher( lastLine );
            		if( currLine.length() == 0 ) {
            			idx += 1;
            		}
            		else if( punctuationPatternMatcher.find() ) {
            			numberPatternMatcher = numberPattern.matcher( lastLine );
            			if( numberPatternMatcher.find() ) {
            				htmlSB.append( "\n" + currLine + " " + nextLine);
            				lastLine = nextLine;
            				idx += 1;
            			}
            			else {
            				htmlSB.append( "\n" + currLine + " ");
            				lastLine = currLine;
            				idx += 1;            				
            			}
            		}
            		else {
            			htmlSB.append( currLine + " " );
            			lastLine = currLine;
            			idx += 1;
            		}            		
            	}
            	String parsedHTML = htmlSB.toString();
            	bw.write( parsedHTML );
            }
            else {
            	System.out.println("Did not find one, either, or both of the initial '<p>' and closing </p> tags. Time for an upgrade!");
            }
            
            bw.newLine();
			bw.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public String secondPass( String html ) {
		
		StringBuilder sb = new StringBuilder();
		Pattern openPattern = Pattern.compile("(\\<)");
		Pattern closePattern = Pattern.compile("(\\>)");
		Pattern endOfCDATAPattern = Pattern.compile("(\\/\\/\\]\\]\\>)");
		Matcher openMatcher = openPattern.matcher( html );
		
		try {
			
			Stack<Character> stack = new Stack();
			StringBuilder lastTagSB = new StringBuilder();
			String lastTag = "";
			StringBuilder prevCharsSB = new StringBuilder();
			
			int idx_x = 0, idx_y = 1;
			while( idx_y < html.length()-1 ) {
				Character currChar = html.charAt( idx_x );
				Character nextChar = html.charAt( idx_y );
				String window;
				if(idx_x + 5 < html.length()-1)
					window = html.substring(idx_x,idx_x+5);
				else
					window = html.substring(idx_x);
				if( Pattern.matches("(CDATA)", window ) ) {

					String choppedHTML = html.substring(idx_x-6);
					int offestFromLocationOfCDATASectionInOriginalHTML = html.indexOf( choppedHTML );					
					Matcher endOfCDATAMatcher = endOfCDATAPattern.matcher( choppedHTML );
					if( endOfCDATAMatcher.find() ) {
						
						int endOfCDATASection = endOfCDATAMatcher.end() + offestFromLocationOfCDATASectionInOriginalHTML;
						int cDATASectionSize = endOfCDATASection + idx_x;
						idx_x = idx_x + cDATASectionSize; idx_y = idx_x + 1;
						
					}					
					continue;
				}
				else if( currChar == '<' ) {
					if( stack.empty() ) {
						stack.push(currChar);
						lastTagSB.append(currChar);
					}
				}
				else if( currChar != '>' ) {
					if( stack.empty() && lastTag.length() == 0 ) {
						sb.append(currChar);
					}
					else {
						lastTagSB.append(currChar);
					}
				}
				else {
					if( !stack.empty() ) {
						Character prevChar = prevCharsSB.toString().charAt( 0 );
						if( prevChar != '=') {
							stack.pop();
							lastTagSB.append(currChar);
							lastTag = lastTagSB.toString();
							if( !lastTag.matches("(\\<script)+") ) {
								lastTag = "";
								lastTagSB = new StringBuilder();
							}					
						} else {
							lastTagSB.append(currChar);
						}
					}
				}
				prevCharsSB = new StringBuilder();
				prevCharsSB.append( currChar );
				idx_x += 1; idx_y += 1;
			}
			
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return sb.toString();
		
	}
		
	/* resources:
	 *  https://stackoverflow.com/questions/29279676/how-to-run-a-java-class-from-a-jar-file-dynamically
	 *  https://stackoverflow.com/questions/3903039/wait-and-notify-method-always-illegalmonitorstateexception-is-happen-and-t
	 *  https://www.geeksforgeeks.org/java/timeunit-class-in-java-with-examples/
	 *  https://jenkov.com/tutorials/java-reflection/dynamic-class-loading-reloading.html
	 *  https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
	 *  https://github.com/radkovo/Pdf2Dom/blob/main/src/main/java/org/fit/pdfdom/PDFToHTML.java
	 *  https://www.toptal.com/java/java-wizardry-101-a-guide-to-java-class-reloading
	 *  https://docs.oracle.com/javase/8/docs/api/java/net/JarURLConnection.html
	 */
	public void convertPDFToHTML( String pdfFileName ) {
		
		try {

			String location = "file:src/main/java/com/resources/lib/PDFToHTML.jar";
			
			URL url = new URI( location ).toURL();
			jar = new JarClassLoader( url );
			
			System.out.println( "[*] attempting to load jar file...");
			System.out.println( "[*] name of main class of PDFToHTML: " + jar.getMainClassName() );
			String[] args = new String[2];
			args[0] = pdfFileName;
			args[1] = htmlFromPDFFileName;
			System.out.println("args[0]: " + args[0]);
			jar.invokeClass(jar.getMainClassName(), args);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			ProcessBuilder builder = new ProcessBuilder();
//			String pdfToHTMLJar = "src/main/java/com/resources/lib/PDFToHTML.jar";
//			File tmp = new File( pdfToHTMLJar );
//			System.out.println("[*] jar file " + pdfToHTMLJar + " exists: " + tmp.exists());
//			builder = new ProcessBuilder("java", "-jar", pdfToHTMLJar, pdfFileName, htmlFromPDFFileName);
//			System.out.println("[*] Executing convertPDFToHTML...");
//			Process p = builder.start();
//			long timeout = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS);
//			
//			synchronized ( p ){
//				p.wait( timeout );
//			}
//			
//			tmp = new File(htmlFromPDFFileName);
//			System.out.println("[*] Finished converting pdf file. "+ htmlFromPDFFileName + " exists: "+ tmp.exists());
//			
//		}catch( Exception e ) {
//			e.printStackTrace();
//		}
	}
}

final class Utils {
	
	private Utils() {}
	
	public final static Map<Integer,String> html_tags = 
			Stream.of( new Object[][] {
				{0,"title"},
				{1,"p"},
				{2,"a"},
				{3,"b"},
				{4,"em"},
				{5,"i"},
				{6,"img"},
				{7,"figure"},
				{8,"strong"},
				{9,"li"},
				{10,"div"},
				{11,"ol"},
				{12,"ul"},
				{13,"span"},
				{14,"polygon"},
				{15,"path"},
				{16,"script"},
				{17,"style"}
			}).collect( Collectors.toMap( data -> (Integer) data[0], data -> (String) data[1] ) );
	
	public final static Map<Integer,String> skip_tags = 
			Stream.of( new Object[][] {
				{6,"img"},
				{7,"figure"},
				{14,"polygon"},
				{15,"path"},
				{16,"script"},
				{17,"style"}
			}).collect( Collectors.toMap( data -> (Integer) data[0], data -> (String) data[1] ) );	
			
	private static final Pattern stripTagPattern = Pattern.compile("\\<\\/*([0-9a-zA-Z]+).*?\\>(.+)");
	
	public static Matcher stripTagL( String line ) {				
		Matcher matcher = stripTagPattern.matcher(line);
		return matcher;
	}
	
	public static Boolean shouldSkipTag( String match ) {
		Boolean tagShouldBeSkipped = false;
		if( skip_tags.containsValue( match ) )
			tagShouldBeSkipped = true;
		return tagShouldBeSkipped;
	}
	
	private static Pattern escapedHTMLPattern = Pattern.compile("(&{1}#{0,1}[a-z0-9]+;{1})");
	
	public static String[] unescapeHTML( String[] html ) {
				
		Matcher escapedHTMLMatcher;
		
		for( int idx = 0; idx < html.length; idx++ ) {
			escapedHTMLMatcher = escapedHTMLPattern.matcher( html[idx] );
			if( escapedHTMLMatcher.find() ) {
				StringBuilder tmpSB = new StringBuilder();				
				tmpSB.append( html[idx].substring( 0, escapedHTMLMatcher.start() ) );
				tmpSB.append( StringEscapeUtils.unescapeHtml4​( escapedHTMLMatcher.group( 1 ) ) );
				tmpSB.append( html[idx].substring( escapedHTMLMatcher.end() ) );
				html[idx] = tmpSB.toString();
			}
		}
		return html;
	}
	
	public static String unescapeHTML( String html ) {
				
		Matcher escapedHTMLMatcher;
		
		escapedHTMLMatcher = escapedHTMLPattern.matcher( html );
		StringBuilder tmpSB = new StringBuilder();
		
		if( escapedHTMLMatcher.find() ) {
			tmpSB.append( html.substring( 0, escapedHTMLMatcher.start() ) );
			tmpSB.append( StringEscapeUtils.unescapeHtml4​( escapedHTMLMatcher.group( 1 ) ) );
			tmpSB.append( html.substring( escapedHTMLMatcher.end() ) );
			html = tmpSB.toString();
		}
		
		return html;
	}
}

