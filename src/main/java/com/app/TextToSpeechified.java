package src.main.java.com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import src.main.java.com.app.TextToSpeechify;

public class TextToSpeechified {
		
	private Synthesizer synthesizer;
	TextToSpeechify tts_helper;
	String speechReadyTextFile = "speechReadyText.txt";	
	
	public TextToSpeechified() throws Exception {
		
		try {
			// Set property as Kevin Dictionary
	        System.setProperty(
	            "freetts.voices",
	            "com.sun.speech.freetts.en.us"
	                + ".cmu_us_kal.KevinVoiceDirectory");
			
			/* AlanVoiceDirectory seems to be buggy so we don't use it!
			 * repeated errors of:
			 * 		ClusterUnitDatabase: can't find tree for pau_ax
			 * 		ClusterUnitDatabase Error: getUnitIndex: can't find unit type pau_ax
			 */
			// Set property as Alan Dictionary
//	        System.setProperty(
//	            "freetts.voices",
//	            "com.sun.speech.freetts.en.us"
//	                + ".cmu_time_awb.AlanVoiceDirectory");
	
	        // Register Engine
	        Central.registerEngineCentral(
	            "com.sun.speech.freetts"
	            + ".jsapi.FreeTTSEngineCentral");
	        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
	
	        // Create a Synthesizer        
	        synthesizer = Central.createSynthesizer( new SynthesizerModeDesc( Locale.US ) );
	
	        // Allocate synthesizer
	        synthesizer.allocate();
	
	        // Resume Synthesizer
	        synthesizer.resume();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
    // Deallocate the Synthesizer.
	public void deallocate() throws Exception {
		
		try {
			synthesizer.deallocate();
		} catch( Exception e ) {
			e.printStackTrace();
		}

	}
	
	public void speak() throws Exception {
		
		try {
			// Speaks the given text
	        // until the queue is empty.
			BufferedReader br = new BufferedReader( new FileReader( speechReadyTextFile ) );
			String line;
			while( ( line = br.readLine() ) != null ) {
				synthesizer.speakPlainText( line, null );
			}
	        synthesizer.waitEngineState( Synthesizer.QUEUE_EMPTY );
	        br.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}		
		
	}
	
	public void speechifyText() {

		tts_helper = new TextToSpeechify();
		tts_helper.getWebResourcesFromURLFeed();

	}
	
	public static void main(String[] args) throws Exception {
		
		try {
			
//			if( args.length < 1 ) {
//				System.out.println("usage: textToSpeechify <filename>");
//				System.exit(0);
//			}
			TextToSpeechified tts_client = new TextToSpeechified();
			tts_client.speechifyText();
			tts_client.speak();
			tts_client.deallocate();
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
}