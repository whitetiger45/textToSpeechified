package src.main.java.com.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.regex.*;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

public class TextToSpeechify {
	
	String urlFeedFile = "urlFeed.txt";
	private final String htmlFileName = "blob.html";
	private final String scratchFileName = "blob.scratch.html";
	String speechReadyTextFile = "speechReadyText.txt";
	
	// https://www.geeksforgeeks.org/java/download-web-page-using-java/
	public void getWebResourcesFromURLFeed() {
		
		try {

			BufferedReader br0 = new BufferedReader( new FileReader( urlFeedFile ) );
			String urls;
			File htmlFile = new File( htmlFileName );
//			File textFile = new File( textFileName );
			while( ( urls = br0.readLine() ) != null ) {
				System.out.println("[*] Downloading: " + urls);
				try {
		            // Create a URI object and convert it to a URL
		            URI uri = new URI( urls );
		            URL url = uri.toURL();

		            // Open a stream to read the webpage content
		            BufferedReader br1 = new BufferedReader( new InputStreamReader( url.openStream() ) );

		            // Specify the filename to save the downloaded content
		            if( !htmlFile.exists() )
		            	htmlFile.createNewFile();
		            else {
		            	htmlFile.delete();
		            	htmlFile.createNewFile();
		            }
		            	
		            BufferedWriter bw = new BufferedWriter( new FileWriter( htmlFile ) );

		            // Read each line from the stream and write it to the file
		            String line;
		            while ( ( line = br1.readLine() ) != null ) {
		                bw.write( line );
		                bw.newLine(); // Add a new line for better formatting
		            }

		            // Close the streams
		            br1.close();
		            bw.close();
		            parseHTMLFile();
		
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
		} catch( IOException ioe ) {
			ioe.printStackTrace();
		}
		
	}
	
	public void parseHTMLFile() {

		String line;
		try {
			
			File htmlFile = new File( htmlFileName );
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
            String offset = "</head>\n";
            int stepThree = stepTwo.indexOf( offset );
            
            // step 4            
            String stepFour = stepTwo.substring( stepThree + offset.length(), stepTwo.length() );
            StringBuffer stepFourReversedSB = new StringBuffer( stepFour );
            String stepFourReversed = stepFourReversedSB.reverse().toString();
//            bw.write( stepFourReversed );
            
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
            				bw.write( stepEight );
            				bw.newLine();
            			}
            		}
            	}
            	
            } else if ( stepFour.length() != 0 || stepOne.length() != 0 ) {
            	
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
            	
            	for( String stepSevenArrayVal : stepSeven ) {
            		htmlSB.append(stepSevenArrayVal);
            		htmlSB.append("\n");
            	}
            	String parsedHTML = htmlSB.toString(); 
            	bw.write( parsedHTML );
            } else {
            	System.out.println("Did not find one, either, or both of the initial '<p>' and closing </p> tags. Time for an upgrade!");
            }
            
            bw.newLine();
			bw.close();
		} catch( Exception e ) {
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
			while( idx_y < html.length() ) {
				Character currChar = html.charAt( idx_x );
				Character nextChar = html.charAt( idx_y );				
				String window = html.substring(idx_x,idx_x+5);
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
						Character prevChar = prevCharsSB.toString().charAt(0);
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
				prevCharsSB.append(currChar);
				idx_x += 1; idx_y += 1;
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return sb.toString();
		
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
	
	public static String[] unescapeHTML( String[] html ) {
		
		Pattern escapedHTMLPattern = Pattern.compile("(&?#[a-z0-9]+;)");
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
	
}
